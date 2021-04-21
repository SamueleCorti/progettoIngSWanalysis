package it.polimi.ingsw.LorenzoIlMagnifico;

import it.polimi.ingsw.GameBoard;
import it.polimi.ingsw.papalpath.PapalPath;

/**
 * token that advances Lorenzo of two tiles in the papal path
 */
public class DoubleBlackCrossToken implements Token{
    public DoubleBlackCrossToken() {
    }

    /**
     * Lorenzo advances two tiles in the papal path
     */
    public void tokenEffect(PapalPath papalPath, LorenzoIlMagnifico lorenzoIlMagnifico, GameBoard gameBoard){
        papalPath.moveForwardLorenzo(2);
    }

    @Override
    public String toString() {
        return "Double black cross";
    }
}
