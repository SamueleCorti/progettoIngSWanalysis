package it.polimi.ingsw.model.lorenzoIlMagnifico;

import it.polimi.ingsw.exception.BothPlayerAndLorenzoActivatePapalCardException;
import it.polimi.ingsw.exception.LorenzoActivatesPapalCardException;
import it.polimi.ingsw.exception.LorenzoWonTheMatch;
import it.polimi.ingsw.model.boardsAndPlayer.GameBoard;
import it.polimi.ingsw.model.papalpath.PapalPath;

/**
 * token that advances Lorenzo of two tiles in the papal path
 */
public class DoubleBlackCrossToken implements Token{
    public DoubleBlackCrossToken() {
    }

    /**
     * Lorenzo advances two tiles in the papal path
     */
    public void tokenEffect(PapalPath papalPath, LorenzoIlMagnifico lorenzoIlMagnifico, GameBoard gameBoard) throws LorenzoWonTheMatch, LorenzoActivatesPapalCardException, BothPlayerAndLorenzoActivatePapalCardException {
        papalPath.moveForwardLorenzo(2);
    }

    @Override
    public String toString() {
        return "Double black cross";
    }
}
