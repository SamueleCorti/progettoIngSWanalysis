package it.polimi.ingsw.Model.LorenzoIlMagnifico;

import it.polimi.ingsw.Exceptions.LorenzoWonTheMatch;
import it.polimi.ingsw.Model.boardsAndPlayer.GameBoard;
import it.polimi.ingsw.Model.papalpath.PapalPath;

public class BlackCrossToken implements Token{

    /**
     * Token that moves Lorenzo forward one tile on the papal path, then resets the token deck
     */
    public BlackCrossToken() {
    }

    /**
     * moves Lorenzo forward one tile on the papal path, then resets the token deck
     */
     public void tokenEffect(PapalPath papalPath,LorenzoIlMagnifico lorenzoIlMagnifico, GameBoard gameBoard) throws LorenzoWonTheMatch {
        papalPath.moveForwardLorenzo();
        lorenzoIlMagnifico.resetTokenDeck();
    }

    @Override
    public String toString() {
        return "Black cross";
    }
}