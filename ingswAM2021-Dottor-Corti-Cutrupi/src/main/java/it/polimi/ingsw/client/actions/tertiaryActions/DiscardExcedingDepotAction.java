package it.polimi.ingsw.client.actions.tertiaryActions;

import it.polimi.ingsw.controller.GameHandler;
import it.polimi.ingsw.server.messages.printableMessages.IncorrectPhaseMessage;

/**
 * Used when the player has chosen what depot to discard (after receiving a {@link it.polimi.ingsw.server.messages.DiscardDepotMessage}
 */
public class DiscardExcedingDepotAction implements TertiaryAction {
    private final int index;

    public int getIndex() {
        return index;
    }

    /**
     * @param index: num of the depot to discard
     */
    public DiscardExcedingDepotAction(int index) {
        this.index = index;
    }

    @Override
    public void execute(GameHandler gameHandler) {
        if(gameHandler.actionPerformedOfActivePlayer()==3)        {
            gameHandler.discardDepot(index,-1);
        }
        else    gameHandler.sendMessageToActivePlayer(new IncorrectPhaseMessage());
    }

    /**
     * Calls {@link GameHandler#discardDepot(int index, int clientID)}
     */
    public void execute(GameHandler gameHandler,int clientID) {
        if(gameHandler.turnPhaseGivenNickname(clientID)==3)        {
            gameHandler.discardDepot(index,clientID);
        }
        else    gameHandler.sendMessage(new IncorrectPhaseMessage(),clientID);
    }
}
