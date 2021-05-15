package it.polimi.ingsw.Communication.client;

import it.polimi.ingsw.Communication.client.actions.NotInLobbyAnymore;
import it.polimi.ingsw.Communication.server.messages.*;
import it.polimi.ingsw.Communication.server.messages.rejoinErrors.RejoinErrorMessage;

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
        if(message instanceof GenericMessage){
            System.out.println(((GenericMessage) message).getString());
        }
        else if(message instanceof CreateMatchAckMessage){
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
            System.out.println("the dashoard is"+((DashboardMessage) message).getJsonDashboard());
        }
        else if(message instanceof JoinMatchErrorMessage){
            System.out.println("No game found, please try later");
        }
        else if(message instanceof JoinMatchAckMessage){
            clientSideSocket.setGameID(((JoinMatchAckMessage) message).getGameID());
            System.out.println("You joined match n."+((JoinMatchAckMessage) message).getGameID());
        }
        else if(message instanceof GameStartingMessage){
            System.out.println(((GameStartingMessage) message).getMessage());
            clientSideSocket.send(new NotInLobbyAnymore());
        }
        else if(message instanceof DisconnectionMessage){
            System.out.println(((DisconnectionMessage) message).getMessage());
        }
        else if(message instanceof RejoinErrorMessage){
            System.out.println(((RejoinErrorMessage) message).getString());
            clientSideSocket.createOrJoinMatchChoice();
        }
        else if(message instanceof RejoinAckMessage){
            System.out.println("You have been correctly reconnected to the game");
        }
        else if(message instanceof InitializationMessage){
            clientSideSocket.initialize(((InitializationMessage) message).getOrder());
        }
    }
}
