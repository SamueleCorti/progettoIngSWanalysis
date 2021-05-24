package it.polimi.ingsw.server.messages.gameCreationPhaseMessages;

import it.polimi.ingsw.server.messages.Message;

public class JoinMatchAckMessage implements Message {
    private final int gameID;

    public JoinMatchAckMessage(int gameID) {
        this.gameID = gameID;
    }

    public int getGameID() {
        return gameID;
    }
}
