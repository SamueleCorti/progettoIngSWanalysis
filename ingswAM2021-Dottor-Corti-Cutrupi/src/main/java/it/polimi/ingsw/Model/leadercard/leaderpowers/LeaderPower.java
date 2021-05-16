package it.polimi.ingsw.Model.leadercard.leaderpowers;

import it.polimi.ingsw.Model.boardsAndPlayer.Dashboard;
import it.polimi.ingsw.Model.resource.Resource;

public interface LeaderPower {

    void activateLeaderPower(Dashboard dashboard);
    PowerType returnPowerType();
    Resource returnRelatedResource();
}
