package it.polimi.ingsw.communication.server.messages.printableMessages;

import it.polimi.ingsw.communication.server.messages.Message;

public class BlackCrossTokenMessage implements PrintableMessage {
    String string;

    public BlackCrossTokenMessage(int faithPosition) {
        string = "Lorenzo drew a BlackCrossToken: now he is at position "+faithPosition+" and his token deck has been shuffled";
    }

    @Override
    public String getString() {
        return string;
    }
}
