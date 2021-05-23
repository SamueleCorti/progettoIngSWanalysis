package it.polimi.ingsw.communication.server.messages.printableMessages;

import it.polimi.ingsw.communication.server.messages.Message;

public class WrongZoneInProduce implements PrintableMessage {
    String string = "There is no card activable in the selected dev zone";

    public String getString() {
        return string;
    }
}
