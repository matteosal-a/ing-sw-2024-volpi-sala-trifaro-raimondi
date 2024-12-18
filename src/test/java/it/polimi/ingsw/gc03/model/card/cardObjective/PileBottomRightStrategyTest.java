package it.polimi.ingsw.gc03.model.card.cardObjective;

import it.polimi.ingsw.gc03.model.Codex;
import it.polimi.ingsw.gc03.model.Game;
import it.polimi.ingsw.gc03.model.enumerations.Kingdom;
import it.polimi.ingsw.gc03.model.enumerations.Value;
import it.polimi.ingsw.gc03.model.side.Side;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PileBottomRightStrategyTest {
    private Codex codex;

    CardObjective cardObjective;
    private Game game;

    @BeforeEach
    void setup() throws RemoteException {
        this.codex = new Codex();
        CalculateScoreStrategy scoreStrategy = new PileBottomRightStrategy();
        this.cardObjective = new CardObjective("OBJ094",
                "3 points for each pile of cards belonging to the insect kingdom that starts from the bottom-right corner of a card belonging to the animal kingdom.",
                3,
                new ArrayList<Value>() {
                        {add(Value.ANIMAL);
                        {add(Value.INSECT);
                        {add(Value.INSECT);}
                        }}},
                scoreStrategy
        );
        game = new Game(5444616);
    }

    @Test
    @DisplayName("A single correct pile")
    void singlePile() throws RemoteException {
        Side topSide = new Side(Kingdom.ANIMAL, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY);
        Side midSide = new Side(Kingdom.INSECT, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY);
        Side bottomSide = new Side(Kingdom.INSECT, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY);
        Side helperSide = new Side(Kingdom.PLANT, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY);

        codex.insertStarterIntoCodex(midSide,game,"TestName");
        codex.insertIntoCodex(game,topSide, 39,39);
        codex.insertIntoCodex(game,helperSide, 41, 41);
        codex.insertIntoCodex(game,bottomSide, 42,40);

        codex.setPointCodex(cardObjective.calculateScore(codex,
                3,
                new ArrayList<Value>() {
                        {add(Value.ANIMAL);
                        {add(Value.INSECT);
                        {add(Value.INSECT);
                            }}}}));
        assertEquals(3, codex.getPointCodex());
    }

    @Test
    @DisplayName("Two correct piles")
    void twoPiles() throws RemoteException {
        Side topSide = new Side(Kingdom.ANIMAL, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY);
        Side midSide = new Side(Kingdom.INSECT, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY);
        Side bottomSide = new Side(Kingdom.INSECT, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY);

        Side topSide2 = new Side(Kingdom.ANIMAL, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY);
        Side midSide2 = new Side(Kingdom.INSECT, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY);
        Side bottomSide2 = new Side(Kingdom.INSECT, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY);
        Side helperSide = new Side(Kingdom.PLANT, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY);

        codex.insertStarterIntoCodex(midSide,game,"TestName");
        codex.insertIntoCodex(game,topSide, 39,39);
        codex.insertIntoCodex(game,topSide2, 41, 41);
        codex.insertIntoCodex(game,bottomSide, 42,40);

        codex.insertIntoCodex(game,midSide2, 42, 42);
        codex.insertIntoCodex(game,helperSide, 43, 41);
        codex.insertIntoCodex(game,bottomSide2, 44, 42);


        codex.setPointCodex(cardObjective.calculateScore(codex,
                3,
                new ArrayList<Value>() {
                        {add(Value.ANIMAL);
                        {add(Value.INSECT);
                        {add(Value.INSECT);
                        }}}}));
        assertEquals(6, codex.getPointCodex());
    }
}