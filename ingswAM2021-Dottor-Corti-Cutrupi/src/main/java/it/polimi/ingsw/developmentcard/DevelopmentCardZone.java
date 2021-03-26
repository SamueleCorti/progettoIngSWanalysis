package it.polimi.ingsw.developmentcard;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;

public class DevelopmentCardZone {
    private List <DevelopmentCard> contentCards;

    public DevelopmentCardZone() {
        this.contentCards= new ArrayList<DevelopmentCard>();
    }

    public List <DevelopmentCard> getCards() {
        return this.contentCards;
    }

    public List <Pair<Integer,Color>> getCardsStats(){
        List <Pair<Integer,Color>> temp=new ArrayList<Pair<Integer,Color>>();
        for(DevelopmentCard card:contentCards){
            temp.add(card.getCardStats());
        }
        return temp;
    }

    public void addNewCard(DevelopmentCard cardToAdd){
        this.contentCards.add(cardToAdd);
    }

    public int calculateVictoryPoints(){
        int i,victoryPointsSum=0;
        for (i=0;i<this.contentCards.size();i++){
            victoryPointsSum+=contentCards.get(i).getVictoryPoints();
        }
    return victoryPointsSum;
    }
}
