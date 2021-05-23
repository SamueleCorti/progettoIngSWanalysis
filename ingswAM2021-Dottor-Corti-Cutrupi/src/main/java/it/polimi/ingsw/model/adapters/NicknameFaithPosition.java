package it.polimi.ingsw.model.adapters;

public class NicknameFaithPosition {
    private String nickname;
    private int papalPos;

    public NicknameFaithPosition(String nickname, int papalPos) {
        this.nickname = nickname;
        this.papalPos = papalPos;
    }

    public String getNickname() {
        return nickname;
    }

    public int getFaithPosition() {
        return papalPos;
    }
}
