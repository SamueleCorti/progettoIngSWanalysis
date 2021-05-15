package it.polimi.ingsw.Communication.client.actions;

public class RejoinMatchAction implements Action{
    private int gameID;
    private String nickname;

    public int getGameID() {
        return gameID;
    }

    public String getNickname() {
        return nickname;
    }

    public RejoinMatchAction(int gameID, String nickname) {
        this.gameID = gameID;
        this.nickname = nickname;
    }
}