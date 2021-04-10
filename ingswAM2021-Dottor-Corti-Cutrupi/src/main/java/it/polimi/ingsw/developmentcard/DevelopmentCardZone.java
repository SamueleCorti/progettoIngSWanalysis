package it.polimi.ingsw.developmentcard;
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

    public DevelopmentCard getLastCard(){
        if(containedCards.size()>0)  return containedCards.get((containedCards.size())-1);
        else return null;
    }

    public DevelopmentCard getFirstCard(){
        return containedCards.get(0);
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

    public int calculateVictoryPoints(){
        int i,victoryPointsSum=0;
        for (i=0; i<this.containedCards.size(); i++){
            victoryPointsSum+= containedCards.get(i).getVictoryPoints();
        }
    return victoryPointsSum;
    }
}
