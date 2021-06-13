package it.polimi.ingsw.model.resource;

import it.polimi.ingsw.exception.PapalCardActivatedException;
import it.polimi.ingsw.model.boardsAndPlayer.Dashboard;

public class BlankResource implements Resource {
    private ResourceType resourceType = ResourceType.Blank;

    @Override
    public ResourceType getResourceType() {
        return resourceType;
    }

    /**
     *If the player doesn't have a whiteToColor leader card active, method does nothing,
     * if it has one, it adds to th warehouse a resource of the same type of the resource of the special ability
     */
    @Override
    public void effectFromMarket(Dashboard dashboard) throws PapalCardActivatedException {
        if(dashboard.getWhiteToColorResources()!=null && dashboard.getWhiteToColorResources().size()==1){
            for (Resource resource:dashboard.getWhiteToColorResources().get(0)) {
                resource.effectFromMarket(dashboard);
            }
        }

    }

    public void effectFromProduction(Dashboard dashboard){}

    public void notNewAnymore(){}

    public boolean getIsNew(){
        return true;
    }
}