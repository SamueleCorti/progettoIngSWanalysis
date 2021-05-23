package it.polimi.ingsw.communication.server.messages.notifications;

import it.polimi.ingsw.model.developmentcard.Color;

public class DevelopmentNotification implements Notification{
    private final int index;
    private final int level;
    private final Color color;
    private final String nickname;

    public DevelopmentNotification(int index, int level, Color color, String nickname) {
        this.index = index;
        this.level = level;
        this.color = color;
        this.nickname=nickname;
    }

    public int getIndex() {
        return index;
    }

    public String getNickname() {
        return nickname;
    }

    public int getLevel() {
        return level;
    }

    public Color getColor() {
        return color;
    }
}
