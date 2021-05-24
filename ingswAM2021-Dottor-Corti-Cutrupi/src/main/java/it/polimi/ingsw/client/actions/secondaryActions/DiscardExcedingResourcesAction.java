package it.polimi.ingsw.client.actions.secondaryActions;

import it.polimi.ingsw.controller.GameHandler;
import it.polimi.ingsw.server.messages.printableMessages.IncorrectPhaseMessage;
import it.polimi.ingsw.model.resource.ResourceType;

import java.util.ArrayList;

public class DiscardExcedingResourcesAction implements SecondaryAction{
    private final ArrayList<ResourceType> resources;

    public DiscardExcedingResourcesAction(ArrayList<ResourceType> resources) {
        this.resources = resources;
    }

    public ArrayList<ResourceType> getResources() {
        return resources;
    }

    @Override
    public void execute(GameHandler gameHandler) {
        if(gameHandler.actionPerformedOfActivePlayer()==4)  gameHandler.discardExtraResources(resources);
        else gameHandler.sendMessageToActivePlayer(new IncorrectPhaseMessage());
    }
}
