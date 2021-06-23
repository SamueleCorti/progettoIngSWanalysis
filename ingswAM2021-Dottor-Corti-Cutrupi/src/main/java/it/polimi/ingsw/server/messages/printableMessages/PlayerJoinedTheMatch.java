package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;

public class PlayerJoinedTheMatch implements PrintableMessage {
    private String string;
    public PlayerJoinedTheMatch(String nickname) {
        string = "Player "+ nickname+" joined the game";
    }

    public String getString() {
        return string;
    }


    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(!isGui) System.out.println(string);
    }
}
