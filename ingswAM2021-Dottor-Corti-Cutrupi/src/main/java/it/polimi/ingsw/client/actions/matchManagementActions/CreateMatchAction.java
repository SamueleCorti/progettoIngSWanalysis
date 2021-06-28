package it.polimi.ingsw.client.actions.matchManagementActions;

import it.polimi.ingsw.client.actions.Action;


/**
 * Used when the first player connects and chooses to create the match
 */
public class CreateMatchAction implements Action {
    private final int gameSize;
    private final String nickname;
    private final String jsonSettings;

    /**
     * @param gameSize how many players will play in this game
     * @param nickname nick of the creator of the game
     * @param jsonSettings: string representing the settings
     */
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
