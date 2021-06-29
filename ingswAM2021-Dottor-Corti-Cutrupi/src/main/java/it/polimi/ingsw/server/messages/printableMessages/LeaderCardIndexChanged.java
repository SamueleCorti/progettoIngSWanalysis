package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;

/**
 * Self explanatory name
 */
public class LeaderCardIndexChanged implements PrintableMessage{
    private String string = "Now card at index 0 is the card that previously was at index 1";

    public String getString() {
        return string;
    }


    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(!isGui) System.out.println(string);
    }
}
