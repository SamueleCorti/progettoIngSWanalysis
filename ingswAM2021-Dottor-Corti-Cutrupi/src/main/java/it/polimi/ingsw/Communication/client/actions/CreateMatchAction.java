package it.polimi.ingsw.Communication.client.actions;

public class CreateMatchAction implements Action {
    private int gameSize;
    private String nickname;
    private String jsonSettings;

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
