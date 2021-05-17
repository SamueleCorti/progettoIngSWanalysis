package it.polimi.ingsw.Model.leadercard.leaderpowers;

import it.polimi.ingsw.Model.boardsAndPlayer.Dashboard;
import it.polimi.ingsw.Model.papalpath.CardCondition;
import it.polimi.ingsw.Model.resource.Resource;
import it.polimi.ingsw.Model.storing.ExtraDepot;

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
        for(int i=0; i<2; i++) {
            if(dashboard.getLeaderCardZone().getLeaderCards().get(i).getLeaderPower().equals(this));
            dashboard.getLeaderCardZone().getLeaderCards().get(i).setCondition(CardCondition.Active,dashboard);
        }
        dashboard.getExtraDepots().add(new ExtraDepot(depotType));
    }

    public Resource returnRelatedResource(){
        return depotType;
    }

    @Override
    public PowerType returnPowerType() {
        return type;
    }
}
