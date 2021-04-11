package it.polimi.ingsw.Exceptions;

public class GenericWrongIndexException extends Exception{
    public GenericWrongIndexException() {
        super("Error: ");
    }

    @Override
    public String toString() {
        //return super.toString();
        return getMessage() + "invalid index, try again";
    }
}
