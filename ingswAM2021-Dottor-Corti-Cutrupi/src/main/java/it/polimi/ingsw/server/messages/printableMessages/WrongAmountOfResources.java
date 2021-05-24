package it.polimi.ingsw.server.messages.printableMessages;

public class WrongAmountOfResources implements PrintableMessage {
    String string;

    public WrongAmountOfResources(int i) {
        this.string = "Wrong number of resources wanted inserted; that leader card needs "+i+"resources wanted";
    }

    public String getString() {
        return string;
    }
}
