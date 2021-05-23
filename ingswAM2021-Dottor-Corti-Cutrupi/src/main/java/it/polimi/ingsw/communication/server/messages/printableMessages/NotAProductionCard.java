package it.polimi.ingsw.communication.server.messages.printableMessages;

import it.polimi.ingsw.communication.server.messages.Message;

public class NotAProductionCard implements PrintableMessage {
    String string = "The card you selected is not a production card, please try again";

    public String getString() {
        return string;
    }
}
