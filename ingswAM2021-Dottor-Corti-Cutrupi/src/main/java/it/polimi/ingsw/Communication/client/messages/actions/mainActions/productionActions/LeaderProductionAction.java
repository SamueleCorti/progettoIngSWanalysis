package it.polimi.ingsw.Communication.client.messages.actions.mainActions.productionActions;

import it.polimi.ingsw.Communication.client.messages.actions.mainActions.ProductionAction;
import it.polimi.ingsw.resource.Resource;

import java.util.ArrayList;

public class LeaderProductionAction implements ProductionAction {
    private int leaderCardZoneIndex;
    private Resource resourcesUsed;
    private Resource resourcesWanted;

    public LeaderProductionAction(int leaderCardZoneIndex, Resource resourcesUsed, Resource resourcesWanted) {
        this.leaderCardZoneIndex = leaderCardZoneIndex;
        this.resourcesUsed = resourcesUsed;
        this.resourcesWanted = resourcesWanted;
    }

    public int getLeaderCardZoneIndex() {
        return leaderCardZoneIndex;
    }

    public Resource getResourcesUsed() {
        return resourcesUsed;
    }

    public Resource getResourcesWanted() {
        return resourcesWanted;
    }
}
