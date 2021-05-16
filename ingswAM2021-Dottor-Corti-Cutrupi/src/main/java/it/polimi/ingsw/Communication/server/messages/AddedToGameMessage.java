package it.polimi.ingsw.Communication.server.messages;

public class AddedToGameMessage implements Message{
    private String message=", you have been successfully added to the match";

    public AddedToGameMessage(String nickname, boolean isHost) {
        message= nickname+message;
        if(isHost) message+= " and set as host!";
    }

    public String getMessage() {
        return message;
    }
}
