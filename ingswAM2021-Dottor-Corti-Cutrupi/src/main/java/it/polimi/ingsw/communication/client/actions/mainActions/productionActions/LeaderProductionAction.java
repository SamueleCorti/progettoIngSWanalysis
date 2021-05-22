package it.polimi.ingsw.communication.client.actions.mainActions.productionActions;

import it.polimi.ingsw.communication.client.actions.mainActions.ProductionAction;
import it.polimi.ingsw.model.resource.ResourceType;

import java.util.ArrayList;

/**
 * Type of action created when the player decides to activate the production of on of his leader cards
 */
public class LeaderProductionAction implements ProductionAction {
    private final int leaderCardZoneIndex;
    private final ArrayList<ResourceType> resourcesWanted;

    public LeaderProductionAction(int leaderCardZoneIndex,ArrayList <ResourceType> resourcesWanted) {
        this.leaderCardZoneIndex = leaderCardZoneIndex;
        this.resourcesWanted = resourcesWanted;
    }

    @Override
    public String toString() {
        return "LeaderProductionAction{" +
                "leaderCardZoneIndex=" + leaderCardZoneIndex +
                ", resourcesWanted=" + resourcesWanted +
                '}';
    }

    public int getLeaderCardZoneIndex() {
        return leaderCardZoneIndex;
    }
    public ArrayList <ResourceType> getResourcesWanted() {
        return resourcesWanted;
    }
}
