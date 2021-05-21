package it.polimi.ingsw.exception;

public class NotCoherentResourceInArrayWhiteToColorException extends Exception{
    //used when the player wants to activate a leader card that doesn't exist, e.g.: a card with index -1
    public NotCoherentResourceInArrayWhiteToColorException() {
        super("Error: ");
    }

    @Override
    public String toString() {
        //return super.toString();
        return getMessage() + "you chose resources you are not able to transform to because you haven't its related WhiteToColor leader card";
    }
}

