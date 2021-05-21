package it.polimi.ingsw.exception;

public class NotCoherentLevelException extends Exception{
    public NotCoherentLevelException()    {super("Error: ");}

    @Override
    public String toString() {
        //return super.toString();
        return getMessage()+ "you can't put the card in the selected zone";
    }
}
