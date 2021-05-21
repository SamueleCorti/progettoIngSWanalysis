package it.polimi.ingsw.model.leadercard.leaderpowers;

import it.polimi.ingsw.model.boardsAndPlayer.Dashboard;
import it.polimi.ingsw.model.resource.Resource;

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
        /*for(int i=0; i<2; i++) {
            if(dashboard.getLeaderCardZone().getLeaderCards().get(i).getLeaderPower().equals(this));
            dashboard.getLeaderCardZone().getLeaderCards().get(i).setCondition(CardCondition.Active,dashboard);
        }*/
        dashboard.getWhiteToColorResources().add(resourceToCreate);
    }

    @Override
    public PowerType returnPowerType() {
        return type;
    }

    public Resource returnRelatedResource(){
        return resourceToCreate;
    }

    @Override
    public String toString() {
        return "White to color transforming to "+ resourceToCreate.getResourceType();
    }
}
