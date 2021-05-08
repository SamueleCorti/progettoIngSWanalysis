package it.polimi.ingsw.Communication.client.messages.actions.mainActions;

import it.polimi.ingsw.Communication.client.messages.actions.mainActions.MainAction;
import it.polimi.ingsw.Player;
import it.polimi.ingsw.developmentcard.Color;

public class DevelopmentAction implements MainAction {
    Player player;
    Color color;
    int cardLevel;
    int index;

    public DevelopmentAction(Player player, Color color, int cardLevel, int index) {
        this.player = player;
        this.color = color;
        this.cardLevel = cardLevel;
        this.index = index;
    }

    public Player getPlayer() {
        return player;
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
