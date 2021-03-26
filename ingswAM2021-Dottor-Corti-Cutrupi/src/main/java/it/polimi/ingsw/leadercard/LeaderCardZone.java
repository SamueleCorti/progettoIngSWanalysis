package it.polimi.ingsw.leadercard;

import it.polimi.ingsw.leadercard.LeaderCard;

import java.util.ArrayList;

public class LeaderCardZone {
    private ArrayList <LeaderCard> leaderCards;

    public LeaderCardZone() {
        this.leaderCards= new ArrayList<LeaderCard>();
    }

    public ArrayList<LeaderCard> getLeaderCards() {
        return leaderCards;
    }

    public void addNewCard(LeaderCard cardToAdd){
        this.leaderCards.add(cardToAdd);
    }

    public int calculateVictoryPoints(){
        int i,victoryPointsSum=0;
        for (i=0;i<this.leaderCards.size();i++){
            victoryPointsSum+=leaderCards.get(i).getVictoryPoints();
        }
        return victoryPointsSum;
    }
}
