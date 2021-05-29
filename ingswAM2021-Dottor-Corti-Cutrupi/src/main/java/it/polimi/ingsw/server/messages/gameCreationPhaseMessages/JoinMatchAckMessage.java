package it.polimi.ingsw.server.messages.gameCreationPhaseMessages;

import it.polimi.ingsw.server.messages.Message;

public class JoinMatchAckMessage implements Message {
    private final int gameID;
    private final int size;

    public JoinMatchAckMessage(int gameID, int sizeOfLobby) {
        this.gameID = gameID;
        this.size = sizeOfLobby;
    }

    public int getGameID() {
        return gameID;
    }

    public int getSize() {
        return size;
    }
}
