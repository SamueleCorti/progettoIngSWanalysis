package it.polimi.ingsw.Communication.server.messages.Notificatios;

import it.polimi.ingsw.Model.boardsAndPlayer.Player;

public class MarketNotification implements Notification{
    private final int index;
    private final String nickname;
    private final boolean isRow;

    public MarketNotification(int index, boolean isRow, String nickname) {
        this.index = index;
        this.isRow = isRow;
        this.nickname=nickname;
    }

    public int getIndex() {
        return index;
    }

    public String getNickname() {
        return nickname;
    }

    public boolean isRow() {
        return isRow;
    }
}
