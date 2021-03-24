package it.polimi.ingsw.LorenzoIlMagnifico;

import it.polimi.ingsw.papalpath.PapalPath;

public class DoubleBlackCrossToken implements Token{


    public DoubleBlackCrossToken() {
    }

    //Lorenzo moves forward by 2 in his own papal path
    public void tokenEffect(PapalPath papalPath, LorenzoIlMagnifico lorenzoIlMagnifico){
        papalPath.moveForwardLorenzo(2);
    }

    @Override
    public String toString() {
        return "Double black cross";
    }
}
