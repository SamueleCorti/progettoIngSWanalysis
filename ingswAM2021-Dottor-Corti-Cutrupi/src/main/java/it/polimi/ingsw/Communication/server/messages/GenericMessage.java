package it.polimi.ingsw.Communication.server.messages;

public class GenericMessage implements Message{
    private final String string;

    public GenericMessage(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
