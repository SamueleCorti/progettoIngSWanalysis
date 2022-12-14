package it.polimi.ingsw.exception;

/**
 * Thrown when the player required a line in the market that doesn't exist
 */
public class OutOfBoundException extends Exception{

    //used when the player tries to get a line that doesn't exist in the market
    public OutOfBoundException()    {super("Out of bound exception, ");}

    @Override
    public String toString() {
        return getMessage()+ "you required a line that doesn't exists";
    }
}
