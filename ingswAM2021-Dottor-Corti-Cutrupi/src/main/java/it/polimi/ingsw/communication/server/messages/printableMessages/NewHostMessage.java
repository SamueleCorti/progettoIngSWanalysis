package it.polimi.ingsw.communication.server.messages.printableMessages;

import it.polimi.ingsw.communication.server.messages.Message;

public class NewHostMessage implements PrintableMessage {
    String string;

    public NewHostMessage(String s) {
        string = s+" is the new host";
    }

    public String getString() {
        return string;
    }
}
