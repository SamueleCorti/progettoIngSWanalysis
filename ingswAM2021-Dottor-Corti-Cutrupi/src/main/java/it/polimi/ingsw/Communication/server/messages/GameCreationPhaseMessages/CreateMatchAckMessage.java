package it.polimi.ingsw.Communication.server.messages.GameCreationPhaseMessages;

import it.polimi.ingsw.Communication.server.messages.Message;

public class CreateMatchAckMessage implements Message {
    private final int gameID;
    private final String message;
    private final int size;

    public CreateMatchAckMessage(int gameID, int size) {
        this.gameID = gameID;
        message = "New match created, ID = "+ gameID + ".\nNumber of players = "+size;
        this.size = size;
    }

    public int getGameID() {
        return gameID;
    }

    public String getMessage() {
        return message;
    }

    public int getSize() {
        return size;
    }
}
