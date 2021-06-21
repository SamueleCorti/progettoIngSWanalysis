package it.polimi.ingsw.server.messages.printableMessages;

public class LastRoundOfMatch implements PrintableMessage {
    String string="Someone has fulfilled the conditions to end the game; when the last rotation of turns " +
            "will finish the winner will be declared!";

    public String getString() {
        return string;
    }
}
