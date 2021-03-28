package it.polimi.ingsw.resource;

import it.polimi.ingsw.Dashboard;
import it.polimi.ingsw.Warehouse;
import it.polimi.ingsw.papalpath.PapalPath;

public class FaithResource implements Resource {

    @Override
    public String getResourceType() {
        return "faith";
    }

    @Override
    public void effectFromMarket(Dashboard dashboard) {
        dashboard.getPapalPath().moveForward();
    }

    public void notNewAnymore(){}

    public boolean getIsNew(){
        return true;
    }
}

