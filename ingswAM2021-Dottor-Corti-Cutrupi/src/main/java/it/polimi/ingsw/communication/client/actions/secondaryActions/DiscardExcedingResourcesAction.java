package it.polimi.ingsw.communication.client.actions.secondaryActions;

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
}
