package it.polimi.ingsw.server.messages.notifications;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import it.polimi.ingsw.server.messages.Message;

/**
 * Self explanatory name
 */
public class MarketNotification implements Message{
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

    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(!isGui){
            String string="";
            MarketNotification notification= this;
            string="This turn "+notification.getNickname()+ " has decided to take resources from market, in particular he chose";
            if (notification.isRow()) string+=" row ";
            if (!notification.isRow()) string+=" column ";
            string+= "number "+ notification.getIndex()+ "\nHere is the new market: \n";
            System.out.println(string);
        }
    }

}
