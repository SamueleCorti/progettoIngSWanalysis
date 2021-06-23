package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;

public class DiscardedSuccessfully implements PrintableMessage {
    private String string = "All players will now advance of one tile in papal " +
            "path, because you discarded a resource.\n"+"You successfully deleted a resource";

    public String getString() {
        return string;
    }

    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(!isGui) System.out.println(string);
    }
}
