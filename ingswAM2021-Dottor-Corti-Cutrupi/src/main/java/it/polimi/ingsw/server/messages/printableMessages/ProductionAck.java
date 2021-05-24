package it.polimi.ingsw.server.messages.printableMessages;

public class ProductionAck implements PrintableMessage {
    String string="Production activated successfully";

    public String getString() {
        return string;
    }
}
