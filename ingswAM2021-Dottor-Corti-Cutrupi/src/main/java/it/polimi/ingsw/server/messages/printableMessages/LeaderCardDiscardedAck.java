package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;

public class LeaderCardDiscardedAck implements PrintableMessage{
    private String string;

    public String getString() {
        return string;
    }

    public LeaderCardDiscardedAck(int index) {
        this.string = "You have successfully removed the card at index "+index;
    }

    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(!isGui) System.out.println(string);
    }
}
