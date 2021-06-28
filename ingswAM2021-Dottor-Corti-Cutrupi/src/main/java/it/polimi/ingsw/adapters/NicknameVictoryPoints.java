package it.polimi.ingsw.adapters;

/**
 * Links nickname and victory points for each player
 */
public class NicknameVictoryPoints {
    private String nickname;
    private int victoryPoints;

    /**
     * Class used to link players' nickname and victory points
     */
    public NicknameVictoryPoints(String nickname, int victoryPoints) {
        this.nickname = nickname;
        this.victoryPoints = victoryPoints;
    }

    public String getNickname() {
        return nickname;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }
}
