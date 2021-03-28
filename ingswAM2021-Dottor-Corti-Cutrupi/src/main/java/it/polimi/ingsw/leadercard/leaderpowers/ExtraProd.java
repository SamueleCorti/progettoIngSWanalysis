package it.polimi.ingsw.leadercard.leaderpowers;

import it.polimi.ingsw.Dashboard;
import it.polimi.ingsw.resource.Resource;

public class ExtraProd implements LeaderPower {
    private PowerType type= PowerType.ExtraProd;
    private Resource resourceProduced;

    @Override
    public void activateLeaderPower(Dashboard dashboard) {

    }

    @Override
    public PowerType returnPowerType() {
        return type;
    }
}
