package it.polimi.ingsw.client.actions.secondaryActions;

import it.polimi.ingsw.controller.GameHandler;
import it.polimi.ingsw.server.messages.printableMessages.YouMustDeleteADepotFirst;
import it.polimi.ingsw.server.messages.printableMessages.YouMustDiscardResourcesFirst;
import it.polimi.ingsw.server.messages.printableMessages.YouMustSelectWhiteToColorsFirst;

/**
 * Action used to activate a leader card. Contains the index to access the card via leader card zone.
 */
public class ActivateLeaderCardAction implements SecondaryAction{
    private final int index;

    /**
     * @param index: index of the card to activate
     */
    public ActivateLeaderCardAction(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }


    @Override
    public void execute(GameHandler gameHandler) {
        int num=gameHandler.actionPerformedOfActivePlayer();
        if(num==3)  gameHandler.sendAllExceptActivePlayer(new YouMustDeleteADepotFirst());
        else if(num==4)     gameHandler.sendAllExceptActivePlayer(new YouMustDiscardResourcesFirst());
        else if(num==5)     gameHandler.sendAllExceptActivePlayer(new YouMustSelectWhiteToColorsFirst());
        gameHandler.activateLeaderCard(index);
    }
}
