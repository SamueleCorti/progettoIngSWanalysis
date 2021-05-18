package it.polimi.ingsw.Communication.client.actions.MatchManagementActions;

import it.polimi.ingsw.Communication.client.actions.Action;

public class CreateMatchAction implements Action {
    private final int gameSize;
    private final String nickname;
    private final String jsonSettings;

    public CreateMatchAction(int gameSize, String nickname, String jsonSettings) {
        this.gameSize = gameSize;
        this.nickname = nickname;
        this.jsonSettings = jsonSettings;
    }

    public int getGameSize() {
        return gameSize;
    }

    public String getNickname() {
        return nickname;
    }

    public String getJsonSettings() {
        return jsonSettings;
    }
}
