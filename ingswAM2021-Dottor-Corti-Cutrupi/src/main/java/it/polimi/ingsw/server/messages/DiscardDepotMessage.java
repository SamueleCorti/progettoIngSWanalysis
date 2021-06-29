package it.polimi.ingsw.server.messages;

/**
 * Self explanatory name
 */
public class DiscardDepotMessage {
    private final int index;

    public DiscardDepotMessage(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
