package it.polimi.ingsw.Communication.server.messages;

public class GameStartingMessage implements Message{
    private final String message;

    public GameStartingMessage() {
        message = "The game has started";
    }

    public String getMessage() {
        return message;
    }
}
