package it.polimi.ingsw.server.messages.printableMessages;

public class WrongZoneInBuyMessage implements PrintableMessage {
    String string = "You cant put a card of that level in that developmentCardZone";

    public String getString() {
        return string;
    }
}
