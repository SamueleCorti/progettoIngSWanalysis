package it.polimi.ingsw.server.messages.printableMessages;

public class PlayerRejoinedTheMatch implements PrintableMessage {
    String string;

    public PlayerRejoinedTheMatch(String nickname) {
        this.string = "Player "+nickname+" has reconnected to the game";
    }

    public String getString() {
        return string;
    }
}
