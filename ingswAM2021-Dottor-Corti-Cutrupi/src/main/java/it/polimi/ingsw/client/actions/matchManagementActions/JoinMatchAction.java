package it.polimi.ingsw.client.actions.matchManagementActions;

import it.polimi.ingsw.client.actions.Action;

public class JoinMatchAction implements Action {
    private final String nickname;

    public JoinMatchAction(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
