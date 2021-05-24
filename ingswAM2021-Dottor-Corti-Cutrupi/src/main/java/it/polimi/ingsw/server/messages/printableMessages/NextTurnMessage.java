package it.polimi.ingsw.server.messages.printableMessages;

public class NextTurnMessage implements PrintableMessage {
    String string;

    public NextTurnMessage(String nickname) {
        string = "It's "+nickname+"'s turn";
    }

    public String getString() {
        return string;
    }
}
