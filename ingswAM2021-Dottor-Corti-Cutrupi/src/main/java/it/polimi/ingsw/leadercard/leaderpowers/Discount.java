package it.polimi.ingsw.leadercard.leaderpowers;

import it.polimi.ingsw.Dashboard;
import it.polimi.ingsw.papalpath.CardCondition;
import it.polimi.ingsw.resource.Resource;

import java.util.concurrent.locks.Condition;

public class Discount implements LeaderPower {
    private PowerType type= PowerType.Discount;
    private Resource discountedResource;

    public Discount(Resource resourceType){
        this.discountedResource=resourceType;
    }

    public Resource returnRelatedResource() {
        return discountedResource;
    }

    public void activateLeaderPower(Dashboard dashboard){
        for(int i=0; i<2; i++) {
            if(dashboard.getLeaderCardZone().getLeaderCards().get(i).getLeaderPower().equals(this));
                dashboard.getLeaderCardZone().getLeaderCards().get(i).setCondition(CardCondition.Active,dashboard);
        }
        dashboard.activatedDiscountCard(this.discountedResource);
    }

    @Override
    public PowerType returnPowerType() {
        return type;
    }
}