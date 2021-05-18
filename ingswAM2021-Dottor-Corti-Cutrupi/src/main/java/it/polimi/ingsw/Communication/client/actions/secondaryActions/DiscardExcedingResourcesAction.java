package it.polimi.ingsw.Communication.client.actions.secondaryActions;

import it.polimi.ingsw.Model.resource.ResourceType;

public class DiscardExcedingResourcesAction {
    private final ResourceType resources;

    public DiscardExcedingResourcesAction(ResourceType resources) {
        this.resources = resources;
    }

    public ResourceType getResources() {
        return resources;
    }
}
