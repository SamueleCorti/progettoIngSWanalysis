package it.polimi.ingsw.model.leadercard.leaderpowers;

import it.polimi.ingsw.model.boardsAndPlayer.Dashboard;
import it.polimi.ingsw.model.resource.Resource;

import java.util.ArrayList;

public class Discount implements LeaderPower {
    private PowerType type= PowerType.Discount;
    private ArrayList<Resource> discountedResources;

    public Discount(ArrayList<Resource> discountedResources){
        this.discountedResources=discountedResources;
    }

    public ArrayList<Resource> returnRelatedResources() {
        return discountedResources;
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
        dashboard.activateDiscountCard(this.discountedResources);
    }
/*
    @Override
    public String toString() {
        return "Discount of "+ discountedResources.getResourceType()+" resources";
    }
*/
    @Override
    public PowerType returnPowerType() {
        return type;
    }
}