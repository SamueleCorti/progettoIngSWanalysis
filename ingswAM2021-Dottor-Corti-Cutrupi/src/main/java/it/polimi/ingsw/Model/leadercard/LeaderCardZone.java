package it.polimi.ingsw.Model.leadercard;

import java.util.ArrayList;

/**every player owns two leader card zones where he keeps his leader cards (they can be either active or not)
 *
 */

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
