package it.polimi.ingsw.model.resource;

import it.polimi.ingsw.model.boardsAndPlayer.Dashboard;

public class ShieldResource implements Resource {
    boolean isNew = true;

    private final ResourceType resourceType = ResourceType.Shield;

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
            while(i<dashboard.getExtraDepots().size() && !found){
                if(dashboard.getExtraDepots().get(i).getExtraDepotType()==ResourceType.Shield && dashboard.getExtraDepots().get(i).getAmountOfContainedResources()<2){
                    dashboard.getExtraDepots().get(i).addResource(new ShieldResource());
                    found = true;
                }
                i++;
            }
        }
        if(!found) dashboard.getWarehouse().addResource(new ShieldResource());
    }

    /**
     *It adds the resource to the strongbox
     */
    public void effectFromProduction(Dashboard dashboard){
        dashboard.getResourcesProduced().add(new ShieldResource());
    }
}
