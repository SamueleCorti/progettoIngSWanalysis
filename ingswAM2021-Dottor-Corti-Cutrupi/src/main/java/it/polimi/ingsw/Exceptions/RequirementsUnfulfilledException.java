package it.polimi.ingsw.Exceptions;

public class RequirementsUnfulfilledException extends Exception{
    //used when the player tries to activate a leader card, but it isn't inactive as it should be
    public RequirementsUnfulfilledException() {
        super("Error: ");
    }

    @Override
    public String toString() {
        //return super.toString();
        return getMessage() + "the action you requested can't be completed because one or more requirements aren't fulfilled. \n" +
                "Try with something else please.";
    }
}
