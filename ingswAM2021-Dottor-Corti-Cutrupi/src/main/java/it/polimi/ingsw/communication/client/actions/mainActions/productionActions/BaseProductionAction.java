package it.polimi.ingsw.communication.client.actions.mainActions.productionActions;

import it.polimi.ingsw.communication.client.actions.mainActions.ProductionAction;
import it.polimi.ingsw.communication.server.GameHandler;
import it.polimi.ingsw.model.resource.ResourceType;

import java.util.ArrayList;

/**
 * Created when the client decides to activate the basic production
 */
public class BaseProductionAction implements ProductionAction {
    private final ArrayList<ResourceType> resourcesToUse;
    private final ArrayList<ResourceType> resourcesWanted;

    public BaseProductionAction(ArrayList<ResourceType> resourcesUsed, ArrayList<ResourceType> resourcesWanted) {
        this.resourcesToUse = resourcesUsed;
        this.resourcesWanted = resourcesWanted;
    }

    @Override
    public String toString() {
        return "BaseProductionAction{" +
                "resourcesUsed=" + resourcesToUse +
                ", resourcesWanted=" + resourcesWanted +
                '}';
    }

    public ArrayList<ResourceType> getResourcesToUse() {
        return resourcesToUse;
    }

    public ArrayList<ResourceType> getResourcesWanted() {
        return resourcesWanted;
    }


}
