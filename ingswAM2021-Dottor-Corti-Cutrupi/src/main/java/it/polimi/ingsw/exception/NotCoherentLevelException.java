package it.polimi.ingsw.exception;
/**
 * Thrown when choosing a development card zone that can't contain the card just bought
 */
public class NotCoherentLevelException extends Exception{
    public NotCoherentLevelException()    {super("Error: ");}

    @Override
    public String toString() {
        //return super.toString();
        return getMessage()+ "you can't put the card in the selected zone";
    }
}
