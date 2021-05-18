package it.polimi.ingsw.Communication.client.actions.MatchManagementActions;

import it.polimi.ingsw.Communication.client.actions.Action;

public class JoinMatchAction implements Action {
    private final String nickname;

    public JoinMatchAction(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
