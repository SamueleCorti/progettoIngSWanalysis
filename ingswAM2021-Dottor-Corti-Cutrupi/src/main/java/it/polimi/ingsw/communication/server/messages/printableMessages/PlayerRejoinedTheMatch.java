package it.polimi.ingsw.communication.server.messages.printableMessages;

import it.polimi.ingsw.communication.server.messages.Message;

public class PlayerRejoinedTheMatch implements PrintableMessage {
    String string;

    public PlayerRejoinedTheMatch(String nickname) {
        this.string = "Player "+nickname+" has reconnected to the game";
    }

    public String getString() {
        return string;
    }
}
