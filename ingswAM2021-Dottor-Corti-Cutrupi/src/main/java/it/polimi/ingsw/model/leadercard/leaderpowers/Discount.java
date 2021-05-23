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

    public ArrayList<Resource> returnRelatedResourcesCopy() {
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

    @Override
    public String toString() {
        String s=new String();
        s="applies the following discount whenever you buy a development card: \n";
        for(Resource resource:discountedResources)  s+=resource.getResourceType()+"\t";
        return s;
    }

    @Override
    public PowerType returnPowerType() {
        return type;
    }
}