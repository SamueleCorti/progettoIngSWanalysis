package it.polimi.ingsw.communication.server.messages.printableMessages;

import it.polimi.ingsw.communication.server.messages.Message;

public class PlayerJoinedTheMatch implements PrintableMessage {
    String string;
    public PlayerJoinedTheMatch(String nickname) {
        string = "Player "+ nickname+" joined the game";
    }

    public String getString() {
        return string;
    }
}
