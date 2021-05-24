package it.polimi.ingsw.server.messages.printableMessages;

public class SlotsLeft implements PrintableMessage {
    String string;

    public SlotsLeft(int num) {
        this.string = num +" slots left";
    }

    public String getString() {
        return string;
    }
}
