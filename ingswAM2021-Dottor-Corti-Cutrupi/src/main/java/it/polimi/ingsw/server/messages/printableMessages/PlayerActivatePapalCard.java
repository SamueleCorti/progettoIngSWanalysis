package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;

/**
 * Self explanatory name
 */
public class PlayerActivatePapalCard implements PrintableMessage {
    private String string;

    public PlayerActivatePapalCard(String nickname, int index) {
        string= nickname + " has just activated the papal card number " + index;
    }

    public String getString() {
        return string;
    }

    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(!isGui) System.out.println(string);
    }
}
