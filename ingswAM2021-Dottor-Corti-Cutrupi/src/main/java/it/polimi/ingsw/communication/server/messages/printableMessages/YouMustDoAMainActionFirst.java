package it.polimi.ingsw.communication.server.messages.printableMessages;

import it.polimi.ingsw.communication.server.messages.Message;

public class YouMustDoAMainActionFirst implements PrintableMessage {
    String string = "You can't end your turn until you make a main action";

    public String getString() {
        return string;
    }
}
