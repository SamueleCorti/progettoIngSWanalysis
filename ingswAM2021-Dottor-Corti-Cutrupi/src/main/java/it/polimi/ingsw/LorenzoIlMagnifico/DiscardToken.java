package it.polimi.ingsw.LorenzoIlMagnifico;

import it.polimi.ingsw.developmentcard.Color;

public class DiscardToken extends Token{
    Color color;

    public DiscardToken(Color color) {
        this.color = color;
    }

    public void tokenEffect(){
        //calls two time the method to get a card (of the lowest tier possible) of the same color of the token,
    }
}
