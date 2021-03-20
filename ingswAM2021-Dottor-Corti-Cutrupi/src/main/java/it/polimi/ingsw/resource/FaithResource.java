package it.polimi.ingsw.resource;

import it.polimi.ingsw.Warehouse;

public class FaithResource implements Resource {
    @Override
    public String getResourceType() {
        return "faith";
    }

    @Override
    public void effectFromMarket(Warehouse warehouse) {

    }
}

