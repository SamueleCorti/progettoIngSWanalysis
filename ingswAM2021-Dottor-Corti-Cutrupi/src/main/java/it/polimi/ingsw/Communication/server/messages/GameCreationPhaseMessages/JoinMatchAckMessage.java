package it.polimi.ingsw.Communication.server.messages.GameCreationPhaseMessages;

import it.polimi.ingsw.Communication.server.messages.Message;

public class JoinMatchAckMessage implements Message {
    private final int gameID;

    public JoinMatchAckMessage(int gameID) {
        this.gameID = gameID;
    }

    public int getGameID() {
        return gameID;
    }
}