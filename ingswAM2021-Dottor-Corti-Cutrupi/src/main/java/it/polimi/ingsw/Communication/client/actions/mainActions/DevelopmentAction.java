package it.polimi.ingsw.Communication.client.actions.mainActions;

import it.polimi.ingsw.developmentcard.Color;

public class DevelopmentAction implements MainAction {
    Color color;
    int cardLevel;
    int index;

    public DevelopmentAction(Color color, int cardLevel, int index) {
        this.color = color;
        this.cardLevel = cardLevel;
        this.index = index;
    }

    public Color getColor() {
        return color;
    }

    public int getCardLevel() {
        return cardLevel;
    }

    public int getIndex() {
        return index;
    }
}
