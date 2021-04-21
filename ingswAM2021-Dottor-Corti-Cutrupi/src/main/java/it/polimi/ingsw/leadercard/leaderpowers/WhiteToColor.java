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

    /**
     *Method adds the related resource of this card to the dashboard, so it possible to transform a blank resource
     * in the market to the one of the related color in the warehouse.
     */
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
