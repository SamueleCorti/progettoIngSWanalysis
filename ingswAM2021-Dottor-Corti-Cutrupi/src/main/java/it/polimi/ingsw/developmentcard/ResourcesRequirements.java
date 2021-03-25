package it.polimi.ingsw.developmentcard;

import it.polimi.ingsw.Dashboard;
import it.polimi.ingsw.resource.Resource;
import org.javatuples.Pair;

import java.util.ArrayList;

public class ResourcesRequirements implements Requirements{

    private Pair<Integer, Resource> resourcesRequired;

    public ResourcesRequirements(Pair<Integer, Resource> resourcesRequired) {
        this.resourcesRequired = resourcesRequired;
    }

    public boolean checkRequirement(Dashboard dashboard){
         /*
        this part still has to be implemented
         */
        return true;
    };
}
