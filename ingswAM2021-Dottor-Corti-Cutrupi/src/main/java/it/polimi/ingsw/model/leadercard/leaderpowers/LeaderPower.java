package it.polimi.ingsw.model.leadercard.leaderpowers;

import it.polimi.ingsw.model.boardsAndPlayer.Dashboard;
import it.polimi.ingsw.model.resource.Resource;

import java.util.ArrayList;

public interface LeaderPower {

    void activateLeaderPower(Dashboard dashboard);
    PowerType returnPowerType();
    ArrayList<Resource> returnRelatedResourcesCopy();
}
