package it.polimi.ingsw.Model.resource;

import it.polimi.ingsw.Model.boardsAndPlayer.Dashboard;

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
    public void effectFromMarket(Dashboard dashboard) {
        if(dashboard.getWhiteToColorResources()!=null && dashboard.getWhiteToColorResources().size()>0){
            if(dashboard.getWhiteToColorResources().size()==1){
                dashboard.getWhiteToColorResources().get(0).effectFromMarket(dashboard);
            }
            else if(dashboard.getWhiteToColorResources().size()==2){
                //it does nothing because we call the method in player
            }
        }

    }

    public void effectFromProduction(Dashboard dashboard){}

    public void notNewAnymore(){}

    public boolean getIsNew(){
        return true;
    }
}