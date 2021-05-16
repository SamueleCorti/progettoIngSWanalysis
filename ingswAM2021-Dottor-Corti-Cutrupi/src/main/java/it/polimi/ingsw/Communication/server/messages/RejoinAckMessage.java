package it.polimi.ingsw.Communication.server.messages;

public class RejoinAckMessage implements Message{
    private final int gamePhase;

    public RejoinAckMessage(int gamePhase) {
        this.gamePhase = gamePhase;
    }

    public int getGamePhase() {
        return gamePhase;
    }
}
