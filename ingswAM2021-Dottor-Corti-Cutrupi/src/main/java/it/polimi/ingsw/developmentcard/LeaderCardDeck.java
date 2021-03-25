package it.polimi.ingsw.developmentcard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeaderCardDeck {
    private List<LeaderCard> deck;
    public LeaderCardDeck(){
        this.deck = new ArrayList<LeaderCard>();
    }
    public void addNewCard(LeaderCard cardToAdd){
        this.deck.add(cardToAdd);
    }
    public void shuffle(){
        Collections.shuffle(this.deck);
    }
    public LeaderCard drawCard(){
        LeaderCard temp=this.deck.get(0);
        this.deck.remove(0);
        return temp;
    }
    public int deckSize(){
        return this.deck.size();
    }
}
