package it.polimi.ingsw.server.messages.printableMessages;

public class NewHostMessage implements PrintableMessage {
    String string;

    public NewHostMessage(String s) {
        string = s+" is the new host";
    }

    public String getString() {
        return string;
    }
}
