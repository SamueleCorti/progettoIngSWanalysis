package it.polimi.ingsw.server.messages.printableMessages;

public class ActivatedLeaderCardAck implements PrintableMessage {
    String string = "Leader card activated correctly!";

    public String getString() {
        return string;
    }
}
