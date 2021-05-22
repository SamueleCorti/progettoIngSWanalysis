package it.polimi.ingsw.communication.server.messages.printableMessages;

import it.polimi.ingsw.communication.server.messages.Message;

public class NextTurnMessage implements PrintableMessage {
    String string;

    public NextTurnMessage(String nickname) {
        string = "It's "+nickname+"'s turn";
    }

    public String getString() {
        return string;
    }
}
