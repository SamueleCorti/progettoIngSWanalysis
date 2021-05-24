package it.polimi.ingsw.model.leadercard;

import it.polimi.ingsw.model.boardsAndPlayer.Dashboard;
import it.polimi.ingsw.model.leadercard.leaderpowers.LeaderPower;
import it.polimi.ingsw.model.leadercard.leaderpowers.PowerType;
import it.polimi.ingsw.model.papalpath.CardCondition;
import it.polimi.ingsw.model.requirements.Requirements;

import java.util.ArrayList;

public class LeaderCard {

    private final ArrayList <Requirements> cardRequirements;
    private final int victoryPoints;
    private final LeaderPower leaderPower;
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
        StringBuilder string= new StringBuilder();
        string.append("Type of power : ").append(leaderPower.toString()).append("\n");
        string.append("Activation requirements: \n");
        for(Requirements requirements: cardRequirements){
            string.append(requirements).append("\n");
        }
        string.append("Victory points ").append(victoryPoints).append(":\n");
        string.append("This card is currently ").append(condition).append("\n");
        return string.toString();
    }

    public boolean checkRequirements(Dashboard dashboard) {
        for(Requirements requirements: cardRequirements){
            if(!requirements.checkRequirement(dashboard)){
                return false;
            }
        }
        return true;
    }

    public void setCondition(CardCondition newCondition) {
        if(!this.condition.equals(CardCondition.Discarded)) condition=newCondition;
    }

    public boolean isAnExtraProd() {
        if(leaderPower.returnPowerType().equals(PowerType.ExtraProd)){
            return true;
        }else return false;
    }

}
