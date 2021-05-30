package it.polimi.ingsw.server.messages.printableMessages;

public class ActivatedLeaderCardAck implements PrintableMessage {
    private String string = "Leader card activated correctly!";
    private int index;

    public ActivatedLeaderCardAck(int index) {
        this.index = index;
    }

    public String getString() {
        return string;
    }

    public int getIndex() {
        return index;
    }
}
