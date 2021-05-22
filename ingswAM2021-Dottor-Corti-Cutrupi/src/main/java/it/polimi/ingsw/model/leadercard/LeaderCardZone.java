package it.polimi.ingsw.model.leadercard;

import it.polimi.ingsw.model.boardsAndPlayer.Dashboard;
import it.polimi.ingsw.model.papalpath.CardCondition;

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

    public void removeCard(int index){
        leaderCards.remove(index);
    }

    public boolean isLeaderActive(int index){
        return leaderCards.get(index).getCondition()== CardCondition.Active;
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

    public void activateCard(int index, Dashboard dashboard) {
        leaderCards.get(index).activateCardPower(dashboard);
        leaderCards.get(index).setCondition(CardCondition.Active);
    }

    public boolean isLeaderInactive(int index) {
        return leaderCards.get(index).getCondition()==CardCondition.Inactive;
    }

    public boolean checkRequirements(int index, Dashboard dashboard) {
        return leaderCards.get(index).checkRequirements(dashboard);
    }
}
