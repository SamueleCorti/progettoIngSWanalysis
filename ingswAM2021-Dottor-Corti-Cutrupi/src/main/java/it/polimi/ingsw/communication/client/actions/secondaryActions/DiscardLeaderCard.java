package it.polimi.ingsw.communication.client.actions.secondaryActions;

import it.polimi.ingsw.communication.server.GameHandler;

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
