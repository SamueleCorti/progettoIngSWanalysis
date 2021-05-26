package it.polimi.ingsw.server.messages.printableMessages;

public class SlotsLeft implements PrintableMessage {
    private String string;
    private int num;

    public SlotsLeft(int num) {
        this.num = num;
        this.string = num +" slots left";
    }

    public String getString() {
        return string;
    }

    public int getNum() {
        return num;
    }
}
