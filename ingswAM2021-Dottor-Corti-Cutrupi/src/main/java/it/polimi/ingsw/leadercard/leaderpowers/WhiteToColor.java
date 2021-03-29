package it.polimi.ingsw.leadercard.leaderpowers;

import it.polimi.ingsw.Dashboard;
import it.polimi.ingsw.resource.Resource;

import java.util.ArrayList;

public class WhiteToColor implements LeaderPower {
    private Resource resourceToCreate;
    private PowerType type= PowerType.WhiteToColor;


    public WhiteToColor(Resource resourceToCreate) {
        this.resourceToCreate = resourceToCreate;
    }

    @Override
    public void activateLeaderPower(Dashboard dashboard) {
            dashboard.getWhiteToColorResources().add(resourceToCreate);
    }

    @Override
    public PowerType returnPowerType() {
        return type;
    }

    public Resource returnRelatedResource(){
        return resourceToCreate;
    }
}
