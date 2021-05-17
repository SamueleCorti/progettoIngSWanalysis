package it.polimi.ingsw.Model.LorenzoIlMagnifico;

import it.polimi.ingsw.Model.boardsAndPlayer.GameBoard;
import it.polimi.ingsw.Model.papalpath.PapalPath;

public class LorenzoIlMagnifico {

    TokenDeck tokenDeck;
    PapalPath papalPath;
    GameBoard gameBoard;
    int consecutiveMoves=0;

    /**
     * Constructor, needs to be linked with a papal path in order to keep track of Lorenzo's progress, and possibly to let him win by completing it
     */
   public LorenzoIlMagnifico(GameBoard gameBoard) {
       this.gameBoard = gameBoard;
       this.papalPath = this.gameBoard.getPlayers().get(0).getDashboard().getPapalPath();
       this.tokenDeck = new TokenDeck();
    }

    /**
     * the move depends on the token drawn. Each time an action is made the counter goes up, ensuring a token isn't drawn two times unless a reshuffle happens
     */
    public void playTurn(){
       tokenDeck.getToken(consecutiveMoves).tokenEffect(papalPath, this, gameBoard);
        if (this.getFaithPosition()==24) lorenzoWins();
       consecutiveMoves++;
    }

    /**
     * this method can get called only by the black cross token, and is used to reset Lorenzo's list of possible actions
     * consecutiveMoves is set to -1 because after this method is completed playTurn() still has to do consecutiveMoves++, so they even out
     */
    public void resetTokenDeck(){
       consecutiveMoves=-1;
       tokenDeck.shuffleDeck();
    }

    /**
     * TODO
     */
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
}