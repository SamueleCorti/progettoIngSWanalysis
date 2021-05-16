package it.polimi.ingsw.Model.LorenzoIlMagnifico;

import it.polimi.ingsw.Model.boardsAndPlayer.GameBoard;
import it.polimi.ingsw.Model.papalpath.PapalPath;

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
