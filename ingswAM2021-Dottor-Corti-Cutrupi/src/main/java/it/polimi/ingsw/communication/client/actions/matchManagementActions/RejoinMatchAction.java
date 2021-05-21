package it.polimi.ingsw.communication.client.actions.matchManagementActions;

import it.polimi.ingsw.communication.client.actions.Action;

public class RejoinMatchAction implements Action {
    private final int gameID;
    private final String nickname;

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
