package it.polimi.ingsw.resource;

import it.polimi.ingsw.Dashboard;
import it.polimi.ingsw.Warehouse;
import it.polimi.ingsw.papalpath.PapalPath;

public class ShieldResource implements Resource {
    boolean isNew = true;

    //added later; tests not updated for this
    private ResourceType resourceType = ResourceType.Shield;

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
    public void effectFromMarket(Dashboard dashboard) {
        boolean found = false;
        if(dashboard.getExtraDepots().size()>0){
            int i=0;
            while(i<dashboard.getExtraDepots().size() && found==false){
                if(dashboard.getExtraDepots().get(i).getExtraDepotType()==this && dashboard.getExtraDepots().get(i).getExtraDepotSize()<2){
                    dashboard.getExtraDepots().get(i).addResource(this);
                    found = true;
                }
                i++;
            }
        }
        if(found==false) dashboard.getWarehouse().addResource(this       );
    }
}
