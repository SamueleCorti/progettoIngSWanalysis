package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;

public class PrintAString implements PrintableMessage {
    private String string;

    public PrintAString(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(!isGui) System.out.println(string);
    }
}
