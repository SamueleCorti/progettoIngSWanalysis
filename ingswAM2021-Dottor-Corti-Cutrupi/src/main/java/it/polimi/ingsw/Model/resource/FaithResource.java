package it.polimi.ingsw.Model.resource;

import it.polimi.ingsw.Model.boardsAndPlayer.Dashboard;

public class FaithResource implements Resource {
    private ResourceType resourceType = ResourceType.Faith;

    @Override
    public ResourceType getResourceType() {
        return resourceType;
    }

    /**
     *Method moves the player position in the papalPath forward of 1 unit.
     */
    @Override
    public void effectFromMarket(Dashboard dashboard) {
    }

    /**
     *Method moves the player position in the papalPath forward of 1 unit.
     */
    public void effectFromProduction(Dashboard dashboard){
        dashboard.getPapalPath().moveForward();
    }

    public void notNewAnymore(){}

    public boolean getIsNew(){
        return true;
    }
}

