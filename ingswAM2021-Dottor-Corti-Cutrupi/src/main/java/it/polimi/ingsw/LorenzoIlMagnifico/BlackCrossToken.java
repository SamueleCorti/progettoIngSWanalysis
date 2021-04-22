package it.polimi.ingsw.LorenzoIlMagnifico;

import it.polimi.ingsw.GameBoard;
import it.polimi.ingsw.papalpath.PapalPath;

public class BlackCrossToken implements Token{

    /**
     * Token that moves Lorenzo forward one tile on the papal path, then resets the token deck
     */
    public BlackCrossToken() {
    }

    /**
     * moves Lorenzo forward one tile on the papal path, then resets the token deck
     */
     public void tokenEffect(PapalPath papalPath,LorenzoIlMagnifico lorenzoIlMagnifico, GameBoard gameBoard){
        papalPath.moveForwardLorenzo();
        lorenzoIlMagnifico.resetTokenDeck();
    }

    @Override
    public String toString() {
        return "Black cross";
    }
}