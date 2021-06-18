package it.polimi.ingsw.model.developmentcard;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DevelopmentCardDeck {
    /**
     *  all the cards in the deck are supposed to be of the same tier and of the same color; this
     *  requisite must be used during the instantiation of the deck
     */
    private List <DevelopmentCard> deck;
    private Color deckColor;
    private int deckLevel;

    public DevelopmentCardDeck(Color deckColor, int deckLevel) {
        this.deck = new ArrayList<DevelopmentCard>();
        this.deckColor = deckColor;
        this.deckLevel = deckLevel;
    }

    public List<DevelopmentCard> getDeck() {
        return deck;
    }

    public Color getDeckColor(){
        return this.deckColor;
    }

    public int getDeckLevel() {
        return deckLevel;
    }

    /**
     * Used to create the deck at the start of the game
     */
    public void addNewCard(DevelopmentCard cardToAdd){
        this.deck.add(cardToAdd);
    }

    /**
     * returns the card on top of the deck (the one that can be bought)
     */
    public DevelopmentCard getFirstCard(){
        if(deck!=null && deck.size()>0) {
            return this.deck.get(0);
        }
        else return null;
    }

    public void shuffle(){
        Collections.shuffle(this.deck);
    }

    /**
     * returns and deletes the first card of the deck
     */
    public DevelopmentCard drawCard(){
        DevelopmentCard temp=this.deck.get(0);
        this.deck.remove(0);
        return temp;
    }

    /**
     * @return the number of cards in the selected deck
     */
    public int deckSize(){
        return this.deck.size();
    }

    /**
     * Method called by Lorenzo to discard a card by the selected deck
     */
    public void removeCard(){
        this.deck.remove(0);
    }
}
