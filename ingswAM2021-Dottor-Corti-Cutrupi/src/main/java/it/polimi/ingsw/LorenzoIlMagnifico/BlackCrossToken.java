package it.polimi.ingsw.LorenzoIlMagnifico;

import it.polimi.ingsw.papalpath.PapalPath;

public class BlackCrossToken extends Token{

    PapalPath papalPath;
    LorenzoIlMagnifico lorenzoIlMagnifico;

    //this particular token takes Lorenzo as a parameter to call the method needed to effectively reset the possible actions
    public BlackCrossToken(PapalPath papalPath,LorenzoIlMagnifico lorenzoIlMagnifico) {
        this.papalPath = papalPath;
        this.lorenzoIlMagnifico= lorenzoIlMagnifico;
    }

    //one move on the papal path and shuffles all the tokens to reset lorenzo's possible actions.
    public void tokenEffect(){
        papalPath.moveForwardLorenzo();
        lorenzoIlMagnifico.resetTokenDeck();
    }

}
