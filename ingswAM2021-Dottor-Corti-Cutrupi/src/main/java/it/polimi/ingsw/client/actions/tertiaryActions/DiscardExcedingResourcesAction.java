package it.polimi.ingsw.client.actions.tertiaryActions;

import it.polimi.ingsw.client.actions.secondaryActions.SecondaryAction;
import it.polimi.ingsw.controller.GameHandler;
import it.polimi.ingsw.server.messages.printableMessages.IncorrectPhaseMessage;
import it.polimi.ingsw.model.resource.ResourceType;

import java.util.ArrayList;

public class DiscardExcedingResourcesAction implements TertiaryAction {
    private final ArrayList<ResourceType> resources;

    public DiscardExcedingResourcesAction(ArrayList<ResourceType> resources) {
        this.resources = resources;
    }

    public ArrayList<ResourceType> getResources() {
        return resources;
    }

    @Override
    public void execute(GameHandler gameHandler) {
        if(gameHandler.actionPerformedOfActivePlayer()==4)  gameHandler.discardExtraResources(resources,-1);
        else gameHandler.sendMessageToActivePlayer(new IncorrectPhaseMessage());
    }

    @Override
    public void execute(GameHandler gameHandler, int clientID) {
        if(gameHandler.turnPhaseGivenNickname(clientID)==4)  gameHandler.discardExtraResources(resources,clientID);
        else gameHandler.sendMessage(new IncorrectPhaseMessage(),clientID);
    }
}
