package it.polimi.ingsw.Communication.client.actions.mainActions.productionActions;

import it.polimi.ingsw.Communication.client.actions.mainActions.ProductionAction;
import it.polimi.ingsw.resource.Resource;
import it.polimi.ingsw.resource.ResourceType;

import java.util.ArrayList;

/**
 * Created when the client decides to activate the basic production
 */
public class BaseProductionAction implements ProductionAction {
    private ArrayList<ResourceType> resourcesUsed;
    private ArrayList<ResourceType> resourcesWanted;

    public BaseProductionAction(ArrayList<ResourceType> resourcesUsed, ArrayList<ResourceType> resourcesWanted) {
        this.resourcesUsed = resourcesUsed;
        this.resourcesWanted = resourcesWanted;
    }

    @Override
    public String toString() {
        return "BaseProductionAction{" +
                "resourcesUsed=" + resourcesUsed +
                ", resourcesWanted=" + resourcesWanted +
                '}';
    }

    public ArrayList<ResourceType> getResourcesUsed() {
        return resourcesUsed;
    }

    public ArrayList<ResourceType> getResourcesWanted() {
        return resourcesWanted;
    }
}
