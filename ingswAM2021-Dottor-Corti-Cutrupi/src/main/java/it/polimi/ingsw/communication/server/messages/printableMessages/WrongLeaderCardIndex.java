package it.polimi.ingsw.communication.server.messages.printableMessages;

import it.polimi.ingsw.communication.server.messages.Message;

public class WrongLeaderCardIndex implements PrintableMessage {
    String string = "There's no leader card at the index you selected";

    public String getString() {
        return string;
    }
}
