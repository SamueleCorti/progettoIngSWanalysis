package it.polimi.ingsw.gui;

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
}