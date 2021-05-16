package it.polimi.ingsw.Communication.server.messages.ConnectionRelatedMessages;

import it.polimi.ingsw.Communication.server.messages.Message;

public class RejoinAckMessage implements Message {
    private final int gamePhase;

    public RejoinAckMessage(int gamePhase) {
        this.gamePhase = gamePhase;
    }

    public int getGamePhase() {
        return gamePhase;
    }
}
