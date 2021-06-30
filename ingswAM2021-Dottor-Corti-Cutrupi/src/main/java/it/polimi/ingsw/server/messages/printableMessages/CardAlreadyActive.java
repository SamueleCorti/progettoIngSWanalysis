package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;

/**
 * Self explanatory name
 */
public class CardAlreadyActive implements PrintableMessage {
    private String string = "The leader card you selected is already active!";

    public String getString() {
        return string;
    }

    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(!isGui) System.out.println(string);
    }
}
