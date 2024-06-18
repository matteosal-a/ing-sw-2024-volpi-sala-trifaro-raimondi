package it.polimi.ingsw.gc03.view.gui;

/**
 * The SceneEnum class holds the file paths for the FXML files of the various scenes.
 */
public enum SceneEnum {

    /**
     * NICKNAME is the scene where the user is asked to enter their nickname.
     */
    NICKNAME("/it/polimi/ingsw/gc03/fxml/nickname.fxml"),

    /**
     * MENU is the scene that displays the menu with possible actions for the player to choose from.
     */
    MENU("/it/polimi/ingsw/gc03/fxml/menu.fxml"),

    /**
     * GAME_ID is the scene where the user is asked to enter the game ID.
     */
    GAME_ID("/it/polimi/ingsw/gc03/fxml/gameId.fxml"),

    /**
     * LOBBY2 is the scene that represents the lobby for a two-player game.
     */
    LOBBY2("/it/polimi/ingsw/gc03/fxml/lobby2.fxml"),

    /**
     * LOBBY3 is the scene that represents the lobby for a three-player game.
     */
    LOBBY3("/it/polimi/ingsw/gc03/fxml/lobby3.fxml"),

    /**
     * LOBBY4 is the scene that represents the lobby for a four-player game.
     */
    LOBBY4("/it/polimi/ingsw/gc03/fxml/lobby4.fxml"),

    /**
     * LOBBY_PLAYER1 is the scene that represents the first player in the lobby.
     */
    LOBBY_PLAYER1("/it/polimi/ingsw/gc03/fxml/lobbyPlayer1.fxml"),

    /**
     * LOBBY_PLAYER2 is the scene that represents the second player in the lobby.
     */
    LOBBY_PLAYER2("/it/polimi/ingsw/gc03/fxml/lobbyPlayer2.fxml"),

    /**
     * LOBBY_PLAYER3 is the scene that represents the third player in the lobby.
     */
    LOBBY_PLAYER3("/it/polimi/ingsw/gc03/fxml/lobbyPlayer3.fxml"),

    /**
     * LOBBY_PLAYER4 is the scene that represents the fourth player in the lobby.
     */
    LOBBY_PLAYER4("/it/polimi/ingsw/gc03/fxml/lobbyPlayer4.fxml"),

    /**
     * CARD_STARTER is the scene where the player is asked to choose the side of their starter card.
     */
    CARD_STARTER("/it/polimi/ingsw/gc03/fxml/cardStarter.fxml"),

    /**
     * CARD_OBJECTIVE is the scene where the player is asked to choose their personal objective card.
     */
    CARD_OBJECTIVE("/it/polimi/ingsw/gc03/fxml/cardObjective.fxml"),

    /**
     * GAME_RUNNING is the scene displayed during the actual execution of the game.
     */
    GAME_RUNNING("/it/polimi/ingsw/gc03/fxml/gameRunning.fxml"),

    /**
     * WINNERS is the final scene where the players' scores and the winners of the game are displayed.
     */
    WINNERS("/it/polimi/ingsw/gc03/fxml/winners.fxml"),

    /**
     * ERROR is the scene displayed in case an error occurs.
     */
    ERROR("/it/polimi/ingsw/gc03/fxml/error.fxml");

    private final String value;

    SceneEnum(final String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

}
