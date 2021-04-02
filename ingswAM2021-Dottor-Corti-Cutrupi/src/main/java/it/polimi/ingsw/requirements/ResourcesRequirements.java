package it.polimi.ingsw.requirements;

import it.polimi.ingsw.Dashboard;
import it.polimi.ingsw.resource.Resource;
import org.javatuples.Pair;

    /*  this class is used by the development card to define what is needed to activate the production;
    it can also be used by the leader cards to define their price
    it has a method to check if the requirements are satisfied in the dashboard */

public class ResourcesRequirements implements Requirements{
    private Pair<Integer, Resource> resourcesRequired;

    public ResourcesRequirements(int quantity, Resource resource) {
        this.resourcesRequired = new Pair<Integer,Resource>(quantity,resource);
    }

    public Pair<Integer, Resource> getResourcesRequired() {
        return resourcesRequired;
    }

    public boolean checkRequirement(Dashboard dashboard) {
        if (dashboard.availableResourcesForProduction(resourcesRequired.getValue1()) >= resourcesRequired.getValue0()) return true;
        else   return false;
    }
}
