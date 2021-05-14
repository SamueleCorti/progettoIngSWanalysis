package it.polimi.ingsw.Communication.client;

import it.polimi.ingsw.Communication.server.messages.DashboardMessage;
import it.polimi.ingsw.Communication.server.messages.Message;

/**
 * the ActionHandler handles the messages coming from the Server
 */
public class MessageHandler {
    /**
     * this method prints to screen the dashboard
     */
    public void handle(Message message){
        System.out.println("we're handling the server message!");
        if (message instanceof DashboardMessage){
            System.out.println("it is a dashboard message!");
        }
    }
}
