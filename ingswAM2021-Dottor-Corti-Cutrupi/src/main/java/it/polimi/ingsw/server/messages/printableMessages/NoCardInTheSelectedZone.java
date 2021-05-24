package it.polimi.ingsw.server.messages.printableMessages;

public class NoCardInTheSelectedZone implements PrintableMessage {
    String string = "There is no card activable in the selected zone";

    public String getString() {
        return string;
    }
}
