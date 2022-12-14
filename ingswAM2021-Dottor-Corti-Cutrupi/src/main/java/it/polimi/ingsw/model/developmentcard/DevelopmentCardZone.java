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

    /**
     * Returns a copy of the card on top of the selected deck
     */
    public DevelopmentCard copyLastCard(){
        if(!containedCards.isEmpty()) {
            DevelopmentCard card = containedCards.get((containedCards.size()) - 1);
            DevelopmentCard copy = new DevelopmentCard(card.getCardPrice(),card.getCardStats(),card.getProdRequirements(),card.getProdResults(),card.getVictoryPoints(), card.isWasCardModified());
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

    /**
     * Used to place the card in a player's development card zone after he acquires it
     */
    public void addNewCard(DevelopmentCard cardToAdd){
        this.containedCards.add(cardToAdd);
    }

    /**
     * this method calculates the sum of the victory points of the cards contained in this zone
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

    /**
     *Activates the production of the development card in the selected development card zone
     * @param dashboard: used to indicate where to put the produced resources
     * @throws PapalCardActivatedException thrown when a papal favor card gets activated during the production
     */
    public void activateProd(Dashboard dashboard) throws PapalCardActivatedException {
        getLastCard().produce(dashboard);
    }
}
