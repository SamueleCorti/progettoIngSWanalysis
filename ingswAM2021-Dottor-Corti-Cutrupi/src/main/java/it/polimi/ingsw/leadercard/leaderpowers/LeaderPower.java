package it.polimi.ingsw.leadercard.leaderpowers;

import it.polimi.ingsw.Dashboard;
import it.polimi.ingsw.papalpath.CardCondition;

public interface LeaderPower {

    void activateLeaderPower(Dashboard dashboard);
    PowerType returnPowerType();
}
