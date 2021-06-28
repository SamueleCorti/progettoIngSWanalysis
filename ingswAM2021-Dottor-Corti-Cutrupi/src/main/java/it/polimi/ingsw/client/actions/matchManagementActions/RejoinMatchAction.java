package it.polimi.ingsw.client.actions.matchManagementActions;

import it.polimi.ingsw.client.actions.Action;

/**
 * Used when a player attempts to reconnect
 */
public class RejoinMatchAction implements Action {
    private final int gameID;
    private final String nickname;

    public int getGameID() {
        return gameID;
    }

    public String getNickname() {
        return nickname;
    }

    /**
     * @param gameID: num of the game
     * @param nickname: name used when connecting to the game for the first time
     */
    public RejoinMatchAction(int gameID, String nickname) {
        this.gameID = gameID;
        this.nickname = nickname;
    }
}
