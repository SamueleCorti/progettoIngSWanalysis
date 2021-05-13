package it.polimi.ingsw.Communication.client.actions.mainActions.productionActions;

import it.polimi.ingsw.Communication.client.actions.mainActions.ProductionAction;
import it.polimi.ingsw.resource.Resource;

/**
 * Type of action created when the player decides to activate the production of on of his leader cards
 */
public class LeaderProductionAction implements ProductionAction {
    private int leaderCardZoneIndex;
    private Resource resourcesWanted;

    public LeaderProductionAction(int leaderCardZoneIndex,Resource resourcesWanted) {
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
    public Resource getResourcesWanted() {
        return resourcesWanted;
    }
}
