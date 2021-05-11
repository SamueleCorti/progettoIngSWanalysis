package it.polimi.ingsw.Exceptions;

public class NoGameFoundException extends Exception {
    public NoGameFoundException() {
        super("Error: ");
    }

    @Override
    public String toString() {
        //return super.toString();
        return getMessage() + "There are 0 matches in lobby at the moment, please try later";
    }
}
