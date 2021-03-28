package it.polimi.ingsw.leadercard.leaderpowers;

import it.polimi.ingsw.Dashboard;

public class Discount implements LeaderPower {
    private PowerType type= PowerType.Discount;


    public void activateLeaderPower(Dashboard dashboard){
        // this still needs to be implemented
    }

    @Override
    public PowerType returnPowerType() {
        return type;
    }

    ;
}
