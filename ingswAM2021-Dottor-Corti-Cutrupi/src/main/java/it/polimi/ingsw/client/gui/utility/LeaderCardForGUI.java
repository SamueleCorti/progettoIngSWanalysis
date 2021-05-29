package it.polimi.ingsw.client.gui.utility;

import it.polimi.ingsw.server.messages.jsonMessages.LeaderCardMessage;
import it.polimi.ingsw.server.messages.jsonMessages.SerializationConverter;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;
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
    private IntegerProperty cardIndex = new SimpleIntegerProperty();
    private Image cardImage;
    private CheckBox checkBox;

    public LeaderCardForGUI(LeaderCardMessage message) {
        SerializationConverter converter= new SerializationConverter();
        this.needsResources = message.isNeedsResources();
        this.resourcesRequired = message.getResourcesRequired();
        this.developmentCardsRequired = message.getDevelopmentCardsRequired();
        this.specialPower = message.getSpecialPower();
        this.specialPowerResources = message.getSpecialPowerResources();
        this.victoryPoints = message.getVictoryPoints();
        this.leaderCardZone = message.getLeaderCardZone();
        this.cardIndex.set(message.getLeaderCardZone());
        ImageSearcher parser = new ImageSearcher();
        this.path = parser.getImageFromPowerTypeResource(specialPower,converter.getResourceRelatedFromArray(specialPowerResources));
        this.cardName.set(path);
        this.cardImage = new Image((getClass().getResourceAsStream(path)));
        this.checkBox = new CheckBox();
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

    public CheckBox getCheckbox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkbox) {
        this.checkBox = checkbox;
    }

    public int getCardIndex() {
        return cardIndex.get();
    }

    public IntegerProperty cardIndexProperty() {
        return cardIndex;
    }

    public void setCardIndex(int cardIndex) {
        this.cardIndex.set(cardIndex);
    }
}
