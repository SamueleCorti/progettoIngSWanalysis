package it.polimi.ingsw.Communication.server.messages;

public class DiscardDepotMessage {
    private final int index;

    public DiscardDepotMessage(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
