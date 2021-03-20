package it.polimi.ingsw.resource;

import it.polimi.ingsw.Warehouse;

public class ServantResource implements Resource {
    @Override
    public String getResourceType() {
        return "servant";
    }

    @Override
    public void effectFromMarket(Warehouse warehouse) {
        warehouse.addResource(this       );
    }
}