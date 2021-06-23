package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;

public class YouMustDeleteADepotFirst implements PrintableMessage {
    String string = "You're not allowed to do that, as right now you have to delete a depot";

    public String getString() {
        return string;
    }

    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(!isGui) System.out.println(string);
    }
}
