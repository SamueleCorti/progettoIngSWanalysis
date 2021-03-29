package it.polimi.ingsw.requirements;

import it.polimi.ingsw.Dashboard;
import it.polimi.ingsw.requirements.Requirements;
import it.polimi.ingsw.resource.Resource;
import org.javatuples.Pair;

public class ResourcesRequirements implements Requirements {

    private Pair<Integer, Resource> resourcesRequired;

    public ResourcesRequirements(Pair<Integer, Resource> resourcesRequired) {
        this.resourcesRequired = resourcesRequired;
    }

    public boolean checkRequirement(Dashboard dashboard) {
        if (dashboard.totalAmountOfResources(resourcesRequired.getValue1()) == resourcesRequired.getValue0()) return true;
        else   return false;
    }
}