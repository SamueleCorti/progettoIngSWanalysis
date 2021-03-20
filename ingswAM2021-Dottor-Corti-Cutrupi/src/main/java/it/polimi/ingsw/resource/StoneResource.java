package it.polimi.ingsw.resource;

import it.polimi.ingsw.Warehouse;

public class StoneResource implements Resource {
    boolean isNew = true;

    public void notNewAnymore(){
        isNew = false;
    }

    public boolean getIsNew(){
        return isNew;
    }

    @Override
    public String getResourceType() {
        return "stone";
    }

    @Override
    public void effectFromMarket(Warehouse warehouse) {
        warehouse.addResource(this       );
    }
}
