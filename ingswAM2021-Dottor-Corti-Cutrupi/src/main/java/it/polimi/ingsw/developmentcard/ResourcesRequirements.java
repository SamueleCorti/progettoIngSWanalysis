package it.polimi.ingsw.developmentcard;

import it.polimi.ingsw.resource.Resource;
import org.javatuples.Pair;

import java.util.ArrayList;

public class ResourcesRequirements implements Requirements{

    private Pair<Integer, Resource> resourcesRequired;

    public ResourcesRequirements(Pair<Integer, Resource> resourcesRequired) {
        this.resourcesRequired = resourcesRequired;
    }

    public boolean checkRequirements(){
        //da implementare
        return true;
    };
}
