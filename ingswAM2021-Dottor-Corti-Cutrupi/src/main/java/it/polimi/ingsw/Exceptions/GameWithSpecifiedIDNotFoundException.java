package it.polimi.ingsw.Exceptions;

public class GameWithSpecifiedIDNotFoundException extends Exception {
    public GameWithSpecifiedIDNotFoundException() {
        super("Error: ");
    }

    @Override
    public String toString() {
        //return super.toString();
        return getMessage() + "the ID of the game you insert is not correct, please " +
                "insert a correct one or create/join another match";
    }
}
