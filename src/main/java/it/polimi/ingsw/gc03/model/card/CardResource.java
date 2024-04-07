package it.polimi.ingsw.gc03.model.card;

import it.polimi.ingsw.gc03.model.side.back.BackSide;
import it.polimi.ingsw.gc03.model.side.front.FrontResource;
import it.polimi.ingsw.gc03.model.enumerations.Kingdom;
import it.polimi.ingsw.gc03.model.enumerations.Value;

import java.util.ArrayList;

/**
 * This class represents a Resource card.
 */
public class CardResource extends Card {

    /**
     * Kingdom to which the card belongs.
     */
    private Kingdom kingdom;

    /**
     * The front side of the card.
     */
    private FrontResource frontResource;

    /**
     * The back side of the card.
     */
    private  BackSide backResource;


    /**
     * Constructor for the CardResource class.
     * @param idCard The unique identifier of the card.
     * @param kingdom The kingdom to which the card belongs.
     * @param frontResource The front side of the card.
     * @param backResource The back side of the card.
     */
    public CardResource(String idCard, Kingdom kingdom, FrontResource frontResource, BackSide backResource) {
        super(idCard, true);
        this.kingdom = kingdom;
        this.frontResource = frontResource;
        this.backResource = backResource;
    }


    /**
     * Getter method to retrieve the kingdom to which the card belongs.
     * @return The kingdom of the card.
     */
    public Kingdom getKingdom() {
        return kingdom;
    }


    /**
     * Setter method to set the kingdom of the card.
     * @param kingdom The kingdom to set.
     */
    public void setKingdom(Kingdom kingdom) {
        this.kingdom = kingdom;
    }


    /**
     * Getter method to retrieve the front side of the card.
     * @return The front side of the card.
     */
    public FrontResource getFrontResource() {
        return frontResource;
    }


    /**
     * Setter method to set the front side of the card.
     * @param frontResource front of ResourceCard.
     */
    public void setFrontResource(FrontResource frontResource) {
        this.frontResource = frontResource;
    }


    /**
     * Getter method to retrieve the back side of the card.
     * @return The back side of the card.
     */
    public BackSide getBackResource() {
        return backResource;
    }


    /**
     * Setter method to set the back side of the card.
     * @param backResource back of ResourceCard.
     */
    public void setBackResource(BackSide backResource){
        this.backResource=backResource;
    }


    /**
     * Method for printing all the information on a Resource card.
     * @param cardResource The Resource card you want to print.
     */
    public void printCardResource(CardResource cardResource) {
        // General information
        System.out.println("RESOURCE CARD:");
        System.out.println("Card ID: " + getIdCard());
        System.out.println("Kingdom: " + cardResource.getKingdom());
        // Information on the front
        System.out.println("FRONT SIDE:");
        System.out.println("Top Left Corner: " + frontResource.getTopLeftCorner());
        System.out.println("Bottom Left Corner: " + frontResource.getBottomLeftCorner());
        System.out.println("Top Right Corner: " + frontResource.getTopRightCorner());
        System.out.println("Bottom Right Corner: " + frontResource.getBottomRightCorner());
        System.out.println("Points: " + frontResource.getPoint());
        // Information on the back
        System.out.println("BACK SIDE:");
        System.out.println("Top Left Corner: " + backResource.getTopLeftCorner());
        System.out.println("Bottom Left Corner: " + backResource.getBottomLeftCorner());
        System.out.println("Top Right Corner: " + backResource.getTopRightCorner());
        System.out.println("Bottom Right Corner: " + backResource.getBottomRightCorner());
        ArrayList<Value> center = backResource.getCenter();
        System.out.println("Center: " + center.get(0));
    }


}