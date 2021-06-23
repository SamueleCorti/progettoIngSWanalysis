package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;

public class PlayerRejoinedTheMatch implements PrintableMessage {
    private String string;

    public PlayerRejoinedTheMatch(String nickname) {
        this.string = "Player "+nickname+" has reconnected to the game";
    }

    public String getString() {
        return string;
    }


    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(!isGui) System.out.println(string);
    }
}
