package it.polimi.ingsw.LorenzoIlMagnifico;

import it.polimi.ingsw.developmentcard.Color;
import it.polimi.ingsw.papalpath.PapalPath;

import java.util.*;

public class TokenDeck {

    List<Token> tokens;

    //constructor, the papalPath is needed by both black cross tokens, lorenzo only by the "single" one
    public TokenDeck(PapalPath papalPath, LorenzoIlMagnifico lorenzoIlMagnifico) {
        this.tokens= new ArrayList<Token>();
        tokens.add(new DiscardToken(Color.Yellow));
        tokens.add(new DiscardToken(Color.Blue));
        tokens.add(new DiscardToken(Color.Green));
        tokens.add(new DiscardToken(Color.Purple));
        tokens.add(new BlackCrossToken());
        tokens.add(new DoubleBlackCrossToken());
        tokens.add(new DoubleBlackCrossToken());
        this.shuffleDeck();
    }

    //return the required token, giving access to its tokenEffect method
    public Token getToken(int index){
        return tokens.get(index);
    }

    //the order of all the tokens is randomized
    public void shuffleDeck(){
        Collections.shuffle(tokens);
    }

    //used for testing
    public void printTokenDeck() {
        for(int i=0;i<tokens.size();i++)  System.out.println(tokens.get(i));
       }
}