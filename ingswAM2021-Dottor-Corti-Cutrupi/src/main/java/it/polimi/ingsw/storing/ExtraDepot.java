package it.polimi.ingsw.storing;

import it.polimi.ingsw.resource.Resource;

import java.util.ArrayList;

public class ExtraDepot {

    private Resource depotType;
    private ArrayList<Resource> depot;

    public ExtraDepot(Resource depotType) {
        this.depotType = depotType;
        this.depot = new ArrayList<Resource>();
    }

    public void addResource(Resource newResource){
        depot.add(newResource);
    }

    public int getExtraDepotSize(){
        return depot.size();
    }

    public void removeResource(){
        depot.remove(0);
    }
}

