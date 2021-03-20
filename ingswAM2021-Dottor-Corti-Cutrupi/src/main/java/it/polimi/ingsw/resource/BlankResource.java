package it.polimi.ingsw.resource;

import it.polimi.ingsw.Warehouse;

public class BlankResource implements Resource {
    @Override
    public String getResourceType() {
        return "blank";
    }

    @Override
    public void effectFromMarket(Warehouse warehouse) {

    }
}