package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;

public class WrongLeaderCardIndex implements PrintableMessage {
    String string = "There's no leader card at the index you selected";

    public String getString() {
        return string;
    }


    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(!isGui) System.out.println(string);
    }
}
