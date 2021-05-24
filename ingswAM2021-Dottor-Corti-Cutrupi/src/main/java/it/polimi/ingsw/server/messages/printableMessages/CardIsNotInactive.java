package it.polimi.ingsw.server.messages.printableMessages;

public class CardIsNotInactive implements PrintableMessage{
    String string = "The selected card is not in your hand anymore (you activated it): you can't discard it!";

    public String getString() {
        return string;
    }
}
