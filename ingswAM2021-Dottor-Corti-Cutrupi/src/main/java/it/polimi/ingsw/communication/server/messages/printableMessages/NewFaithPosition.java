package it.polimi.ingsw.communication.server.messages.printableMessages;

import it.polimi.ingsw.communication.server.messages.Message;

public class NewFaithPosition implements PrintableMessage {
    String string;

    public NewFaithPosition(int faithPosition) {
        string="Your faith position is "+faithPosition;
    }

    public String getString() {
        return string;
    }
}
