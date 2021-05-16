package it.polimi.ingsw.Communication.client.actions;

import it.polimi.ingsw.Model.resource.ResourceType;

/**
 * Action used during the initialization phase; it contains up to two {@link ResourceType}, representing the bonus resources the player wishes to start with.
 */
public class BonusResourcesAction implements Action{
    private ResourceType resourceType1;
    private ResourceType resourceType2;

    /**
     * Used for the 4th player, who starts with two bonus resources
     * @param resourceType1 1st bonus resource
     * @param resourceType2 2ns bonus resource
     */
    public BonusResourcesAction(ResourceType resourceType1, ResourceType resourceType2) {
        this.resourceType1 = resourceType1;
        this.resourceType2 = resourceType2;
    }

    /**
     * Used for the 2nd and 3rd player, both starting with one extra resource
     * @param resourceType1 bonus resource
     */
    public BonusResourcesAction(ResourceType resourceType1) {
        this.resourceType1 = resourceType1;
    }

    /**
     * @return {@link ResourceType}
     */
    public ResourceType getResourceType1() {
        return resourceType1;
    }

    /**
     * Used only for the 4th player (if called by other players it'll return null)
     * @return {@link ResourceType}
     */
    public ResourceType getResourceType2() {
        return resourceType2;
    }
}
