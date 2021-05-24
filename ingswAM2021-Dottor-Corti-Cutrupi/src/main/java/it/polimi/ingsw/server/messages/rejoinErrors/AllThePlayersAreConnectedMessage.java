package it.polimi.ingsw.server.messages.rejoinErrors;

public class AllThePlayersAreConnectedMessage extends RejoinErrorMessage {
    private final String string = "All the players of the game you want to rejoin are connected, please create or join another game";

    public String getString() {
        return string;
    }
}
