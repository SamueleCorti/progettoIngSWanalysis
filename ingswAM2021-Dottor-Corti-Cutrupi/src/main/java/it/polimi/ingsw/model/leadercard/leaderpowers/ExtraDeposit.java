package it.polimi.ingsw.model.leadercard.leaderpowers;

import it.polimi.ingsw.model.boardsAndPlayer.Dashboard;
import it.polimi.ingsw.model.resource.CoinResource;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceType;
import it.polimi.ingsw.model.storing.ExtraDepot;

import java.util.ArrayList;

public class ExtraDeposit implements LeaderPower {
    private PowerType type = PowerType.ExtraDeposit;
    private ResourceType resourceType;
    private int size;
    private ArrayList<Resource> depotTypes;

    public ExtraDeposit(ArrayList<Resource> depotTypes) {
        size= depotTypes.size();
        resourceType=depotTypes.get(0).getResourceType();
    }

    /**
     *Method creates an extraDepot of the same type of the resource in this card
     */
    @Override
    public void activateLeaderPower(Dashboard dashboard) {
        dashboard.addExtraDepot(new ExtraDepot(this));
        int resourcesDeleted=0;
        for(int i=1;i<4;i++){
            if(dashboard.getWarehouse().returnTypeofDepot(i)==resourceType){
                int lengthOfDepot = dashboard.getWarehouse().returnLengthOfDepot(i);
                while(resourcesDeleted<lengthOfDepot &&resourcesDeleted<2){
                    dashboard.getWarehouse().removeResource(depotTypes.get(0).getResourceType());
                    resourcesDeleted++;
                }
            }
        }
        while (resourcesDeleted>0){
            for (ExtraDepot extraDepot: dashboard.getExtraDepots()) {
                if(extraDepot.getExtraDepotType()==(this.depotTypes.get(0).getResourceType())){
                    extraDepot.addResource(depotTypes.get(0).getResourceType());
                    resourcesDeleted--;
                }
            }
        }
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

    public PowerType getType() {
        return type;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public int getSize() {
        return size;
    }

    public ArrayList<Resource> getDepotTypes() {
        return depotTypes;
    }
}