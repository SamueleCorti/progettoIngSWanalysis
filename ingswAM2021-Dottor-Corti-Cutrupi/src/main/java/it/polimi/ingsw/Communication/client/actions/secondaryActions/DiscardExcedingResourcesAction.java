package it.polimi.ingsw.Communication.client.actions.secondaryActions;

import it.polimi.ingsw.Model.resource.ResourceType;

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
