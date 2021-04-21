package it.polimi.ingsw.resource;

import it.polimi.ingsw.Dashboard;
import it.polimi.ingsw.Warehouse;
import it.polimi.ingsw.papalpath.PapalPath;

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
        dashboard.getPapalPath().moveForward();
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

