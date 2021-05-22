package it.polimi.ingsw.communication.server.messages.printableMessages;

import it.polimi.ingsw.communication.server.messages.Message;

public class YouMustDeleteADepot implements PrintableMessage {
    String string = "There's a fourth depot in the warehouse, you must delete one"+"To do so, you have to perform a delete " +
            "depot action " +"[e.g. deletedepot 4]";

    public String getString() {
        return string;
    }
}
