package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;

public class ViewLorenzoError implements PrintableMessage {
    String string = "We cant show Lorenzo, because this is not a single player game!";

    public String getString() {
        return string;
    }


    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(!isGui) System.out.println(string);
    }
}
