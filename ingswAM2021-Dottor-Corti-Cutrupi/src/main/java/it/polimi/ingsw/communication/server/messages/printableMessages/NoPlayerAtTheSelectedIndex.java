package it.polimi.ingsw.communication.server.messages.printableMessages;

public class NoPlayerAtTheSelectedIndex implements PrintableMessage {
    String string = "There's no player associated to the index you insert";

    public String getString() {
        return string;
    }
}
