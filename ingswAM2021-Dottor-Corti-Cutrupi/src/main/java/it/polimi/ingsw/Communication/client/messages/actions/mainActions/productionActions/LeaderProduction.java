package it.polimi.ingsw.Communication.client.messages.actions.mainActions.productionActions;

import it.polimi.ingsw.Communication.client.messages.actions.mainActions.ProductionAction;
import it.polimi.ingsw.resource.Resource;

import java.util.ArrayList;

public class LeaderProduction implements ProductionAction {
    private int leaderCardZoneIndex;
    private ArrayList<Resource> resourcesUsed;
    private ArrayList<Resource> resourcesWanted;

    public LeaderProduction(ArrayList<Resource> resourcesUsed, ArrayList<Resource> resourcesWanted) {
        this.resourcesUsed = resourcesUsed;
        this.resourcesWanted = resourcesWanted;
    }

    public int getLeaderCardZoneIndex() {
        return leaderCardZoneIndex;
    }

    public ArrayList<Resource> getResourcesUsed() {
        return resourcesUsed;
    }

    public ArrayList<Resource> getResourcesWanted() {
        return resourcesWanted;
    }
}
