package it.polimi.ingsw.server.messages.printableMessages;

public class PlayerJoinedTheMatch implements PrintableMessage {
    String string;
    public PlayerJoinedTheMatch(String nickname) {
        string = "Player "+ nickname+" joined the game";
    }

    public String getString() {
        return string;
    }
}
