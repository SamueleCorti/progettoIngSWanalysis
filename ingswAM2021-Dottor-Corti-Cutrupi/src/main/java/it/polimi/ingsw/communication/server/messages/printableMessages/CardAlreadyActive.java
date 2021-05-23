package it.polimi.ingsw.communication.server.messages.printableMessages;

public class CardAlreadyActive implements PrintableMessage {
    String string = "The leader card you selected is already active!";

    public String getString() {
        return string;
    }
}
