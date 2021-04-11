package it.polimi.ingsw.LorenzoIlMagnifico;

import it.polimi.ingsw.Dashboard;
import it.polimi.ingsw.GameBoard;
import it.polimi.ingsw.developmentcard.Color;
import it.polimi.ingsw.papalpath.PapalPath;
import it.polimi.ingsw.LorenzoIlMagnifico.*;

import java.util.List;
import java.util.*;

public class LorenzoIlMagnifico {

    TokenDeck tokenDeck;
    PapalPath papalPath;
    GameBoard gameBoard;
    int consecutiveMoves=0;

    //constructor, this class needs to be linked with a papal path and a list of tokens, each one representing a different action
   public LorenzoIlMagnifico(PapalPath papalPath) {
       this.papalPath= papalPath;
       this.tokenDeck= new TokenDeck();
    }

    //the move depends on the token drawn. Each time an action is made the counter goes up, ensuring a token isn't drawn two times unless a reshuffle happens
    public void playTurn(){
       tokenDeck.getToken(consecutiveMoves).tokenEffect(papalPath, this, gameBoard);
       this.checkWinConditions();
       consecutiveMoves++;
    }

    //this method can get called only by the black cross token, and is used to reset Lorenzo's list of possible actions.
    //consecutiveMoves is set to -1 because after this method is completed playTurn() still has to do consecutiveMoves++, so they even out
    public void resetTokenDeck(){
       consecutiveMoves=-1;
       tokenDeck.shuffleDeck();
    }

    public void checkWinConditions(){
       if (this.getFaithPosition()==24) lorenzoWins();
       // if a color of development is finished then lorenzoWins()
    }

    public void lorenzoWins(){
        System.out.println("Lorenzo won!");
    }

    //used for testing
    public void printDeck(){
       tokenDeck.printTokenDeck();
    }

    public int getFaithPosition(){
       return this.papalPath.getFaithPosition();
    }

    public String nextToken(){
       return tokenDeck.getToken(consecutiveMoves).toString();
    }

    public TokenDeck getTokenDeck() {
        return tokenDeck;
    }
}
