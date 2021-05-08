package it.polimi.ingsw.Exceptions;

public class allThePlayersAreConnectedException extends Exception {
    public allThePlayersAreConnectedException() {
        super("Error: ");
    }

    @Override
    public String toString() {
        //return super.toString();
        return getMessage() + "all the players of this game are connected, please create or join another game";
    }
}
