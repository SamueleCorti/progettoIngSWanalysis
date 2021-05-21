package it.polimi.ingsw.communication.server.messages;

public class GenericMessage implements Message{
    private final String string;

    public GenericMessage(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
