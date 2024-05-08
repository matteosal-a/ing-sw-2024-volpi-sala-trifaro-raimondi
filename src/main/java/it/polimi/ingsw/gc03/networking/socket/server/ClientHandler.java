package it.polimi.ingsw.gc03.networking.socket.server;

import it.polimi.ingsw.gc03.controller.GameController;
import it.polimi.ingsw.gc03.controller.MainController;
import it.polimi.ingsw.gc03.networking.AsyncLogger;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;


/**
 * The ClientHandler class processes incoming requests from clients to perform actions like creating, joining, leaving,
 * or reconnecting to a game.
 * Communication occurs via the Socket Network protocol.
 */
public class ClientHandler implements Runnable {

    /**
     * The socket associated with the client.
     */
    private final Socket socketClient;

    /**
     * The nickname of the client.
     */
    private String nicknameClient;

    /**
     * The game controller for the game session.
     */
    private GameController gameController; //Oppure interfaccia???

    /**
     * Listener for client socket messages.
     */
    private final GameListenersHandleSocket gameListenersHandleSocket;

    /**
     * Input stream to receive data from the client.
     */
    private ObjectInputStream inputStream;

    /**
     * Output stream to send data to the client.
     */
    private ObjectOutputStream outputStream;

    /**
     * Queue for managing incoming messages.
     */
    private final LinkedBlockingQueue<SocketClientGenericMessage> messagesQueue = new LinkedBlockingQueue<>();

    /**
     * Executor for running game logic.
     */
    private final ExecutorService gameLogicExecutor = Executors.newSingleThreadExecutor();


    /**
     * Constructs a handler for a specific client socket.
     * @param socketClient The socket connected to the client.
     * @throws IOException If an I/O error occurs when creating input/output streams.
     */
    public ClientHandler(Socket socketClient) throws IOException {
        this.socketClient = socketClient;
        this.nicknameClient = null;
        this.gameController = null;
        this.inputStream = new ObjectInputStream(socketClient.getInputStream());
        this.outputStream = new ObjectOutputStream(socketClient.getOutputStream());
        this.gameListenersHandleSocket = new GameListenersHandleSocket(this.outputStream);
    }


    /**
     * Updates the GameController and the client's nickname based on the provided controller.
     * If the controller is non-null, it retrieves the nickname from the controller. If the controller is null,
     * it sets the nickname to null, indicating no valid controller is associated.
     * @param controller The GameController instance to set. This controller may be null if the previous execution did
     *                   not result in a valid controller.
     */
    private void updateGameControllerAndNickname(GameController controller) {
        this.gameController = controller;
        this.nicknameClient = Optional.ofNullable(controller).map(GameController::getNicknameClient).orElse(null);
    }


    /**
     * Processes a message that is intended for the MainController. This method extracts the game controller
     * from the message and updates the current game controller and the client's nickname associated with this handler.
     * @param message The socket client generic message intended for the MainController.
     *                This message should contain the necessary information to fetch or create a GameController instance.
     */
    private void processMessageForMainController(SocketClientGenericMessage message) {
        GameController controller = message.execute(this.gameListenersHandleSocket, MainController.getInstance());
        // Assigns the controller and updates the nickname
        updateGameControllerAndNickname(controller);
    }


    /**
     * Executes the game logic for the client.
     */
    private void runGameLogic() {
        // Process messages from the queue
        try {
            while (!Thread.currentThread().isInterrupted()) {
                SocketClientGenericMessage message = this.messagesQueue.take();
                // Execute game actions based on message type
                if (message.isMessageForMainController())
                    processMessageForMainController(message);
                else if (!message.isPing())
                    message.execute(this.gameController);
            }
        } catch (RemoteException | GameEndedException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException ignored) {
            // Thread interrupted, exit gracefully
        }
    }


    /**
     * Gracefully shuts down the game logic executor.
     */
    private void shutdownGameLogicExecutor() {
        this.gameLogicExecutor.shutdown();
        try {
            if (!this.gameLogicExecutor.awaitTermination(60, TimeUnit.SECONDS))
                this.gameLogicExecutor.shutdownNow();
        } catch (InterruptedException e) {
            this.gameLogicExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }


    /**
     * Interrupts the client handler thread.
     */
    public void interruptThread() {
        this.gameLogicExecutor.shutdownNow();
        Thread.currentThread().interrupt();
    }


    /**
     * Processes all actions received from the client, delegating them to the appropriate game controller.
     */
    @Override
    public void run() {
        gameLogicExecutor.submit(this::runGameLogic);
        // Handle incoming messages in a loop
        try {
            SocketClientGenericMessage message;
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    message = (SocketClientGenericMessage) this.inputStream.readObject();
                    // Process ping and other messages
                    if (message.isPing() && !message.isMessageForMainController()) {
                        if (this.gameController != null)
                            this.gameController.ping(message.getNicknameClient(), this.gameListenersHandleSocket);
                    } else {
                        this.messagesQueue.add(message);
                    }
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            AsyncLogger.log(Level.SEVERE, "[SERVER SOCKET] The socket connection between the server " +
                    "and the client was interrupted due to a communication failure: " + e.getMessage());
            return;
        } finally {
            shutdownGameLogicExecutor();
        }
    }


}