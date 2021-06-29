package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;

/**
 * Self explanatory name
 */
public class NotAProductionCard implements PrintableMessage {
    private String string = "The card you selected is not a production card, please try again";

    public String getString() {
        return string;
    }

    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(!isGui) System.out.println(string);
    }
}
