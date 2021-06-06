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
    private ArrayList<Resource> resourcesRelated;

    public ExtraDeposit(ArrayList<Resource> depotTypes) {
        size = depotTypes.size();
        resourceType=depotTypes.get(0).getResourceType();
        this.resourcesRelated = depotTypes;
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
                    dashboard.getWarehouse().removeResource(resourceType);
                    resourcesDeleted++;
                }
            }
        }
        while (resourcesDeleted>0){
            for (ExtraDepot extraDepot: dashboard.getExtraDepots()) {
                if(extraDepot.getExtraDepotType()==(resourceType)){
                    extraDepot.addResource();
                    resourcesDeleted--;
                }
            }
        }

    }

    public ArrayList<Resource> returnRelatedResourcesCopy() {
        return resourcesRelated;
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

    @Override
    public String toString() {
        return "allows you to store up to "+ size + " extra "+resourceType+" resources in your warehouse";
    }
}