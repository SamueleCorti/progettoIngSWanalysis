package it.polimi.ingsw.Model.storing;

import it.polimi.ingsw.Model.resource.Resource;

import java.util.ArrayList;

public class ExtraDepot {

    private Resource depotType;
    private ArrayList<Resource> depot;

    public ExtraDepot(Resource depotType) {
        this.depotType = depotType;
        this.depot = new ArrayList<Resource>();
    }

    /**
     *It adds a resource in the tail of the depot list
     */
    public void addResource(Resource newResource){
        newResource.notNewAnymore();
        depot.add(newResource);
    }


    public Resource getExtraDepotType(){ return depotType;}

    public int getExtraDepotSize(){
        return depot.size();
    }

    public void removeResource(){
        if(depot!=null && depot.size()>0)        depot.remove(0);
    }
}

