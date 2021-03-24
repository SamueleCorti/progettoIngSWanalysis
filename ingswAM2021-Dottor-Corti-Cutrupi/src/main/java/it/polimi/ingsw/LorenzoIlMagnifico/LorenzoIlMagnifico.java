package it.polimi.ingsw.LorenzoIlMagnifico;

import it.polimi.ingsw.developmentcard.Color;
import it.polimi.ingsw.papalpath.PapalPath;
import it.polimi.ingsw.LorenzoIlMagnifico.*;

import java.util.List;
import java.util.*;

public class LorenzoIlMagnifico {

    TokenDeck tokenDeck;
    PapalPath papalPath;
    int consecutiveMoves=0;

    //constructor, this class needs to be linked with a papal path and a list of tokens, each one representing a different action
   public LorenzoIlMagnifico() {
       papalPath= new PapalPath(1);
       tokenDeck= new TokenDeck(papalPath, this);
    }

    //the move depends on the token drawn. Each time an action is made the counter goes up, ensuring a token isn't drawn two times unless a reshuffle happens
    public void playTurn(){
       tokenDeck.getToken(consecutiveMoves).tokenEffect();
       consecutiveMoves++;
    }

    //this method can get called only by the black cross token, and is used to reset Lorenzo's list of possible actions
    public void resetTokenDeck(){
       consecutiveMoves=0;
       tokenDeck.shuffleDeck();
    }

    //used for testing
    public void printDeck(){
       tokenDeck.printTokenDeck();
    }

}
