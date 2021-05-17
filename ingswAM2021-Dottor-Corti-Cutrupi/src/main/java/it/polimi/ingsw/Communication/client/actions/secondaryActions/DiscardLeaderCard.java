package it.polimi.ingsw.Communication.client.actions.secondaryActions;

import it.polimi.ingsw.Communication.client.actions.Action;

public class DiscardLeaderCard implements Action {
    private final int index;

    public DiscardLeaderCard(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}