package it.polimi.ingsw.model.resource;

import it.polimi.ingsw.model.boardsAndPlayer.Dashboard;

public class ServantResource implements Resource {
    boolean isNew = true;

    private ResourceType resourceType = ResourceType.Servant;

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
                if(dashboard.getExtraDepots().get(i).getExtraDepotType()==ResourceType.Servant && dashboard.getExtraDepots().get(i).getExtraDepotSize()<2){
                    dashboard.getExtraDepots().get(i).addResource(new ServantResource());
                    found = true;
                }
                i++;
            }
        }
        if(found==false) dashboard.getWarehouse().addResource(new ServantResource());
    }

    /**
     *It adds the resource to the strongbox
     */
    public void effectFromProduction(Dashboard dashboard){
        dashboard.getResourcesProduced().add(new ServantResource());
    }
}