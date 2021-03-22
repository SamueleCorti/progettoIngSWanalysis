package it.polimi.ingsw.developmentcard;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List <DevelopmentCard> deck;
    public Deck(){
        this.deck = new ArrayList<DevelopmentCard>();
    }
    public void addNewCard(DevelopmentCard cardToAdd){
        this.deck.add(cardToAdd);
    }
    public DevelopmentCard getFirstCard(){
        return this.deck.get(0);
    }
    public void shuffle(){
        Collections.shuffle(this.deck);
    }
    public DevelopmentCard drawCard(){
        DevelopmentCard temp=this.deck.get(0);
        this.deck.remove(0);
        return temp;
    }
    public int deckSize(){
        return this.deck.size();
    }
}
