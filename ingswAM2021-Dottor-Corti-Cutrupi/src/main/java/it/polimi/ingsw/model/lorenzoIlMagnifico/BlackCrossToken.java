package it.polimi.ingsw.model.lorenzoIlMagnifico;

import it.polimi.ingsw.exception.BothPlayerAndLorenzoActivatePapalCardException;
import it.polimi.ingsw.exception.LorenzoActivatesPapalCardException;
import it.polimi.ingsw.exception.LorenzoWonTheMatch;
import it.polimi.ingsw.model.boardsAndPlayer.GameBoard;
import it.polimi.ingsw.model.papalpath.PapalPath;

public class BlackCrossToken implements Token{

    /**
     * Token that moves Lorenzo forward one tile on the papal path, then resets the token deck
     */
    public BlackCrossToken() {
    }

    /**
     * moves Lorenzo forward one tile on the papal path, then resets the token deck
     */
     public void tokenEffect(PapalPath papalPath,LorenzoIlMagnifico lorenzoIlMagnifico, GameBoard gameBoard) throws LorenzoWonTheMatch, LorenzoActivatesPapalCardException, BothPlayerAndLorenzoActivatePapalCardException {
        papalPath.moveForwardLorenzo();
        lorenzoIlMagnifico.resetTokenDeck();
    }

    @Override
    public String toString() {
        return "Black cross";
    }
}