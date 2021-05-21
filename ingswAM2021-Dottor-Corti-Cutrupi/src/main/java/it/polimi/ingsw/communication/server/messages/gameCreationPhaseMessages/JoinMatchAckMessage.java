package it.polimi.ingsw.communication.server.messages.gameCreationPhaseMessages;

import it.polimi.ingsw.communication.server.messages.Message;

public class JoinMatchAckMessage implements Message {
    private final int gameID;

    public JoinMatchAckMessage(int gameID) {
        this.gameID = gameID;
    }

    public int getGameID() {
        return gameID;
    }
}
