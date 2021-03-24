package it.polimi.ingsw.LorenzoIlMagnifico;

import it.polimi.ingsw.developmentcard.Color;
import it.polimi.ingsw.papalpath.PapalPath;

import java.util.*;

public class TokenDeck {

    LorenzoIlMagnifico lorenzoIlMagnifico;
    List<Token> tokens;
    DiscardToken[] discardTokens= new DiscardToken[4];
    BlackCrossToken blackCrossToken;
    DoubleBlackCrossToken doubleBlackCrossToken;

    //constructor, the papalPath is needed by both black cross tokens, lorenzo only by the "single" one
    public TokenDeck(PapalPath papalPath, LorenzoIlMagnifico lorenzoIlMagnifico) {
        this.discardTokens[0] = new DiscardToken(Color.Yellow);
        this.discardTokens[1] = new DiscardToken(Color.Blue);
        this.discardTokens[2] = new DiscardToken(Color.Purple);
        this.discardTokens[3] = new DiscardToken(Color.Green);
        this.blackCrossToken = new BlackCrossToken(papalPath,lorenzoIlMagnifico);
        this.doubleBlackCrossToken = new DoubleBlackCrossToken(papalPath);
        tokens.add(discardTokens[0]);
        tokens.add(discardTokens[1]);
        tokens.add(discardTokens[2]);
        tokens.add(discardTokens[3]);
        tokens.add(blackCrossToken);
        tokens.add(doubleBlackCrossToken);
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
}
