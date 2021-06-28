package it.polimi.ingsw.adapters;

/**
 * Links nickname and papal position for each player
 */
public class NicknameFaithPosition {
    private String nickname;
    private int papalPos;

    /**
     * Class used to link players' nickname and faith position
     */
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
