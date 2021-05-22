package it.polimi.ingsw.communication.server.messages.printableMessages;

import it.polimi.ingsw.communication.server.messages.Message;

public class YouMustDiscardResources implements PrintableMessage {
    String string = "There's an exceeding amount of resources in one depot of the warehouse," +
            " you must delete resources to fix this problem"+"To do so, you have to perform a discard resource action " +
            "[e.g. discardresources coin stone]";

    public String getString() {
        return string;
    }
}
