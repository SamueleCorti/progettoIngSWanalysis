package it.polimi.ingsw.server.messages.printableMessages;

public class MainActionAlreadyDoneMessage implements PrintableMessage {
    String string = "You already did one of the main actions. Try with something else or end your turn";

    public String getString() {
        return string;
    }
}
