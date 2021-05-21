package it.polimi.ingsw.communication.server.messages.connectionRelatedMessages;

import it.polimi.ingsw.communication.server.messages.Message;

public class RejoinAckMessage implements Message {
    private final int gamePhase;

    public RejoinAckMessage(int gamePhase) {
        this.gamePhase = gamePhase;
    }

    public int getGamePhase() {
        return gamePhase;
    }
}
