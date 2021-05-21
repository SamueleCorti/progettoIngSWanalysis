package it.polimi.ingsw.model.leadercard.leaderpowers;

import it.polimi.ingsw.model.boardsAndPlayer.Dashboard;
import it.polimi.ingsw.model.resource.Resource;

public class Discount implements LeaderPower {
    private PowerType type= PowerType.Discount;
    private Resource discountedResource;

    public Discount(Resource resourceType){
        this.discountedResource=resourceType;
    }

    public Resource returnRelatedResource() {
        return discountedResource;
    }

    /**
     *Method adds the resource of the special ability to the dashboard, so that when player calls the method to acquire
     * a new card, the cards requiring this resource will be discounted
     */
    public void activateLeaderPower(Dashboard dashboard){
        /*for(int i=0; i<2; i++) {
            if(dashboard.getLeaderCardZone().getLeaderCards().get(i).getLeaderPower().equals(this));
                dashboard.getLeaderCardZone().getLeaderCards().get(i).setCondition(CardCondition.Active,dashboard);
        }*/
        dashboard.activateDiscountCard(this.discountedResource);
    }

    @Override
    public String toString() {
        return "Discount of "+ discountedResource.getResourceType()+" resources";
    }

    @Override
    public PowerType returnPowerType() {
        return type;
    }
}