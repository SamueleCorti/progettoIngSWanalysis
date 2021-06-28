package it.polimi.ingsw.client.actions.secondaryActions;

import it.polimi.ingsw.controller.GameHandler;

public class DiscardLeaderCard implements SecondaryAction {
    private final int index;

    /**
     * @param index: index of the card to activate
     */
    public DiscardLeaderCard(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public void execute(GameHandler gameHandler) {
        gameHandler.discardLeaderCard(index);
    }
}
