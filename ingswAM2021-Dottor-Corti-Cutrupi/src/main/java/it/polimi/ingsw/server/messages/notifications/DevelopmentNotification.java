package it.polimi.ingsw.server.messages.notifications;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import it.polimi.ingsw.model.developmentcard.Color;
import it.polimi.ingsw.server.messages.Message;

/**
 * Self explanatory name
 */
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

    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {

    }
}
