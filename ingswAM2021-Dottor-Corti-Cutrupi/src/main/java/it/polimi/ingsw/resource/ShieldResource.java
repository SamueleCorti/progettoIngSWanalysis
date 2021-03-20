package it.polimi.ingsw.resource;

import it.polimi.ingsw.Warehouse;

public class ShieldResource implements Resource {
    boolean isNew = true;

    public void notNewAnymore(){
        isNew = false;
    }

    public boolean getIsNew(){
        return isNew;
    }

    @Override
    public String getResourceType() {
        return "shield";
    }

    @Override
    public void effectFromMarket(Warehouse warehouse) {
        warehouse.addResource(this       );
    }
}
