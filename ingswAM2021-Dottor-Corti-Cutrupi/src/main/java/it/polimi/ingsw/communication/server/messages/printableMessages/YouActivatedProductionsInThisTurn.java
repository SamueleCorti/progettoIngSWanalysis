package it.polimi.ingsw.communication.server.messages.printableMessages;

import it.polimi.ingsw.communication.server.messages.Message;

public class YouActivatedProductionsInThisTurn implements PrintableMessage {
    String string = "This turn you're activating your " +
            "productions. You can either pass your turn or keep on activating them";

    public String getString() {
        return string;
    }
}
