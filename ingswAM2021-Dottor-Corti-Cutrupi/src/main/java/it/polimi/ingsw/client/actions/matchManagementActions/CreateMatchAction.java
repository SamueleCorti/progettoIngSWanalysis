package it.polimi.ingsw.client.actions.matchManagementActions;

import it.polimi.ingsw.client.actions.Action;


/**
 * Used when the first player connects and chooses to create the match
 */
public class CreateMatchAction implements Action {
    private final int gameSize;
    private final String nickname;
    private String devCardInstancingFA;
    private String favorCardsFA;
    private String leaderCardsInstancingFA;
    private String leaderCardsParametersFA;
    private String papalPathTilesFA;
    private String standardProdParameterFA;
    private boolean modifiedOptions;

    /**
     * @param gameSize how many players will play in this game
     * @param nickname nick of the creator of the game
     */
    public CreateMatchAction(int gameSize, String nickname) {
        this.gameSize = gameSize;
        this.nickname = nickname;
        this.modifiedOptions = false;
    }

    public CreateMatchAction(int gameSize, String nickname, String devCardInstancingFA, String favorCardsFA, String leaderCardsInstancingFA, String leaderCardsParametersFA,String standardProdParameterFA, String papalPathTilesFA) {
        this.gameSize = gameSize;
        this.nickname = nickname;
        this.devCardInstancingFA = devCardInstancingFA;
        this.favorCardsFA = favorCardsFA;
        this.leaderCardsInstancingFA = leaderCardsInstancingFA;
        this.leaderCardsParametersFA = leaderCardsParametersFA;
        this.standardProdParameterFA = standardProdParameterFA;
        this.papalPathTilesFA = papalPathTilesFA;
        this.modifiedOptions = true;
    }

    public int getGameSize() {
        return gameSize;
    }

    public String getNickname() {
        return nickname;
    }

    public String getDevCardInstancingFA() {
        return devCardInstancingFA;
    }

    public String getFavorCardsFA() {
        return favorCardsFA;
    }

    public String getLeaderCardsInstancingFA() {
        return leaderCardsInstancingFA;
    }

    public String getLeaderCardsParametersFA() {
        return leaderCardsParametersFA;
    }

    public String getPapalPathTilesFA() {
        return papalPathTilesFA;
    }

    public String getStandardProdParameterFA() {
        return standardProdParameterFA;
    }

    public boolean isModifiedOptions() {
        return modifiedOptions;
    }
}
