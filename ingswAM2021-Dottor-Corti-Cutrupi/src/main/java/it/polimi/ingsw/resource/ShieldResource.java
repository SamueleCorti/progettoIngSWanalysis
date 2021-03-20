package it.polimi.ingsw.resource;

import it.polimi.ingsw.Warehouse;

public class ShieldResource implements Resource {
    @Override
    public String getResourceType() {
        return "shield";
    }

    @Override
    public void effectFromMarket(Warehouse warehouse) {
        warehouse.addResource(this       );
    }
}
