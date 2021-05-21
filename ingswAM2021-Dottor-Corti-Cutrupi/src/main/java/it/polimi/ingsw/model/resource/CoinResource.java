package it.polimi.ingsw.model.resource;

import it.polimi.ingsw.model.boardsAndPlayer.Dashboard;

public class CoinResource implements Resource {
    boolean isNew = true;
    //added later; tests not updated for this
   private ResourceType resourceType = ResourceType.Coin;


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
                if(dashboard.getExtraDepots().get(i).getExtraDepotType().getResourceType()==ResourceType.Coin && dashboard.getExtraDepots().get(i).getExtraDepotSize()<2){
                    dashboard.getExtraDepots().get(i).addResource(new CoinResource());
                    found = true;
                }
                i++;
            }
        }
        if(found==false) dashboard.getWarehouse().addResource(new CoinResource());
    }

    /**
     *It adds the resource to the strongbox
     */
    public void effectFromProduction(Dashboard dashboard){
        dashboard.getResourcesProduced().add(new CoinResource());
    }

    public void notNewAnymore(){
        isNew = false;
    }

    public boolean getIsNew(){
        return isNew;
    }
}

