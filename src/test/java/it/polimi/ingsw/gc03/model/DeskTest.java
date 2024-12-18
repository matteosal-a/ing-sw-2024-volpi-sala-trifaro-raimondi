package it.polimi.ingsw.gc03.model;

import it.polimi.ingsw.gc03.model.card.Card;
import it.polimi.ingsw.gc03.model.card.CardGold;
import it.polimi.ingsw.gc03.model.card.CardResource;
import it.polimi.ingsw.gc03.model.card.CardStarter;
import it.polimi.ingsw.gc03.model.card.cardObjective.CardObjective;
import it.polimi.ingsw.gc03.model.enumerations.DeckType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DeskTest {

    private Desk desk;

    private Game game;


    @BeforeEach
    void setUp() throws RemoteException {
        game = new Game(554544551);
        desk = new Desk(game);
    }

    @AfterEach
    void tearDown() {
        game = null;
        desk = null;
    }

    /**
     * Check if drawCard get the right card from ResourceDeck and if it removes the card from the ResourceDeck
     */
    @Test
    void drawCardDeckResource() throws RemoteException {
        Card firstCard = desk.getDeckResource().getFirst();
        Card cardResource = desk.drawCardDeck(DeckType.DECK_RESOURCE);
        assertEquals(cardResource,firstCard);
        assertFalse(desk.getDeckResource().contains(cardResource));
    }

    /**
     * Check if drawCard get the right card from GoldDeck and if it removes the card from the GoldDeck
     */
    @Test
    void drawCardDeckGold() throws RemoteException {
        Card firstCard = desk.getDeckGold().getFirst();
        Card cardGold = desk.drawCardDeck(DeckType.DECK_GOLD);
        assertEquals(cardGold,firstCard);
        assertFalse(desk.getDeckGold().contains(cardGold));
    }

    /**
     * Check if drawCard get the right card from StarterDeck and if it removes the card from the StarterDeck
     */
    @Test
    void drawCardDeckStarter() throws RemoteException {
        Card firstCard = desk.getDeckStarter().getFirst();
        Card cardStarter = desk.drawCardDeck(DeckType.DECK_STARTER);
        assertEquals(cardStarter,firstCard);
        assertFalse(desk.getDeckStarter().contains(cardStarter));
    }

    /**
     * Check if drawCard get the right card from ObjectiveDeck and if it removes the card from the ObjectiveDeck
     */
    @Test
    void drawCardDeckObjective() throws RemoteException {
        Card firstCard = desk.getDeckObjective().getFirst();
        Card cardObjective = desk.drawCardDeck(DeckType.DECK_OBJECTIVE);
        assertEquals(cardObjective,firstCard);
        assertFalse(desk.getDeckObjective().contains(cardObjective));
    }

    /**
     * Check what happens when the deck is empty
     */

    @Test
    void drawCardDeckEmpty() throws RemoteException {
        Card card;
        //Empty the Deck;
        desk.getDeckGold().clear();
        card = desk.drawCardDeck(DeckType.DECK_GOLD);
        assertEquals(null,card);
    }

    /**
     * Check if drawCardDisplayed returns the correct GoldCard
     */
    @Test
    void drawCardDisplayedGold() throws RemoteException {
        int i = 1;
        Card displayedCard = desk.getDisplayedGold().get(i);
        Card card = desk.drawCardDisplayed(DeckType.DISPLAYED_GOLD,i);
        assertEquals(card,displayedCard);
    }

    /**
     * Check if drawCardDisplayed returns the correct ResourceCard
     */
    @Test
    void drawCardDisplayedResource() throws RemoteException {
        int i = 1;
        Card displayedCard = desk.getDisplayedResource().get(i);
        Card card = desk.drawCardDisplayed(DeckType.DISPLAYED_RESOURCE,i);
        assertEquals(card,displayedCard);
    }

    /**
     * Check if drawCardDisplayed returns the correct GoldCard;
     */
    @Test
    void drawCardDisplayedGoldEmpty() throws RemoteException {
        Card card;
        int i=0;
        desk.getDisplayedGold().clear();
        card = desk.drawCardDisplayed(DeckType.DISPLAYED_GOLD,i);
        assertEquals(null,card);
    }

    /**
     * Check if drawCardDisplayed return null if displayedResource is Empty
     */
    @Test
    void drawCardDisplayedResourceEmpty() throws RemoteException {
        Card card;
        int i=0;
        desk.getDisplayedResource().clear();
        card = desk.drawCardDisplayed(DeckType.DISPLAYED_RESOURCE,i);
        assertEquals(null,card);
    }

    /**
     * Check if drawCardDisplayed return null if index is higher than displayed.size()
     */
    @Test
    void drawCardDisplayedWrongIndexOver() throws RemoteException {
        int i=2;
        Card card = desk.drawCardDisplayed(DeckType.DISPLAYED_GOLD,i);
        assertEquals(card,null);
    }

    /**
     * Check if drawCardDisplayed return null if index is lower than displayed.size()
     */
    @Test
    void drawCardDisplayedWrongIndexUnder() throws RemoteException {
        int i=-1;
        Card card = desk.drawCardDisplayed(DeckType.DISPLAYED_GOLD,i);
        assertEquals(card,null);
    }

    @Test
    void getDeckStarter() {
    }

    @Test
    void setDeckStarter() throws RemoteException {
        Game game1 = new Game(555454454);
        Desk desk1 = new Desk(game1);
        ArrayList<CardStarter> deckStarter = desk.getDeckStarter();
        desk.setDeckStarter(desk1.getDeckStarter());
        assertEquals(desk.getDeckStarter(),desk1.getDeckStarter());
        assertNotEquals(deckStarter,desk.getDeckStarter());
    }

    @Test
    void getDeckResource() {
    }

    @Test
    void setDeckResource() throws RemoteException {
        Game game1 = new Game(555454454);
        Desk desk1 = new Desk(game1);
        ArrayList<CardResource> deckResource = desk.getDeckResource();
        desk.setDeckResource(desk1.getDeckResource());
        assertEquals(desk.getDeckResource(),desk1.getDeckResource());
        assertNotEquals(deckResource,desk.getDeckResource());
    }

    @Test
    void setDeckGold() throws RemoteException {
        Game game1 = new Game(555454454);
        Desk desk1 = new Desk(game1);
        ArrayList<CardGold> deckGold = desk.getDeckGold();
        desk.setDeckGold(desk1.getDeckGold());
        assertEquals(desk.getDeckGold(),desk1.getDeckGold());
        assertNotEquals(deckGold,desk.getDeckGold());
    }

    @Test
    void getDeckObjective() {
    }

    @Test
    void setDeckObjective() throws RemoteException {
        Game game1 = new Game(555454454);
        Desk desk1 = new Desk(game1);
        ArrayList<CardObjective> deckObjective = desk.getDeckObjective();
        desk.setDeckObjective(desk1.getDeckObjective());
        assertEquals(desk.getDeckObjective(),desk1.getDeckObjective());
        assertNotEquals(deckObjective,desk.getDeckObjective());

    }

    @Test
    void setDisplayedResource() throws RemoteException {
        Game game1 = new Game(555454454);
        Desk desk1 = new Desk(game1);
        ArrayList<Card> displayedResource= desk.getDisplayedResource();
        desk.setDisplayedResource(desk1.getDisplayedResource());
        assertEquals(desk.getDisplayedResource(),desk1.getDisplayedResource());
        assertNotEquals(displayedResource,desk.getDisplayedResource());
    }

    @Test
    void setDisplayedGold() throws RemoteException {
        Game game1 = new Game(555454454);
        Desk desk1 = new Desk(game1);
        ArrayList<Card> displayedGold = desk.getDisplayedGold();
        desk.setDisplayedGold(desk1.getDisplayedGold());
        assertEquals(desk.getDisplayedGold(),desk1.getDisplayedGold());
        assertNotEquals(displayedGold,desk.getDisplayedGold());
    }

    @Test
    void setDisplayedObjective() throws RemoteException {
        Game game1 = new Game(555454454);
        Desk desk1 = new Desk(game1);
        ArrayList<CardObjective> displayedObjective = desk.getDisplayedObjective();
        desk.setDisplayedObjective(desk1.getDisplayedObjective());
        assertEquals(desk.getDisplayedObjective(),desk1.getDisplayedObjective());
        assertNotEquals(displayedObjective,desk.getDisplayedObjective());
    }
}