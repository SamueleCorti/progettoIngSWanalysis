package it.polimi.ingsw.Model.leadercard.leaderpowers;

import it.polimi.ingsw.Model.boardsAndPlayer.Dashboard;
import it.polimi.ingsw.Model.papalpath.CardCondition;
import it.polimi.ingsw.Model.resource.Resource;

public class ExtraProd implements LeaderPower {
    private PowerType type= PowerType.ExtraProd;
    private Resource resourceRequired;

    public ExtraProd(Resource resourceRequired) {
        this.resourceRequired = resourceRequired;
    }

    /**
     *Method adds the related resource of this card to the dashboard, so it possible to use the corresponding
     * special production.
     */
    @Override
    public void activateLeaderPower(Dashboard dashboard) {
        /*for(int i=0; i<2; i++) {
            if(dashboard.getLeaderCardZone().getLeaderCards().get(i).getLeaderPower().equals(this));
            dashboard.getLeaderCardZone().getLeaderCards().get(i).setCondition(CardCondition.Active,dashboard);
        }*/
        dashboard.getResourcesForExtraProd().add(resourceRequired);
    }

    public Resource returnRelatedResource(){
        return resourceRequired;
    }

    @Override
    public PowerType returnPowerType() {
        return type;
    }

    @Override
    public String toString() {
        return "Extra production using a "+ resourceRequired+" resource";
    }
}
