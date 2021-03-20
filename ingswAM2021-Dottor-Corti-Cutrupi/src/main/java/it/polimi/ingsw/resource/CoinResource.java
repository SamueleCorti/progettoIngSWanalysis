package it.polimi.ingsw.resource;

import it.polimi.ingsw.Warehouse;

public class CoinResource implements Resource {
    boolean isNew = false;

    @Override
    public String getResourceType() {
        return "coin";
    }

    @Override
    public void effectFromMarket(Warehouse warehouse) {
        warehouse.addResource(this       );
    }

    public void notNewAnymore(){
        isNew = false;
    }

    public boolean getIsNew(){
        return isNew;
    }
}

