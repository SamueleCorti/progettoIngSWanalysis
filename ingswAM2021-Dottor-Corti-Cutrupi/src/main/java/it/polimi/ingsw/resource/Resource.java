package it.polimi.ingsw.resource;

import it.polimi.ingsw.Warehouse;

public interface Resource {
    public String getResourceType();
    public void effectFromMarket(Warehouse warehouse);
    public void notNewAnymore();
    public boolean getIsNew();
}