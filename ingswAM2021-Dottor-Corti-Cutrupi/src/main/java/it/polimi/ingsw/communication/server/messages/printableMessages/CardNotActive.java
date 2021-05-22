package it.polimi.ingsw.communication.server.messages.printableMessages;

import it.polimi.ingsw.communication.server.messages.Message;

public class CardNotActive implements PrintableMessage {
    String string = "The card you selected is not active";

    public String getString() {
        return string;
    }
}
