package it.polimi.ingsw.gc03.model;

import it.polimi.ingsw.gc03.listeners.GameListener;
import it.polimi.ingsw.gc03.model.card.Card;
import it.polimi.ingsw.gc03.model.card.cardObjective.CalculateScoreStrategy;
import it.polimi.ingsw.gc03.model.card.cardObjective.CardObjective;
import it.polimi.ingsw.gc03.model.card.cardObjective.MainDiagonalStrategy;
import it.polimi.ingsw.gc03.model.enumerations.Kingdom;
import it.polimi.ingsw.gc03.model.enumerations.Value;
import it.polimi.ingsw.gc03.model.side.Side;
import it.polimi.ingsw.gc03.view.OptionSelection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.Flow;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;

    private Desk desk;

    private Game game;
    private GameListener gameListener;



    @BeforeEach
    void setUp() throws RemoteException {
        game = new Game(554544551);
        desk = new Desk(game);
        String serverId = "TestIpServer";
        player= new Player("Testname",1,desk,game,gameListener);
    }

    @AfterEach
    void tearDown() {
        desk = null;
        player = null;
    }
    /**
     * Check if selectObjectiveCard returns false when the index is over CardObjective size
     */
    @Test
    void selectObjectiveCardFalseOver() {
        int index = 3;
        assertFalse(player.selectObjectiveCard(index,game));
    }

    /**
     * Check if selectObjectiveCard returns false when the index is under CardObjective size
     */
    @Test
    void selectObjectiveCardFalseUnder() {
        int index = -1;
        assertFalse(player.selectObjectiveCard(index,game));
    }
    /**
     * Check if selectObjectiveCard returns true when the index is correct
     */
    @Test
    void selectObjectiveCardTrue() {
        int index = 1;
        assertTrue(player.selectObjectiveCard(index,game));
    }

    /**
     * Check if the score is calculated correctly
     */
    @Test
    void calculatePlayerScore() {
        player.getCodex().setPointCodex(8);
        player.setPointObjective(7);
        player.calculatePlayerScore();
        assertEquals(player.getScore(),8+7);
    }

}