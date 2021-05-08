package it.polimi.ingsw.Communication.client.messages.actions.mainActions.productionActions;

import it.polimi.ingsw.Communication.client.messages.actions.mainActions.ProductionAction;
import it.polimi.ingsw.resource.Resource;

import java.util.ArrayList;

public class BaseProductionAction implements ProductionAction {
    ArrayList<Resource> resourcesUsed;
    ArrayList<Resource> resourcesWanted;

    public BaseProductionAction(ArrayList<Resource> resourcesUsed, ArrayList<Resource> resourcesWanted) {
        this.resourcesUsed = resourcesUsed;
        this.resourcesWanted = resourcesWanted;
    }
}
