package it.polimi.ingsw.Exceptions;


public class NotEnoughResourcesException extends Exception{

    public NotEnoughResourcesException()    {super("Error: ");}

    @Override
    public String toString() {
        //return super.toString();
        return getMessage()+ "you don't have enough resources to buy this card";
    }
}