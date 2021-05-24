package it.polimi.ingsw.server.messages.printableMessages;

public class YouMustDeleteADepotFirst implements PrintableMessage {
    String string = "You're not allowed to do that, as right now you have to delete a depot";

    public String getString() {
        return string;
    }
}
