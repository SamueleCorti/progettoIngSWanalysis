package it.polimi.ingsw.communication.server.messages.printableMessages;

public class InvalidIndexWhiteToColor implements PrintableMessage{
    String string = "You must insert only valid indexes for your white to color cards";

    public String getString() {
        return string;
    }
}
