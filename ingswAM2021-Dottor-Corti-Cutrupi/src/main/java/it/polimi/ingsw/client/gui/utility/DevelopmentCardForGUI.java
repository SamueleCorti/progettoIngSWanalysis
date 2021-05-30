package it.polimi.ingsw.client.gui.utility;

import it.polimi.ingsw.server.messages.jsonMessages.DevelopmentCardMessage;

public class DevelopmentCardForGUI{


    private final int[] cardPrice;
    private final int level;
    private final int color;
    private final int[] prodRequirements;
    private final int[] prodResults;
    private final int victoryPoints;
    int devCardZone;

    public DevelopmentCardForGUI(DevelopmentCardMessage message) {
        cardPrice=message.getCardPrice();
        level= message.getLevel();
        color= message.getColor();
        prodRequirements= message.getProdRequirements();
        prodResults=message.getProdResults();
        victoryPoints= message.getVictoryPoints();
        devCardZone= message.getDevCardZone();
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
}