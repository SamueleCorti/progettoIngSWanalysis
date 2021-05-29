package it.polimi.ingsw.server.messages.connectionRelatedMessages;

import it.polimi.ingsw.server.messages.Message;

public class RejoinAckMessage implements Message {
    private final int gamePhase;
    private final int size;

    public RejoinAckMessage(int gamePhase, int size) {
        this.gamePhase = gamePhase;
        this.size = size;
    }

    public int getGamePhase() {
        return gamePhase;
    }

    public int getSize() {
        return size;
    }
}
