package it.polimi.ingsw.resource;

import it.polimi.ingsw.Dashboard;
import it.polimi.ingsw.Warehouse;
import it.polimi.ingsw.papalpath.PapalPath;

public interface Resource {

    public ResourceType getResourceType();
    public void effectFromMarket(Dashboard dashboard);
    public void notNewAnymore();
    public boolean getIsNew();
}