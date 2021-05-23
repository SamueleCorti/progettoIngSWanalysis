package it.polimi.ingsw.communication.server.messages.printableMessages;

public class NotNewResources implements PrintableMessage{
    String string = "There was a problem, you tried to eliminate a depot with resources not just taken from market";

    public String getString() {
        return string;
    }
}
