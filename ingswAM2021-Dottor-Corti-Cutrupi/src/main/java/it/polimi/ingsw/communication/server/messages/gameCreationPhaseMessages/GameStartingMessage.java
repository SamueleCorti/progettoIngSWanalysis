package it.polimi.ingsw.communication.server.messages.gameCreationPhaseMessages;

import it.polimi.ingsw.communication.server.messages.Message;

public class GameStartingMessage implements Message {
    private final String message;

    public GameStartingMessage() {
        message = "The game has started";
    }

    public String getMessage() {
        return message;
    }
}
