package it.polimi.ingsw.model.leadercard.leaderpowers;

import it.polimi.ingsw.model.boardsAndPlayer.Dashboard;
import it.polimi.ingsw.model.papalpath.CardCondition;
import it.polimi.ingsw.model.resource.CoinResource;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceType;
import it.polimi.ingsw.model.storing.ExtraDepot;

public class ExtraDeposit implements LeaderPower {
    private PowerType type = PowerType.ExtraDeposit;
    private Resource depotType;

    public ExtraDeposit(Resource depotType) {
        this.depotType = depotType;
    }

    /**
     *Method creates an extraDepot of the same type of the resource in this card
     */
    @Override
    public void activateLeaderPower(Dashboard dashboard) {
        /*for(int i=0; i<2; i++) {
            if(dashboard.getLeaderCardZone().getLeaderCards().get(i).getLeaderPower().equals(this));
            dashboard.getLeaderCardZone().getLeaderCards().get(i).setCondition(CardCondition.Active,dashboard);
        }*/
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
        }
    }

    public Resource returnRelatedResource(){
        return depotType;
    }

    @Override
    public PowerType returnPowerType() {
        return type;
    }

    @Override
    public String toString() {
        return "Extra deposit for "+ depotType.getResourceType()+" resources";
    }
}
