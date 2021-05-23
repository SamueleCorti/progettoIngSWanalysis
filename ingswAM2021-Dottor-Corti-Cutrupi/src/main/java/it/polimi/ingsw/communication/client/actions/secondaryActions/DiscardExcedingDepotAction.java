package it.polimi.ingsw.communication.client.actions.secondaryActions;

import it.polimi.ingsw.communication.server.GameHandler;
import it.polimi.ingsw.communication.server.messages.printableMessages.IncorrectPhaseMessage;

public class DiscardExcedingDepotAction implements SecondaryAction{
    private final int index;

    public int getIndex() {
        return index;
    }

    public DiscardExcedingDepotAction(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "DiscardExcedingDepotAction{" +
                "index=" + index +
                '}';
    }

    @Override
    public void execute(GameHandler gameHandler) {
        if(gameHandler.actionPerformedOfActivePlayer()==3)        gameHandler.discardDepot(index);
        else    gameHandler.sendMessageToActivePlayer(new IncorrectPhaseMessage());
    }
}
