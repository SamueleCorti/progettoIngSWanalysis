package it.polimi.ingsw.Exceptions;

public class TwoWhiteToColorException extends Exception{
    //used when the player wants to activate a leader card that doesn't exist, e.g.: a card with index -1
    public TwoWhiteToColorException() {
        super("Error: ");
    }

    @Override
    public String toString() {
        //return super.toString();
        return getMessage() + "you selected the wrong method since you have multiple leader cards with WhiteToColor effect.";
    }
}
