package it.polimi.ingsw.leadercard.leaderpowers;

import it.polimi.ingsw.Dashboard;

public class ExtraDeposit implements LeaderPower {
    private PowerType type= PowerType.ExtraDeposit;

    @Override
    public void activateLeaderPower(Dashboard dashboard) {

    }

    @Override
    public PowerType returnPowerType() {
        return type;
    }
}
