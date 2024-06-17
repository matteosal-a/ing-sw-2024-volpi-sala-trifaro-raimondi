package it.polimi.ingsw.gc03.view.gui.controllers;

import it.polimi.ingsw.gc03.model.GameImmutable;
import it.polimi.ingsw.gc03.model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 * Controller for handling the selection of the Starter card side.
 */
public class CardStarterController extends GenericController {

    /**
     * Button to select the front side of the Starter card.
     */
    @FXML
    private Button frontSide;

    /**
     * Button to select the back side of the Starter card.
     */
    @FXML
    private Button backSide;


    /**
     * Displays the Starter card images on the buttons.
     * @param gameImmutable The game model.
     * @param nickname The nickname of the player.
     */
    public void showCardStarter(GameImmutable gameImmutable, String nickname) {
        // Get player Starter card
        Player player = getPlayer(gameImmutable, nickname);
        String frontImagePath = null;
        String backImagePath = null;
        if (player != null) {
            frontImagePath = player.getCardStarter().getFrontStarter().getImage();
            backImagePath = player.getCardStarter().getBackStarter().getImage();
        }
        // Load images
        if (frontImagePath != null && backImagePath != null) {
            try {
                Image frontImage = new Image("file:" + frontImagePath);
                Image backImage = new Image("file:" + backImagePath);
                // Set images to buttons
                frontSide.setGraphic(new ImageView(frontImage));
                backSide.setGraphic(new ImageView(backImage));
            } catch (Exception e) {
                showError("Error loading images", "There was an error loading the Starter card images.");
                System.exit(1);
            }
        } else {
            showError("Image paths are null", "The image paths for the Starter card are null.");
            System.exit(1);
        }
    }


    /**
     * Get the player based on the nickname.
     * @param gameImmutable The game model.
     * @param nickname The nickname of the player.
     * @return The player.
     */
    private Player getPlayer(GameImmutable gameImmutable, String nickname) {
        for (Player player : gameImmutable.getPlayers()) {
            if (player.getNickname().equals(nickname))
                return player;
        }
        return null;
    }


    /**
     * Show an error message in an alert dialog.
     * @param title The title of the alert.
     * @param message The message to display.
     */
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    /**
     * Handles the click event for the front side of the Starter card.
     * @param actionEvent The action event triggered by the user.
     */
    @FXML
    public void actionClickOnFrontSide(ActionEvent actionEvent) {
        getInputReaderGUI().addTxt("f");
    }


    /**
     * Handles the click event for the back side of the Starter card.
     * @param actionEvent The action event triggered by the user.
     */
    @FXML
    public void actionClickOnBackSide(ActionEvent actionEvent) {
        getInputReaderGUI().addTxt("b");
    }


}