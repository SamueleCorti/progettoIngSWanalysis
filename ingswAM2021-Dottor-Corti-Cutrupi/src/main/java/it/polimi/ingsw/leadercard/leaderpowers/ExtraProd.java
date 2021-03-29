package it.polimi.ingsw.leadercard.leaderpowers;

import it.polimi.ingsw.Dashboard;
import it.polimi.ingsw.resource.Resource;

public class ExtraProd implements LeaderPower {
    private PowerType type= PowerType.ExtraProd;
    private Resource resourceRequired;

    @Override
    public void activateLeaderPower(Dashboard dashboard) {

    }

    public Resource returnRelatedResource(){
        return resourceRequired;
    }

    @Override
    public PowerType returnPowerType() {
        return type;
    }
}
