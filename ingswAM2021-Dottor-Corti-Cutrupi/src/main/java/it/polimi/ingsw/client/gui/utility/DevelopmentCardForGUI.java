package it.polimi.ingsw.client.gui.utility;

import it.polimi.ingsw.server.messages.showingMessages.DevelopmentCardMessage;
import it.polimi.ingsw.server.messages.showingMessages.SerializationConverter;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DevelopmentCardForGUI{


    private final int[] cardPrice;
    private final int level;
    private final int color;
    private final int[] prodRequirements;
    private final int[] prodResults;
    private final int victoryPoints;
    private int devCardZone;
    private StringProperty cardName = new SimpleStringProperty();
    private StringProperty prodCost = new SimpleStringProperty();
    SerializationConverter converter=new SerializationConverter();
    boolean wasCardModified;

    public DevelopmentCardForGUI(DevelopmentCardMessage message) {
        String string="Development card in zone "+ devCardZone;
        this.cardName.set(string);
        cardPrice=message.getCardPrice();
        level= message.getLevel();
        color= message.getColor();
        prodRequirements= message.getProdRequirements();
        prodResults=message.getProdResults();
        victoryPoints= message.getVictoryPoints();
        devCardZone= message.getDevCardZone();
        wasCardModified = message.isWasCardModified();
    }

    public int[] getCardPrice() {
        return cardPrice;
    }

    public int getLevel() {
        return level;
    }

    public int getColor() {
        return color;
    }

    public int[] getProdRequirements() {
        return prodRequirements;
    }

    public int[] getProdResults() {
        return prodResults;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public int getDevCardZone() {
        return devCardZone;
    }

    public String getCardName() {
        return cardName.get();
    }

    public StringProperty cardNameProperty() {
        return cardName;
    }

    public boolean isWasCardModified() {
        return wasCardModified;
    }
}