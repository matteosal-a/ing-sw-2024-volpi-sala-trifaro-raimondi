package it.polimi.ingsw.gc03.networking.socket.messages.serverToClientMessages;

import it.polimi.ingsw.gc03.listeners.GameListener;
import java.io.IOException;


/**
 * This class is used to send a message from the server to the client to inform that the game id doesn't exist.
 */
public class SocketServerMessageGameIdNotExists extends SocketServerGenericMessage {

    /**
     * The game id.
     */
    private int gameId;


    /**
     * Constructor of the class that creates the message.
     * @param gameId The game id.
     */
    public SocketServerMessageGameIdNotExists(int gameId) {
        this.gameId = gameId;
    }


    /**
     * Executes the appropriate action based on the content of the message.
     * @param gameListener The game listener to which this message's actions are directed.
     * @throws IOException If an input or output exception occurs during message processing.
     * @throws InterruptedException If the thread running the method is interrupted.
     */
    @Override
    public void execute(GameListener gameListener) throws IOException, InterruptedException {
        gameListener.gameIdNotExists(this.gameId);
    }


}