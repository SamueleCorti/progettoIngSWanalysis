package it.polimi.ingsw.Exceptions;

public class ActivatingLeaderCardsUsingWrongIndexException extends Exception{
    //used when the player wants to activate a leader card that doesn't exist, e.g.: a card with index -1
    public ActivatingLeaderCardsUsingWrongIndexException() {
        super("Error: ");
    }

    @Override
    public String toString() {
        //return super.toString();
        return getMessage() + "the allowed indexes are 0 for the first card and 1 for the second. Repeat the action with a different index";
    }
}
