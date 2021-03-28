package it.polimi.ingsw.leadercard.leaderpowers;

import it.polimi.ingsw.Dashboard;
import it.polimi.ingsw.resource.Resource;

public class WhiteToColor implements LeaderPower {
    private Resource newResource;
    private PowerType type= PowerType.WhiteToColor;

    public PowerType getType() {
        return type;
    }

    public WhiteToColor(Resource newResource) {
        this.newResource = newResource;
    }

    @Override
    public void activateLeaderPower(Dashboard dashboard) {
        newResource.effectFromMarket(dashboard);
    }

    @Override
    public PowerType returnPowerType() {
        return type;
    }
}
