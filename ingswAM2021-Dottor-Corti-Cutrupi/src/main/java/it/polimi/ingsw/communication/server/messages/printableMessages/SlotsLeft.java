package it.polimi.ingsw.communication.server.messages.printableMessages;

import it.polimi.ingsw.communication.server.messages.Message;

public class SlotsLeft implements PrintableMessage {
    String string;

    public SlotsLeft(int num) {
        this.string = num +" slots left";
    }

    public String getString() {
        return string;
    }
}
