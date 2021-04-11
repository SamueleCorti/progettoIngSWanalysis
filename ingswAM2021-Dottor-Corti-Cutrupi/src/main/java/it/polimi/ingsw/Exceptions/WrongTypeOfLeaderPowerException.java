package it.polimi.ingsw.Exceptions;

public class WrongTypeOfLeaderPowerException extends Exception{
    //used when the player tries to use the wrong type of power for a leader card
    public WrongTypeOfLeaderPowerException() {
        super("Error: ");
    }

    @Override
    public String toString() {
        //return super.toString();
        return getMessage() + "the selected leader card doesn't have the type of power needed to complete this action";
    }
}
