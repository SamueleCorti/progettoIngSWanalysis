package it.polimi.ingsw.model.leadercard;

import it.polimi.ingsw.model.boardsAndPlayer.Dashboard;
import it.polimi.ingsw.model.leadercard.leaderpowers.LeaderPower;
import it.polimi.ingsw.model.papalpath.CardCondition;
import it.polimi.ingsw.model.requirements.Requirements;

import java.util.ArrayList;

public class LeaderCard {

    private ArrayList <Requirements> cardRequirements;
    private int victoryPoints;
    private LeaderPower leaderPower;
    private CardCondition condition;

    public LeaderCard(ArrayList<Requirements> cardRequirements, int victoryPoints, LeaderPower leaderPower) {
        this.cardRequirements = cardRequirements;
        this.victoryPoints = victoryPoints;
        this.leaderPower = leaderPower;
        this.condition = CardCondition.Inactive;
    }

    public void activateCardPower(Dashboard dashboard){
        this.leaderPower.activateLeaderPower(dashboard);
    }

    public ArrayList<Requirements> getCardRequirements() {
        return cardRequirements;
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

    @Override
    public String toString() {
        String string= new String();
        string+="Type of power : "+ leaderPower.toString()+"\n";
        string+="Activation requirements: \n";
        for(Requirements requirements: cardRequirements){
            string+=requirements+"\n";
        }
        string+="Victory points "+victoryPoints+":\n";
        string+="This card is currently "+ condition+"\n\n";
        return string;
    }

    public boolean checkRequirements(Dashboard dashboard) {
        for(Requirements requirements: cardRequirements){
            if(requirements.checkRequirement(dashboard)!=true){
                return false;
            }
        }
        return true;
    }

    public void setCondition(CardCondition newCondition) {
        if(!this.condition.equals(CardCondition.Discarded)) condition=newCondition;
    }
}
