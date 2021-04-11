package it.polimi.ingsw.Exceptions;

public class LeaderCardNotActiveException extends Exception{
    //used when the player tries to use the power of a leader card that hasn't been activated yet
    public LeaderCardNotActiveException() {
        super("Error: ");
    }

    @Override
    public String toString() {
        //return super.toString();
        return getMessage() + "the selected leader card hasn't been activated yet, you can't use its power";
    }
}
