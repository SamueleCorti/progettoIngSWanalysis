package it.polimi.ingsw.communication.server.messages.printableMessages;

import it.polimi.ingsw.communication.server.messages.Message;

public class WrongZoneInBuyMessage implements PrintableMessage {
    String string = "You cant put a card of that level in that developmentCardZone";

    public String getString() {
        return string;
    }
}
