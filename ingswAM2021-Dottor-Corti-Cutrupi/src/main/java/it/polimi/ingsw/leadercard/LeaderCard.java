package it.polimi.ingsw.leadercard;

import it.polimi.ingsw.Dashboard;
import it.polimi.ingsw.requirements.Requirements;

import java.util.ArrayList;

public class LeaderCard {

    private ArrayList <Requirements> cardRequirements;
    private int victoryPoints;
    private LeaderPower leaderPower;

    /*
    we need a way to instantiate the whole deck
     */

    public LeaderCard(ArrayList<Requirements> cardRequirements, int victoryPoints, LeaderPower leaderPower) {
        this.cardRequirements = cardRequirements;
        this.victoryPoints = victoryPoints;
        this.leaderPower = leaderPower;
    }

    public void activateCard(Dashboard dashboard){
        this.leaderPower.ActivateLeaderPower(dashboard);
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public boolean checkRequirements(Dashboard dashboard) {
        for(Requirements requirements: cardRequirements){
            if(requirements.checkRequirement(dashboard)==true){
            }
            else{
                return false;
            }
        }
        return true;
    }

}
