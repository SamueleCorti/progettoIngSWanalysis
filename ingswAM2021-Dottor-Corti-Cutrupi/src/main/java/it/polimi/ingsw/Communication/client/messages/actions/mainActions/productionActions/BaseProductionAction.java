package it.polimi.ingsw.Communication.client.messages.actions.mainActions.productionActions;

import it.polimi.ingsw.Communication.client.messages.actions.mainActions.ProductionAction;
import it.polimi.ingsw.resource.Resource;

import java.util.ArrayList;

public class BaseProductionAction implements ProductionAction {
    private ArrayList<Resource> resourcesUsed;
    private ArrayList<Resource> resourcesWanted;

    public BaseProductionAction(ArrayList<Resource> resourcesUsed, ArrayList<Resource> resourcesWanted) {
        this.resourcesUsed = resourcesUsed;
        this.resourcesWanted = resourcesWanted;
    }

    public ArrayList<Resource> getResourcesUsed() {
        return resourcesUsed;
    }

    public ArrayList<Resource> getResourcesWanted() {
        return resourcesWanted;
    }
}
