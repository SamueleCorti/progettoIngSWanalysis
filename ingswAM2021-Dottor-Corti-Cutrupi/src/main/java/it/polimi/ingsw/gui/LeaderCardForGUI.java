package it.polimi.ingsw.gui;

import it.polimi.ingsw.server.messages.jsonMessages.DevelopmentCardMessage;
import it.polimi.ingsw.server.messages.jsonMessages.LeaderCardMessage;
import it.polimi.ingsw.server.messages.jsonMessages.SerializationConverter;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class LeaderCardForGUI {
    private String path;
    private StringProperty cardName = new SimpleStringProperty();
    private boolean needsResources;
    private int[] resourcesRequired;
    private int[] developmentCardsRequired;
    private int specialPower;
    private int[] specialPowerResources;
    private int victoryPoints;
    private int leaderCardZone;
    private Image cardImage;

    public LeaderCardForGUI(LeaderCardMessage message) {
        SerializationConverter converter= new SerializationConverter();
        this.needsResources = message.isNeedsResources();
        this.resourcesRequired = message.getResourcesRequired();
        this.developmentCardsRequired = message.getDevelopmentCardsRequired();
        this.specialPower = message.getSpecialPower();
        this.specialPowerResources = message.getSpecialPowerResources();
        this.victoryPoints = message.getVictoryPoints();
        this.leaderCardZone = message.getLeaderCardZone();
        Parser parser = new Parser();
        this.path = parser.getImageFromPowerTypeResource(specialPower,converter.getResourceRelatedFromArray(specialPowerResources));
        this.cardName.set(path);
        this.cardImage = new Image((getClass().getResourceAsStream(path)));
    }

    public String getCardName() {
        return cardName.get();
    }

    public StringProperty cardNameProperty() {
        return cardName;
    }

    public String getPath() {
        return path;
    }

    public boolean isNeedsResources() {
        return needsResources;
    }

    public int[] getResourcesRequired() {
        return resourcesRequired;
    }

    public int[] getDevelopmentCardsRequired() {
        return developmentCardsRequired;
    }

    public int getSpecialPower() {
        return specialPower;
    }

    public int[] getSpecialPowerResources() {
        return specialPowerResources;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public int getLeaderCardZone() {
        return leaderCardZone;
    }

    public Image getCardImage() {
        return cardImage;
    }
}