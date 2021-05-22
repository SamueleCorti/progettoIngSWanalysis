package it.polimi.ingsw.communication.server.messages.printableMessages;

import it.polimi.ingsw.communication.server.messages.Message;

public class MainActionAlreadyDoneMessage implements PrintableMessage {
    String string = "You already did one of the main actions. Try with something else or end your turn";

    public String getString() {
        return string;
    }
}
