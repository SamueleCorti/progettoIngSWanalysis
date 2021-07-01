package it.polimi.ingsw.client.gui.utility;

import it.polimi.ingsw.server.messages.showingMessages.LeaderCardMessage;
import it.polimi.ingsw.server.messages.showingMessages.SerializationConverter;
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
    private boolean cardWasModified;


    private IntegerProperty cardIndex = new SimpleIntegerProperty();
    private Image cardImage;
    private StringProperty status = new SimpleStringProperty();
    private CheckBox checkBox;
    private StringProperty specialPowerResourcesProperty = new SimpleStringProperty();

    public LeaderCardForGUI(LeaderCardMessage message) {
        SerializationConverter converter= new SerializationConverter();
        this.needsResources = message.isNeedsResources();
        this.resourcesRequired = message.getResourcesRequired();
        this.developmentCardsRequired = message.getDevelopmentCardsRequired();
        this.specialPower = message.getSpecialPower();
        this.specialPowerResources = message.getSpecialPowerResources();
        this.victoryPoints = message.getVictoryPoints();
        this.leaderCardZone = message.getLeaderCardZone();
        this.cardIndex.set(message.getLeaderCardZone()+1);
        ImageSearcher parser = new ImageSearcher();

        this.cardWasModified = message.isWasCardModified();
        if(!cardWasModified)
        {
            this.path = parser.getImageFromPowerTypeResource(specialPower, converter.getResourceRelatedFromArray(specialPowerResources));
            this.cardName.set(path);
            this.cardImage = new Image("/images/cardsFrontJPG/customleadercard.jpg");
        }else{
            this.cardName.set("customized"+converter.parseIntToSpecialPower(specialPower));
            this.cardImage = new Image ("/images/cardsFrontJPG/customleadercard.jpg");
        }
        this.checkBox = new CheckBox();
        if(message.isActive()) status.set("Active");
        else status.set("Inactive");
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

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public boolean isCardWasModified() {
        return cardWasModified;
    }
}
