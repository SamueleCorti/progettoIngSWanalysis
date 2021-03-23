package it.polimi.ingsw.resource;

import it.polimi.ingsw.Warehouse;
import it.polimi.ingsw.papalpath.PapalPath;

public class BlankResource implements Resource {
    @Override
    public String getResourceType() {
        return "blank";
    }

    @Override
    public void effectFromMarket(Warehouse warehouse, PapalPath papalPath) {
    }

    public void notNewAnymore(){}

    public boolean getIsNew(){
        return true;
    }
}