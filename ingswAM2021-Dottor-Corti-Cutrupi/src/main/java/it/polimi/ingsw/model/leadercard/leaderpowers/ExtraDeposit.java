package it.polimi.ingsw.model.leadercard.leaderpowers;

import it.polimi.ingsw.model.boardsAndPlayer.Dashboard;
import it.polimi.ingsw.model.resource.CoinResource;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceType;
import it.polimi.ingsw.model.storing.ExtraDepot;

import java.util.ArrayList;

public class ExtraDeposit implements LeaderPower {
    private PowerType type = PowerType.ExtraDeposit;
    private ArrayList<Resource> depotTypes;

    public ExtraDeposit(ArrayList<Resource> depotTypes) {
        this.depotTypes = depotTypes;
    }

    /**
     *Method creates an extraDepot of the same type of the resource in this card
     */
    @Override
    public void activateLeaderPower(Dashboard dashboard) {
       /*
        dashboard.getExtraDepots().add(new ExtraDepot(depotType));
        int resourcesDeleted=0;
        for(int i=1;i<4;i++){
            if(dashboard.getWarehouse().returnTypeofDepot(i)==depotType.getResourceType()){
                int lengthOfDepot = dashboard.getWarehouse().returnLengthOfDepot(i);
                while(resourcesDeleted<lengthOfDepot &&resourcesDeleted<2){
                    dashboard.getWarehouse().removeResource(depotType.getResourceType());
                    resourcesDeleted++;
                }
            }
        }
        while (resourcesDeleted>0){
            for (ExtraDepot extraDepot: dashboard.getExtraDepots()) {
                if(extraDepot.getExtraDepotType().equals(this.depotType)){
                    extraDepot.addResource(depotType.getResourceType());
                    resourcesDeleted--;
                }
            }
        }*/
    }

    public ArrayList<Resource> returnRelatedResources() {
        return depotTypes;
    }

    @Override
    public PowerType returnPowerType() {
        return type;
    }

  /*  @Override
    public String toString() {
        return "Extra deposit for "+ depotType.getResourceType()+" resources";
    }*/
}
