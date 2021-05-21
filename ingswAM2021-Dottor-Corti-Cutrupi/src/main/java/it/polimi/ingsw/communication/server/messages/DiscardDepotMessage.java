package it.polimi.ingsw.communication.server.messages;

public class DiscardDepotMessage {
    private final int index;

    public DiscardDepotMessage(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
