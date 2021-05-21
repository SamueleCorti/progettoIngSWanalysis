package it.polimi.ingsw.communication.server.messages.rejoinErrors;

public class GameWithSpecifiedIDNotFoundMessage extends RejoinErrorMessage {
    private final String string;

    public GameWithSpecifiedIDNotFoundMessage(int id) {
        this.string = "Game with ID="+id+" not found; try another id";
    }

    public String getString() {
        return string;
    }
}
