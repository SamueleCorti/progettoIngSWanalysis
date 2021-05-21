package it.polimi.ingsw.Model.requirements;

import it.polimi.ingsw.Model.boardsAndPlayer.Dashboard;
import it.polimi.ingsw.Model.resource.Resource;
import org.javatuples.Pair;

public class ResourcesRequirementsForLeaderCards implements Requirements{
    private Pair<Integer, Resource> resourcesRequired;

    @Override
    public String toString() {
        return "[" + resourcesRequired.getValue0() + "," + resourcesRequired.getValue1().getResourceType() + "]";
    }

    public ResourcesRequirementsForLeaderCards(int quantity, Resource resource) {
        this.resourcesRequired = new Pair<Integer,Resource>(quantity,resource);
    }

    public Pair<Integer, Resource> getResourcesRequired() {
        return resourcesRequired;
    }

    public boolean checkRequirement(Dashboard dashboard) {
        if (dashboard.allAvailableResources(resourcesRequired.getValue1()) >= resourcesRequired.getValue0()) return true;
        else   return false;
    }
}
