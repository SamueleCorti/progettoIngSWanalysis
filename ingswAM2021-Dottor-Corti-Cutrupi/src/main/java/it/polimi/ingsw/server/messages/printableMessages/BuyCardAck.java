package it.polimi.ingsw.server.messages.printableMessages;

public class BuyCardAck implements PrintableMessage {
    String string = "you've correctly bought the card!";

    public String getString() {
        return string;
    }
}
