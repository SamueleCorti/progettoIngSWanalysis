package it.polimi.ingsw.communication.server.messages.printableMessages;

import it.polimi.ingsw.communication.server.messages.Message;

public class ResourcesProduced implements PrintableMessage {
    String string;

    public ResourcesProduced(String s) {
        string = "Resources produced: "+s;
    }

    public String getString() {
        return string;
    }
}
