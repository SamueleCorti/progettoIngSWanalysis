package it.polimi.ingsw.Communication.client.actions.mainActions.productionActions;

import it.polimi.ingsw.Communication.client.actions.mainActions.ProductionAction;
import it.polimi.ingsw.resource.Resource;
import it.polimi.ingsw.resource.ResourceType;

/**
 * Type of action created when the player decides to activate the production of on of his leader cards
 */
public class LeaderProductionAction implements ProductionAction {
    private final int leaderCardZoneIndex;
    private final ResourceType resourcesWanted;

    public LeaderProductionAction(int leaderCardZoneIndex,ResourceType resourcesWanted) {
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
    public ResourceType getResourcesWanted() {
        return resourcesWanted;
    }
}
