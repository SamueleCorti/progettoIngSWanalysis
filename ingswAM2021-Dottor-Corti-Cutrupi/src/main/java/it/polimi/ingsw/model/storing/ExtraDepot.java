package it.polimi.ingsw.model.storing;

import it.polimi.ingsw.model.resource.*;

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

    public void addResource(ResourceType resourceToRemove)  {
        switch (resourceToRemove){
            case Coin:
                addResource(new CoinResource());
                break;
            case Stone:
                addResource(new StoneResource());
                break;
            case Servant:
                addResource(new ServantResource());
                break;
            case Shield:
                addResource(new ShieldResource());
                break;
            default:
                break;
        }
    }


    public Resource getExtraDepotType(){ return depotType;}

    public int getExtraDepotSize(){
        return depot.size();
    }

    public void removeResource(){
        if(depot!=null && depot.size()>0)        depot.remove(0);
    }

    public ArrayList<Resource> getAllResources() {
        return depot;
    }
}

