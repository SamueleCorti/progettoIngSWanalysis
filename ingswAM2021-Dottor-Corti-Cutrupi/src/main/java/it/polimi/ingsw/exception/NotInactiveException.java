package it.polimi.ingsw.exception;

public class NotInactiveException extends Exception{
    //used when the player tries to activate a leader card, but it isn't inactive as it should be
    public NotInactiveException() {
        super("Error: ");
    }

    @Override
    public String toString() {
        //return super.toString();
        return getMessage() + "the card you're trying to activate is either already active or has been discarded in the past.\n" +
                "The action you requested hasn't been completed (as per the rules)";
    }
}
