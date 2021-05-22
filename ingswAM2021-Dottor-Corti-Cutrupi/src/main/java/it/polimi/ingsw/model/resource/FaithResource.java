package it.polimi.ingsw.model.resource;

import it.polimi.ingsw.exception.PapalCardActivatedException;
import it.polimi.ingsw.model.boardsAndPlayer.Dashboard;

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
    public void effectFromMarket(Dashboard dashboard) throws PapalCardActivatedException {
        dashboard.moveForward();
    }

    /**
     *Method moves the player position in the papalPath forward of 1 unit.
     */
    public void effectFromProduction(Dashboard dashboard) throws PapalCardActivatedException {
        dashboard.moveForward();
    }

    public void notNewAnymore(){}

    public boolean getIsNew(){
        return true;
    }
}

