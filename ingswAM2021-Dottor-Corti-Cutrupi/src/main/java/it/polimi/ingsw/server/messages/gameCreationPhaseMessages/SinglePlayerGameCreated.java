package it.polimi.ingsw.server.messages.gameCreationPhaseMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import it.polimi.ingsw.server.messages.printableMessages.PrintableMessage;

public class SinglePlayerGameCreated implements PrintableMessage {
    String string = "Single-player game created";

    public String getString() {
        return string;
    }

    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {

    }
}
