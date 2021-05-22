package it.polimi.ingsw.communication.server.messages.printableMessages;

import it.polimi.ingsw.communication.server.messages.Message;

public class ResourcesUsableForProd implements PrintableMessage {
    String string;

    public ResourcesUsableForProd(String s) {
        string = "Resources available for more productions: "+s;
    }

    public String getString() {
        return string;
    }
}
