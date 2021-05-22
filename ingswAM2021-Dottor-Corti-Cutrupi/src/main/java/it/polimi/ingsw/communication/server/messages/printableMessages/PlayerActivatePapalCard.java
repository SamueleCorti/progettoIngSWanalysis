package it.polimi.ingsw.communication.server.messages.printableMessages;

import it.polimi.ingsw.communication.server.messages.Message;

public class PlayerActivatePapalCard implements PrintableMessage {
    String string;

    public PlayerActivatePapalCard(String nickname, int index) {
        string= nickname + " has just activated the papal card number " + index;
    }

    public String getString() {
        return string;
    }
}
