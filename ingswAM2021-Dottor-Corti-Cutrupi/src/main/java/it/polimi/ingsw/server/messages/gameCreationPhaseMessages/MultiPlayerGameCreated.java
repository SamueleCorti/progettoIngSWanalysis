package it.polimi.ingsw.server.messages.gameCreationPhaseMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import it.polimi.ingsw.server.messages.printableMessages.PrintableMessage;

public class MultiPlayerGameCreated implements PrintableMessage {
    String string = "Multi-player game created";

    public String getString() {
        return string;
    }

    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {

    }
}
