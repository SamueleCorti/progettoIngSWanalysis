package it.polimi.ingsw.Exceptions;


public class NotEnoughResourcesException extends Exception{

    public NotEnoughResourcesException()    {super("Poor ass lmaooooo, ");}

    @Override
    public String toString() {
        //return super.toString();
        return getMessage()+ "get rich by clicking here! (banks hate him)";
    }
}