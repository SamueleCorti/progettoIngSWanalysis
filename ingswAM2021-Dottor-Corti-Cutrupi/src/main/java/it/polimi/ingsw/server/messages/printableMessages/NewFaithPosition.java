package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;

public class NewFaithPosition implements PrintableMessage {
    private String string;

    public NewFaithPosition(int faithPosition) {
        string="Your faith position is "+faithPosition;
    }

    public String getString() {
        return string;
    }

    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(!isGui) System.out.println(string);
    }
}
