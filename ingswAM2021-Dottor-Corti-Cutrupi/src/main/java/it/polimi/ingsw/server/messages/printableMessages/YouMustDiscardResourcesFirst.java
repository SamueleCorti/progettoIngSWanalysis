package it.polimi.ingsw.server.messages.printableMessages;

public class YouMustDiscardResourcesFirst implements PrintableMessage {
    String string = "You're not allowed to do that, as right now you have to discard exceeding resources";

    public String getString() {
        return string;
    }
}
