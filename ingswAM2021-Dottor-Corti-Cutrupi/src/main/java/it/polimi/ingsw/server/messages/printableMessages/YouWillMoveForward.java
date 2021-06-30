package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;

/**
 * Self explanatory name
 */
public class YouWillMoveForward implements PrintableMessage {
    String string;

    public YouWillMoveForward(String nickname) {
        string = "As "+ nickname+ " discarded a resource, you'll now advance of one" +
                "tile in the papal path";
    }

    public String getString() {
        return string;
    }


    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(!isGui) System.out.println(string);
    }
}
