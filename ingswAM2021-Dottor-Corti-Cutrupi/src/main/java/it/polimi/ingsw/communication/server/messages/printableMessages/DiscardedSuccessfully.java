package it.polimi.ingsw.communication.server.messages.printableMessages;

public class DiscardedSuccessfully implements PrintableMessage {
    String string = "All players will now advance of one tile in papal " +
            "path, because you discarded a resource.\n"+"You successfully deleted a resource";

    public String getString() {
        return string;
    }
}
