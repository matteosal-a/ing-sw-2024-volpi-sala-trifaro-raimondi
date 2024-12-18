package it.polimi.ingsw.gc03.controller;

import it.polimi.ingsw.gc03.listeners.GameListener;
import it.polimi.ingsw.gc03.model.enumerations.GameStatus;
import it.polimi.ingsw.gc03.model.exceptions.NoSuchGameException;
import it.polimi.ingsw.gc03.networking.rmi.GameControllerInterface;
import it.polimi.ingsw.gc03.networking.rmi.MainControllerInterface;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * MainController is a singleton that manages the creation and joining of games.
 */
public class MainController implements MainControllerInterface, Serializable {

    /**
     * The singleton instance of MainController.
     */
    private static MainController instance = null;

    /**
     * List of game controllers managing individual games.
     */
    private List<GameController> gameControllers;

    /**
     * Private constructor to prevent instantiation.
     */
    private MainController() {
        gameControllers = new ArrayList<>();
    }

    /**
     * Retrieves the singleton instance of the MainController.
     * @return The singleton instance of MainController.
     */
    public synchronized static MainController getInstance() {
        if (instance == null) {
            instance = new MainController();
        }
        return instance;
    }

    /**
     * Creates a new game and adds the first player to it.
     * @param gameListener The first player's listener for game events.
     * @param firstPlayerNickname The nickname of the first player.
     * @return The created GameControllerInterface.
     * @throws RemoteException If there is a remote communication error.
     */
    public synchronized GameControllerInterface createGame(GameListener gameListener, String firstPlayerNickname) throws RemoteException {
        try {
            GameController controller = new GameController();
            gameControllers.add(controller);
            joinSpecificGame(gameListener, firstPlayerNickname, controller.getGame().getIdGame());
            controller.getGame().getListener().notifyGameCreated(controller.getGame());
            return controller;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    /**
     * Add a player to the first available game that has waiting status and available slots.
     * @param listener The player's listener for game events.
     * @param playerNickname The nickname of the player.
     * @return The GameControllerInterface for the game joined.
     * @throws RemoteException If there is a remote communication error.
     */
    public synchronized GameControllerInterface joinFirstAvailableGame(GameListener listener, String playerNickname) throws RemoteException {
            List<GameController> GCs = gameControllers.stream()
                    .filter(x -> (x.getGame().getStatus().equals(GameStatus.WAITING) && x.getGame().getPlayers().size() < x.getGame().getSize()))
                    .toList();
            return addPlayerToGame(playerNickname, listener, GCs);
    }

    /**
     * Adds a player to a specific game by its ID.
     * @param listener The listener for game events.
     * @param playerNickname The nickname of the player.
     * @param id The ID of the game to join.
     * @return The GameControllerInterface for the game joined.
     * @throws RemoteException If there is a remote communication error.
     */
    public synchronized GameControllerInterface joinSpecificGame(GameListener listener, String playerNickname, int id) throws RemoteException {
        try {
            List<GameController> GCs = gameControllers.stream()
                    .filter(x -> (x.getGame().getIdGame() == id))
                    .toList();
            return addPlayerToGame(playerNickname, listener, GCs);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    /**
     * Adds a player to the first element of the passed GameController list.
     * If no game is available, a new game is created.
     * @param playerNickname The nickname of the player.
     * @param listener The player's listener for game events.
     * @param GCs The list of GameControllers.
     * @return The GameControllerInterface for the game joined or created.
     * @throws RemoteException If there is a remote communication error.
     */
    private GameControllerInterface addPlayerToGame(String playerNickname, GameListener listener, List<GameController> GCs) throws RemoteException {
        if (!GCs.isEmpty()) {
            try {
                GCs.get(0).addPlayerToGame(playerNickname, listener);
                return GCs.get(0);
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            return createGame(listener, playerNickname);
        }
        return null;
    }

    /**
     * Reconnects a player to an ongoing game they were previously in.
     * @param gameListener The player's listener for game events.
     * @param playerNickname The nickname of the player.
     * @return The GameControllerInterface for the game reconnected to.
     * @throws RemoteException If there is a remote communication error.
     */
    public synchronized GameControllerInterface reconnectToGame(GameListener gameListener, String playerNickname) throws RemoteException {
        List<GameController> GCs = gameControllers.stream()
                .filter(gc -> gc.getGame().getPlayers().stream()
                        .anyMatch(p -> p.getNickname().equals(playerNickname)))
                .toList();

        if (!GCs.isEmpty()) {
            if (!GCs.get(0).getGame().getPlayers().stream()
                    .filter(p -> p.getNickname().equals(playerNickname))
                    .toList().get(0).getOnline()) {
                try {
                    GCs.get(0).reconnectPlayer(playerNickname, gameListener);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                return GCs.get(0);
            }
        } else {
            gameListener.noGameToReconnect(null, playerNickname);
        }
        return null;
    }

    /**
     * Deletes a game by its ID.
     * @param idGame The ID of the game to delete.
     * @throws NoSuchGameException If the game with the specified ID does not exist.
     */
    public synchronized void deleteGame(int idGame) throws NoSuchGameException {
        List<GameController> gameToRemove = gameControllers.stream()
                .filter(x -> (x.getGame().getIdGame() == idGame))
                .toList();
        if (!gameToRemove.isEmpty()) {
            gameControllers.remove(gameToRemove.get(0));
        } else {
            throw new NoSuchGameException();
        }
    }

    /**
     * Returns the list of GameControllers.
     * @return The list of GameControllers.
     */
    public List<GameController> getGameControllers() {
        return gameControllers;
    }

    /**
     * Resets the singleton instance of MainController.
     */
    public static void resetInstance() {
        instance = null;
    }

}