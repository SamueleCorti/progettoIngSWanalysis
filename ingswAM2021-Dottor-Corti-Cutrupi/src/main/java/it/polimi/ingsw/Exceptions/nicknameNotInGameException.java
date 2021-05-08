package it.polimi.ingsw.Exceptions;

public class nicknameNotInGameException extends Exception {
    public nicknameNotInGameException() {
        super("Error: ");
    }

    @Override
    public String toString() {
        //return super.toString();
        return getMessage() + "there's no free spot related to the nickname you insert. Please try again";
    }
}
