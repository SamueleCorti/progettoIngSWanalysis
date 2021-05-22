package it.polimi.ingsw.communication.server.messages.printableMessages;

import it.polimi.ingsw.communication.server.messages.Message;

public class MessageReceivedFromServerMessage implements PrintableMessage {
    String string="Message received from server";

    public String getString() {
        return string;
    }
}
