package it.polimi.ingsw.Communication.server.messages.GameCreationPhaseMessages;

import it.polimi.ingsw.Communication.server.messages.Message;

public class GameStartingMessage implements Message {
    private final String message;

    public GameStartingMessage() {
        message = "The game has started";
    }

    public String getMessage() {
        return message;
    }
}
