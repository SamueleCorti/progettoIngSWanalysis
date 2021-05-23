package it.polimi.ingsw.model.developmentcard;
import it.polimi.ingsw.exception.PapalCardActivatedException;
import it.polimi.ingsw.model.boardsAndPlayer.Dashboard;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;

public class DevelopmentCardZone {

    private ArrayList <DevelopmentCard> containedCards;

    public DevelopmentCardZone() {
        this.containedCards = new ArrayList<DevelopmentCard>();
    }

    public List <DevelopmentCard> getCards() {
        return this.containedCards;
    }

    public int getSize(){
        return containedCards.size();
    }

    /**
     * returns the card on top (the one used to produce)
     */
    public DevelopmentCard getLastCard(){
        if(containedCards.size()>0)  return containedCards.get((containedCards.size())-1);
        else return null;
    }

    public DevelopmentCard copyLastCard(){
        if(!containedCards.isEmpty()) {
            DevelopmentCard card = containedCards.get((containedCards.size()) - 1);
            DevelopmentCard copy = new DevelopmentCard(card.getCardPrice(),card.getCardStats(),card.getProdRequirements(),card.getProdResults(),card.getVictoryPoints());
            return copy;
        }
        return null;
    }

    /**
     *
     * @return the first card (the first one bought)
     */
    public DevelopmentCard getFirstCard(){
        if(containedCards.size()>0)        return containedCards.get(0);
        else return null;
    }

    public List <Pair<Integer,Color>> getCardsStats(){
        List <Pair<Integer,Color>> temp=new ArrayList<Pair<Integer,Color>>();
        for(DevelopmentCard card: containedCards){
            temp.add(card.getCardStats());
        }
        return temp;
    }

    public void addNewCard(DevelopmentCard cardToAdd){
        this.containedCards.add(cardToAdd);
    }

    /** this method calculates the sum of the victory points of the cards contained in this zone
     *
     * @return
     */
    public int calculateVictoryPoints(){
        int i,victoryPointsSum=0;
        for (i=0; i<this.containedCards.size(); i++){
            victoryPointsSum+= containedCards.get(i).getVictoryPoints();
        }
    return victoryPointsSum;
    }

    public boolean checkProdPossible(Dashboard dashboard) {
        return getLastCard().checkRequirements(dashboard);
    }

    public void activateProd(Dashboard dashboard) throws PapalCardActivatedException {
        getLastCard().produce(dashboard);
    }
}
