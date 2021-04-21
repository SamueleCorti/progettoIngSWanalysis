package it.polimi.ingsw.LorenzoIlMagnifico;

import it.polimi.ingsw.GameBoard;
import it.polimi.ingsw.papalpath.PapalPath;

public class DoubleBlackCrossToken implements Token{

    /**
     * token that advances Lorenzo of two tiles in the papal path
     */
    public DoubleBlackCrossToken() {
    }

    /**
     * Lorenzo advances towo tiles in the papal path
     */
    public void tokenEffect(PapalPath papalPath, LorenzoIlMagnifico lorenzoIlMagnifico, GameBoard gameBoard){
        papalPath.moveForwardLorenzo(2);
    }

    @Override
    public String toString() {
        return "Double black cross";
    }
}
