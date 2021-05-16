package it.polimi.ingsw.Model.storing;

public class notEnoughResourcesException extends Exception {

    public notEnoughResourcesException()
    {
        super("Problem in production, ");
    }

    @Override
    public String toString() {
        return getMessage() + "you don't have enough resources to activate this production";
    }
}
