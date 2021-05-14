package it.polimi.ingsw.Communication.client;

import it.polimi.ingsw.Communication.server.messages.*;

/**
 * the ActionHandler handles the messages coming from the Server
 */
public class MessageHandler {
    ClientSideSocket clientSideSocket;

    public MessageHandler(ClientSideSocket clientSideSocket) {
        this.clientSideSocket = clientSideSocket;
    }

    /**
     * this method prints to screen the dashboard
     */
    public void handle(Message message){
        System.out.println("we're handling the server message!");
        if(message instanceof CreateMatchAckMessage){
            CreateMatchAckMessage createMatchAckMessage = (CreateMatchAckMessage) message;
            clientSideSocket.setGameID(createMatchAckMessage.getGameID());
            System.out.println(createMatchAckMessage.getMessage());
        }
        else if(message instanceof AddedToGameMessage){
            AddedToGameMessage addedToGameMessage = (AddedToGameMessage) message;
            System.out.println(addedToGameMessage.getMessage());
        }
        else if (message instanceof DashboardMessage){
            System.out.println("it is a dashboard message!");
        }
        else if(message instanceof JoinMatchErrorMessage){
            System.out.println("No game found, please try later");
        }
        else if(message instanceof JoinMatchAckMessage){
            clientSideSocket.setGameID(((JoinMatchAckMessage) message).getGameID());
            System.out.println("You joined match n."+((JoinMatchAckMessage) message).getGameID());
        }
    }
}
