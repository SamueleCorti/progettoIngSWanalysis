package it.polimi.ingsw.leadercard.leaderpowers;

import it.polimi.ingsw.Dashboard;
import it.polimi.ingsw.resource.Resource;
import it.polimi.ingsw.storing.ExtraDepot;

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
