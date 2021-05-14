package it.polimi.ingsw.Communication.server.messages;

public class CreateMatchAckMessage implements Message{
    private int gameID;
    private String message;
    private int size;

    public CreateMatchAckMessage(int gameID, int size) {
        this.gameID = gameID;
        message = "New match created, ID = "+ gameID + ".\nNumber of players = "+size;
        this.size = size;
    }
}
