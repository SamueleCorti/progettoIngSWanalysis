package it.polimi.ingsw.server.messages.printableMessages;

public class WrongLeaderCardIndex implements PrintableMessage {
    String string = "There's no leader card at the index you selected";

    public String getString() {
        return string;
    }
}
