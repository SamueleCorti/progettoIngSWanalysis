package it.polimi.ingsw.LorenzoIlMagnifico;

import it.polimi.ingsw.GameBoard;
import it.polimi.ingsw.papalpath.PapalPath;

public class BlackCrossToken implements Token{


    //this particular token takes Lorenzo as a parameter to call the method needed to effectively reset the possible actions
    public BlackCrossToken() {
    }

    //one move on the papal path and shuffles all the tokens to reset lorenzo's possible actions.
    public void tokenEffect(PapalPath papalPath,LorenzoIlMagnifico lorenzoIlMagnifico, GameBoard gameBoard){
        papalPath.moveForwardLorenzo();
        lorenzoIlMagnifico.resetTokenDeck();
    }

    @Override
    public String toString() {
        return "Black cross";
    }
}
