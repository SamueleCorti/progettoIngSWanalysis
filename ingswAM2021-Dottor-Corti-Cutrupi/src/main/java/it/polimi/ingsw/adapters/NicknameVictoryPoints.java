package it.polimi.ingsw.adapters;

public class NicknameVictoryPoints {
    private String nickname;
    private int victoryPoints;

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
