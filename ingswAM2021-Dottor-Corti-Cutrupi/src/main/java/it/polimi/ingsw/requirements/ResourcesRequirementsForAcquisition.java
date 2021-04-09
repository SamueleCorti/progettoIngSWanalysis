package it.polimi.ingsw.requirements;

import it.polimi.ingsw.Dashboard;
import it.polimi.ingsw.requirements.Requirements;
import it.polimi.ingsw.resource.Resource;
import org.javatuples.Pair;

/*  this class is used by the development card to define what is needed to buy the card;
   it has a method to check if the requirements are satisfied in the dashboard    */

public class ResourcesRequirementsForAcquisition implements Requirements {

    private Pair<Integer, Resource> resourcesRequired;

    @Override
    public String toString() {
        return "[" + resourcesRequired.getValue0() + "," + resourcesRequired.getValue1().getResourceType() + "]";
    }

    public ResourcesRequirementsForAcquisition(int quantity, Resource resource) {
        this.resourcesRequired = new Pair<Integer,Resource>(quantity,resource);
    }

    public Pair<Integer, Resource> getResourcesRequired() {
        return resourcesRequired;
    }

    public boolean checkRequirement(Dashboard dashboard) {
        if (dashboard.availableResourcesForDevelopment(resourcesRequired.getValue1()) >= resourcesRequired.getValue0()) return true;
        else   return false;
    }
}