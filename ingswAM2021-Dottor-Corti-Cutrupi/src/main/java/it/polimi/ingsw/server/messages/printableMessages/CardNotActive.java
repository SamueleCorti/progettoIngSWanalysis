package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;

public class CardNotActive implements PrintableMessage {
    String string = "The card you selected is not active";

    public String getString() {
        return string;
    }

    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {

    }
}
