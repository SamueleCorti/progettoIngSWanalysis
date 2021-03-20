package it.polimi.ingsw.resource;

import it.polimi.ingsw.Warehouse;

public interface Resource {
    boolean newResource = true;

    public String getResourceType();
    public void effectFromMarket(Warehouse warehouse);
}