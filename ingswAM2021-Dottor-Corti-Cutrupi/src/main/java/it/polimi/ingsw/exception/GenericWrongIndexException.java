package it.polimi.ingsw.exception;

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
