package it.polimi.ingsw.client.actions.secondaryActions;

import it.polimi.ingsw.controller.GameHandler;

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

    @Override
    public void execute(GameHandler gameHandler) {
        gameHandler.discardLeaderCard(index);
    }
}
