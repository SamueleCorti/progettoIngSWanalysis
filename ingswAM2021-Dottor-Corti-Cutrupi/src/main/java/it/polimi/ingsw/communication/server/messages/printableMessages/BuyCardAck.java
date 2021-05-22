package it.polimi.ingsw.communication.server.messages.printableMessages;

import it.polimi.ingsw.communication.server.messages.Message;

public class BuyCardAck implements PrintableMessage {
    String string = "you've correctly bought the card!";

    public String getString() {
        return string;
    }
}
