package it.polimi.ingsw.Communication.client.messages.actions.mainActions;

import it.polimi.ingsw.Communication.client.messages.actions.mainActions.MainAction;
import it.polimi.ingsw.Player;

public class MarketAction implements MainAction {
    private Player player;
    private int index;
    private boolean isRow;

    public MarketAction(Player player, int index, boolean isRow) {
        this.player = player;
        this.index = index;
        this.isRow = isRow;
    }

    public Player getPlayer() {
        return player;
    }

    public int getIndex() {
        return index;
    }

    public boolean isRow() {
        return isRow;
    }
}
