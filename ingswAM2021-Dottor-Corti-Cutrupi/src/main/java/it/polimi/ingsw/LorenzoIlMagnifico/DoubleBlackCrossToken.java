package it.polimi.ingsw.LorenzoIlMagnifico;

import it.polimi.ingsw.papalpath.PapalPath;

public class DoubleBlackCrossToken extends Token{

    PapalPath papalPath;

    public DoubleBlackCrossToken(PapalPath papalPath) {
        this.papalPath = papalPath;
    }

    //Lorenzo moves forward by 2 in his own papal path
    public void tokenEffect(){
        papalPath.moveForwardLorenzo(2);
    }

    @Override
    public String toString() {
        return "Double black cross";
    }
}
