package it.polimi.ingsw.LorenzoIlMagnifico;

import it.polimi.ingsw.developmentcard.Color;
import it.polimi.ingsw.papalpath.PapalPath;

public class DiscardToken implements Token{
    Color color;

    public DiscardToken(Color color) {
        this.color = color;
    }

    public void tokenEffect(PapalPath papalPath, LorenzoIlMagnifico lorenzoIlMagnifico){
        //calls two time the method to get a card (of the lowest tier possible) of the same color of the token
    }

    @Override
    public String toString() {
        return "Discard "+this.color;
    }
}
