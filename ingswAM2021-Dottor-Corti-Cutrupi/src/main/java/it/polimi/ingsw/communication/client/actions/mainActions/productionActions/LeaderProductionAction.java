package it.polimi.ingsw.communication.client.actions.mainActions.productionActions;

import it.polimi.ingsw.communication.client.actions.mainActions.ProductionAction;
import it.polimi.ingsw.model.resource.ResourceType;

/**
 * Type of action created when the player decides to activate the production of on of his leader cards
 */
public class LeaderProductionAction implements ProductionAction {
    private final int leaderCardZoneIndex;
    private final ResourceType resourceWanted;

    public LeaderProductionAction(int leaderCardZoneIndex,ResourceType resourcesWanted) {
        this.leaderCardZoneIndex = leaderCardZoneIndex;
        this.resourceWanted = resourcesWanted;
    }

    @Override
    public String toString() {
        return "LeaderProductionAction{" +
                "leaderCardZoneIndex=" + leaderCardZoneIndex +
                ", resourcesWanted=" + resourceWanted +
                '}';
    }

    public int getLeaderCardZoneIndex() {
        return leaderCardZoneIndex;
    }
    public ResourceType getResourceWanted() {
        return resourceWanted;
    }
}
