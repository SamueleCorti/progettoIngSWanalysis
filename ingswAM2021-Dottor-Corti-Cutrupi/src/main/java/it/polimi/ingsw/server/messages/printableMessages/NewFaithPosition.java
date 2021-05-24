package it.polimi.ingsw.server.messages.printableMessages;

public class NewFaithPosition implements PrintableMessage {
    String string;

    public NewFaithPosition(int faithPosition) {
        string="Your faith position is "+faithPosition;
    }

    public String getString() {
        return string;
    }
}
