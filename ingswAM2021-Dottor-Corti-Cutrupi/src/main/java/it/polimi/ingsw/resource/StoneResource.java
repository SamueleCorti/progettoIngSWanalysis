package it.polimi.ingsw.resource;

import it.polimi.ingsw.Dashboard;
import it.polimi.ingsw.Warehouse;
import it.polimi.ingsw.papalpath.PapalPath;

public class StoneResource implements Resource {

    boolean isNew = true;

    private ResourceType resourceType = ResourceType.Stone;


    public void notNewAnymore(){
        isNew = false;
    }

    public boolean getIsNew(){
        return isNew;
    }

    @Override
    public ResourceType getResourceType() {
        return resourceType;
    }

    /**
     *It adds the resource to the corresponding extradepot if the player has one, else it adds the resource to the
     * warehouse
     */
    @Override
    public void effectFromMarket(Dashboard dashboard) {
        boolean found = false;
        if(dashboard.getExtraDepots().size()>0){
            int i=0;
            while(i<dashboard.getExtraDepots().size() && found==false){
                if(dashboard.getExtraDepots().get(i).getExtraDepotType()==this && dashboard.getExtraDepots().get(i).getExtraDepotSize()<2){
                    dashboard.getExtraDepots().get(i).addResource(this);
                    found = true;
                }
                i++;
            }
        }
        if(found==false) dashboard.getWarehouse().addResource(this       );
    }

    /**
     *It adds the resource to the strongbox
     */
    public void effectFromProduction(Dashboard dashboard){
        dashboard.getResourcesProduced().add(this);
    }
}
