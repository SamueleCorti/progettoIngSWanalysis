package it.polimi.ingsw.Exceptions;

public class NotEnoughResourcesToActivateProductionException extends Exception {
    //used when the player tries to activate a production, but doesn't have enough resources
    public NotEnoughResourcesToActivateProductionException() {
        super("Error: ");
    }

    @Override
    public String toString() {
        //return super.toString();
        return getMessage() + "you don't have enough resources to activate this production";
    }
}
