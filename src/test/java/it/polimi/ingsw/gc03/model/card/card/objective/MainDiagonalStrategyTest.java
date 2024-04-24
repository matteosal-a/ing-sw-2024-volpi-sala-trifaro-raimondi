package it.polimi.ingsw.gc03.model.card.card.objective;

import it.polimi.ingsw.gc03.model.Codex;
import it.polimi.ingsw.gc03.model.Player;
import it.polimi.ingsw.gc03.model.card.Card;
import it.polimi.ingsw.gc03.model.enumerations.Kingdom;
import it.polimi.ingsw.gc03.model.enumerations.Value;
import it.polimi.ingsw.gc03.model.side.Side;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MainDiagonalStrategyTest {

    private Codex codex;
    CardObjective cardObjective;
    @BeforeEach
    void setUp() {
        this.codex = new Codex();
        CalculateScoreStrategy scoreStrategy = new MainDiagonalStrategy();
        this.cardObjective = new CardObjective("OBJ088",
                "2 points for each main diagonal of cards belonging to the plant kingdom.",
                2,
                new ArrayList<Value>() {{add(Value.PLANT); {add(Value.PLANT); {add(Value.PLANT);}}}},
                scoreStrategy
                );
    }

    @Test
    @DisplayName("A single correct diagonal")
    void singleDiagonal() {
        Side topSide = new Side(Kingdom.PLANT, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY);
        Side middleSide = new Side(Kingdom.PLANT, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY);
        Side bottomSide = new Side(Kingdom.PLANT, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY);
        codex.insertStarterIntoCodex(middleSide);

        codex.insertIntoCodex(topSide, 39, 39);
        codex.insertIntoCodex(bottomSide, 41, 41);
        codex.setPointCodex(cardObjective.calculateScore(codex,
                2,
                new ArrayList<Value>() {
                            {add(Value.PLANT);
                            {add(Value.PLANT);
                            {add(Value.PLANT);
                            }}}}));
        assertEquals(2, codex.getPointCodex());
    }

    @Test
    @DisplayName("4 cards in a diagonal")
    void singleDiagonalPlusOne() {
        Side topSide = new Side(Kingdom.PLANT, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY);
        Side middleSide = new Side(Kingdom.PLANT, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY);
        Side bottomSide = new Side(Kingdom.PLANT, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY);
        Side additionalSide = new Side(Kingdom.PLANT, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY);
        codex.insertStarterIntoCodex(middleSide);

        codex.insertIntoCodex(topSide, 39, 39);
        codex.insertIntoCodex(bottomSide, 41, 41);
        codex.insertIntoCodex(additionalSide, 42, 42);
        codex.setPointCodex(cardObjective.calculateScore(codex,
                2,
                new ArrayList<Value>() {
                        {add(Value.PLANT);
                        {add(Value.PLANT);
                        {add(Value.PLANT);
                        }}}}
        ));
        assertEquals(2, codex.getPointCodex());
    }

    @Test
    @DisplayName("Two correct diagonals")
    void twoDiagonals() {
        Side topSide = new Side(Kingdom.PLANT, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY);
        Side middleSide = new Side(Kingdom.PLANT, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY);
        Side bottomSide = new Side(Kingdom.PLANT, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY);
        Side topSide2 = new Side(Kingdom.PLANT, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY);
        Side middleSide2 = new Side(Kingdom.PLANT, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY);
        Side bottomSide2 = new Side(Kingdom.PLANT, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY);
        codex.insertStarterIntoCodex(middleSide);

        codex.insertIntoCodex(topSide, 39, 39);
        codex.insertIntoCodex(bottomSide, 41, 41);
        codex.insertIntoCodex(topSide2, 42, 42);
        codex.insertIntoCodex(middleSide2, 43, 43);
        codex.insertIntoCodex(bottomSide2, 44, 44);
        codex.setPointCodex(cardObjective.calculateScore(codex,
                2,
                new ArrayList<Value>() {
                            {add(Value.PLANT);
                            {add(Value.PLANT);
                            {add(Value.PLANT);
                        }}}}
        ));
        assertEquals(4, codex.getPointCodex());
    }


}