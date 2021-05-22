package it.polimi.ingsw.communication.server.messages.printableMessages;

import it.polimi.ingsw.communication.server.messages.Message;

public class NotEnoughResourcesToBuy implements PrintableMessage {
    String string="You dont have enough resources to buy the card";

    public String getString() {
        return string;
    }
}
