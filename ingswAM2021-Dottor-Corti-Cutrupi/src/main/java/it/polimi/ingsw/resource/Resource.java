package it.polimi.ingsw.resource;

import it.polimi.ingsw.Warehouse;
import it.polimi.ingsw.papalpath.PapalPath;

public interface Resource {

    public String getResourceType();
    public void effectFromMarket(Warehouse warehouse, PapalPath papalPath);
    public void notNewAnymore();
    public boolean getIsNew();
}