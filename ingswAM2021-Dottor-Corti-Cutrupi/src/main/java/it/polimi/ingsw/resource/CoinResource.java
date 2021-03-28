package it.polimi.ingsw.resource;

import it.polimi.ingsw.Dashboard;
import it.polimi.ingsw.Warehouse;
import it.polimi.ingsw.papalpath.PapalPath;

public class CoinResource implements Resource {
    boolean isNew = true;

    @Override
    public String getResourceType() {
        return "coin";
    }

    @Override
    public void effectFromMarket(Dashboard dashboard) {
        dashboard.getWarehouse().addResource(this       );
    }

    public void notNewAnymore(){
        isNew = false;
    }

    public boolean getIsNew(){
        return isNew;
    }
}

