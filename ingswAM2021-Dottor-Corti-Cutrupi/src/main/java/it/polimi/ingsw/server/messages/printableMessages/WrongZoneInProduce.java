package it.polimi.ingsw.server.messages.printableMessages;

public class WrongZoneInProduce implements PrintableMessage {
    String string = "There is no card activable in the selected dev zone";

    public String getString() {
        return string;
    }
}
