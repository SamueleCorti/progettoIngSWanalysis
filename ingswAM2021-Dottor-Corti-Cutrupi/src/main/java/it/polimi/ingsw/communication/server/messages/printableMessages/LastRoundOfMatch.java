package it.polimi.ingsw.communication.server.messages.printableMessages;

import it.polimi.ingsw.communication.server.messages.Message;

public class LastRoundOfMatch implements PrintableMessage {
    String string="Someone has fulfilled the conditions to end the game; the last round of turns " +
            "will finish then we'll see who is the winner!";

    public String getString() {
        return string;
    }
}
