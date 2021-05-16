package it.polimi.ingsw.Communication.client.actions;

public class JoinMatchAction implements Action{
    private final String nickname;

    public JoinMatchAction(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
