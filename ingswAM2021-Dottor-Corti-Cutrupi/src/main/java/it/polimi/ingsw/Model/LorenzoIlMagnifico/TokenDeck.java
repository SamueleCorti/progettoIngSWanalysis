package it.polimi.ingsw.Model.LorenzoIlMagnifico;

import it.polimi.ingsw.Model.developmentcard.Color;

import java.util.*;

public class TokenDeck {

    List<Token> tokens;

    /**
     * constructor, gives Lorenzo his tokens and shuffles them
     */
    public TokenDeck() {
        this.tokens = new ArrayList<Token>();
        tokens.add(new DiscardToken(Color.Yellow));
        tokens.add(new DiscardToken(Color.Blue));
        tokens.add(new DiscardToken(Color.Green));
        tokens.add(new DiscardToken(Color.Purple));
        tokens.add(new BlackCrossToken());
        tokens.add(new DoubleBlackCrossToken());
        tokens.add(new DoubleBlackCrossToken());
        this.shuffleDeck();
    }

    /**
     * return the required token, giving access to its tokenEffect method
     * @param index: Lorenzo keeps track of the index of the token to call, resets it to zero when a black cross gets drawn
     * @return the token whose effect Lorenzo has to activate
     */
    public Token getToken(int index){
        return tokens.get(index);
    }

    /**
     * the order of all the tokens is randomized
     */
    public void shuffleDeck(){
        Collections.shuffle(tokens);
    }

    //used for testing
    public void printTokenDeck() {
        for(int i=0;i<tokens.size();i++)  System.out.println(tokens.get(i));
       }
}