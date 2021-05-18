package it.polimi.ingsw.Communication.client.actions.secondaryActions;

import it.polimi.ingsw.Communication.client.actions.Action;

public class DiscardLeaderCard implements SecondaryAction {
    private final int index;

    public DiscardLeaderCard(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return "DiscardLeaderCard{" +
                "index=" + index +
                '}';
    }
}
