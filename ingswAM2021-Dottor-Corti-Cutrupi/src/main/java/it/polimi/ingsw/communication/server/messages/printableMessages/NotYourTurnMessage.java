package it.polimi.ingsw.communication.server.messages.printableMessages;

public class NotYourTurnMessage implements PrintableMessage{
    String string = "It's not your turn, you must wait until it is before asking" +
            " for an action";

    public String getString() {
        return string;
    }
}
