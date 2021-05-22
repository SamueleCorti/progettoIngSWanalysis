package it.polimi.ingsw.communication.server.messages.printableMessages;

import it.polimi.ingsw.communication.server.messages.Message;

public class ProductionAck implements PrintableMessage {
    String string="Production activated successfully";

    public String getString() {
        return string;
    }
}
