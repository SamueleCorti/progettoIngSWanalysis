package it.polimi.ingsw.model.storing;

import it.polimi.ingsw.model.adapters.ResourceDuplicator;
import it.polimi.ingsw.model.leadercard.leaderpowers.ExtraDeposit;
import it.polimi.ingsw.model.resource.*;

import java.util.ArrayList;

public class ExtraDepot {

    private ResourceType depotType;
    private int size;
    private ArrayList<Resource> depot;

    public ExtraDepot(ExtraDeposit extraDeposit) {
        this.depotType = extraDeposit.getResourceType();
        size=extraDeposit.getSize();
        depot=new ArrayList<>();
    }

    public int getSize() {
        return size;
    }

    /**
     *It adds a resource in the tail of the depot list
     */
    public void addResource(Resource newResource){
        newResource.notNewAnymore();
        depot.add(newResource);
    }

    public void addResource(){
        ResourceDuplicator resourceDuplicator = new ResourceDuplicator();
        Resource resourceToAdd = resourceDuplicator.copyResource(depotType);
        addResource(resourceToAdd);
    }


    public ResourceType getExtraDepotType(){ return depotType;}

    public int getAmountOfContainedResources(){
        return depot.size();
    }

    public void removeResource(){
        if(depot!=null && depot.size()>0)        depot.remove(0);
    }

    public ArrayList<Resource> getAllResources() {
        return depot;
    }
}