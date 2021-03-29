package it.polimi.ingsw.leadercard;

import it.polimi.ingsw.Dashboard;
import it.polimi.ingsw.leadercard.leaderpowers.LeaderPower;
import it.polimi.ingsw.papalpath.CardCondition;
import it.polimi.ingsw.requirements.Requirements;

import java.util.ArrayList;

public class LeaderCard {

    private ArrayList <Requirements> cardRequirements;
    private int victoryPoints;
    private LeaderPower leaderPower;
    private CardCondition condition;

    /*
    we need a way to instantiate the whole deck
     */

    public LeaderCard(ArrayList<Requirements> cardRequirements, int victoryPoints, LeaderPower leaderPower) {
        this.cardRequirements = cardRequirements;
        this.victoryPoints = victoryPoints;
        this.leaderPower = leaderPower;
        this.condition = CardCondition.Inactive;
    }

    public void activateCardPower(Dashboard dashboard){
        this.leaderPower.activateLeaderPower(dashboard);
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public CardCondition getCondition() {
        return condition;
    }

    public LeaderPower getLeaderPower() {
        return leaderPower;
    }

    public boolean checkRequirements(Dashboard dashboard) {
        for(Requirements requirements: cardRequirements){
            if(requirements.checkRequirement(dashboard)!=true){
                return false;
            }
        }
        return true;
    }

}
