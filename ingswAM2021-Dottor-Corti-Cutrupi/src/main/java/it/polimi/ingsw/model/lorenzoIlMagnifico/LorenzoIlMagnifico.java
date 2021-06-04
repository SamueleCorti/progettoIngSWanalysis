package it.polimi.ingsw.model.lorenzoIlMagnifico;

import it.polimi.ingsw.exception.BothPlayerAndLorenzoActivatePapalCardException;
import it.polimi.ingsw.exception.LorenzoActivatesPapalCardException;
import it.polimi.ingsw.exception.LorenzoWonTheMatch;
import it.polimi.ingsw.model.boardsAndPlayer.GameBoard;
import it.polimi.ingsw.model.papalpath.PapalPath;

public class LorenzoIlMagnifico {

    private TokenDeck tokenDeck;
    private PapalPath papalPath;
    private transient GameBoard gameBoard;
    int consecutiveMoves=0;

    /**
     * Constructor, needs to be linked with a papal path in order to keep track of Lorenzo's progress, and possibly to let him win by completing it
     */
   public LorenzoIlMagnifico(GameBoard gameBoard) {
       this.gameBoard = gameBoard;
       this.papalPath = this.gameBoard.getPlayers().get(0).getDashboardCopy().getPapalPath();
       this.tokenDeck = new TokenDeck();
    }

    /**
     * the move depends on the token drawn. Each time an action is made the counter goes up, ensuring a token isn't drawn two times unless a reshuffle happens
     */
    public Token playTurn() throws LorenzoWonTheMatch, LorenzoActivatesPapalCardException, BothPlayerAndLorenzoActivatePapalCardException {
        Token tokenUsed = tokenDeck.getToken(consecutiveMoves);
        tokenDeck.getToken(consecutiveMoves).tokenEffect(papalPath,this, gameBoard);
        consecutiveMoves++;
        return tokenUsed;
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
       return this.papalPath.getFaithPositionLorenzo();
    }

    @Override
    public String toString() {
        return "LorenzoIlMagnifico{" +
                "tokenDeck=" + tokenDeck +
                ", papalPath=" + papalPath +
                ", consecutiveMoves=" + consecutiveMoves +
                '}';
    }
}
