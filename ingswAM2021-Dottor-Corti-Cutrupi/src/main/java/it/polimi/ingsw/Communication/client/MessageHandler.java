package it.polimi.ingsw.Communication.client;

import it.polimi.ingsw.Communication.client.actions.InitializationActions.NotInInitializationAnymoreAction;
import it.polimi.ingsw.Communication.client.actions.MatchManagementActions.NotInLobbyAnymore;
import it.polimi.ingsw.Communication.server.messages.*;
import it.polimi.ingsw.Communication.server.messages.ConnectionRelatedMessages.DisconnectionMessage;
import it.polimi.ingsw.Communication.server.messages.ConnectionRelatedMessages.RejoinAckMessage;
import it.polimi.ingsw.Communication.server.messages.GameCreationPhaseMessages.*;
import it.polimi.ingsw.Communication.server.messages.GameplayMessages.WhiteToColorMessage;
import it.polimi.ingsw.Communication.server.messages.InitializationMessages.GameInitializationFinishedMessage;
import it.polimi.ingsw.Communication.server.messages.InitializationMessages.InitializationMessage;
import it.polimi.ingsw.Communication.server.messages.JsonMessages.DashboardMessage;
import it.polimi.ingsw.Communication.server.messages.JsonMessages.DevelopmentCardMessage;
import it.polimi.ingsw.Communication.server.messages.JsonMessages.GameBoardMessage;
import it.polimi.ingsw.Communication.server.messages.JsonMessages.LorenzoIlMagnificoMessage;
import it.polimi.ingsw.Communication.server.messages.Notificatios.Notification;
import it.polimi.ingsw.Communication.server.messages.rejoinErrors.RejoinErrorMessage;

/**
 * the ActionHandler handles the messages coming from the Server
 */
public class MessageHandler implements Runnable{
    ClientSideSocket clientSideSocket;
    Message message;
    public MessageHandler(ClientSideSocket clientSideSocket,Message messageToHandle) {
        this.clientSideSocket = clientSideSocket;
        this.message = messageToHandle;
    }


    /**
     * Method used to handle the message based on the type of the message received
     */
    public void run(){
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
            System.out.println("the dashboard is"+((DashboardMessage) message).getJsonDashboard());
        }
        else if(message instanceof GameBoardMessage){
            System.out.println("it is a gameboard message!");
            System.out.println(((GameBoardMessage) message).getJsonGameboard());
        }
        else if(message instanceof LorenzoIlMagnificoMessage){
            System.out.println("Lorenzo il Magnifico:");
            System.out.println(((LorenzoIlMagnificoMessage) message).getLorenzoJson());
        }
        else if(message instanceof DevelopmentCardMessage){
            System.out.println("the development card is");
            System.out.println(((DevelopmentCardMessage) message).getLeaderCardJson());
        }
        else if(message instanceof JoinMatchErrorMessage){
            System.out.println("No game found, please try later");
            clientSideSocket.createOrJoinMatchChoice();
        }
        else if(message instanceof JoinMatchNameAlreadyTakenError){
            System.out.println("The nickname you selected is already used in the game we tried to connect you to. Please" +
                    " try with another nickname");
            clientSideSocket.createOrJoinMatchChoice();
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
        else if(message instanceof GameInitializationFinishedMessage){
            System.out.println("All the players have initialized their boards, game is now ready to effectively begin");
            clientSideSocket.send(new NotInInitializationAnymoreAction());
        }
        else if(message instanceof RejoinAckMessage){
            System.out.println("You have been correctly reconnected to the game");
            switch (((RejoinAckMessage) message).getGamePhase()){
                case 0:
                    System.out.println("You are still in lobby so you simply have to wait for the room to full");
                    break;
                case 1:
                    System.out.println("You were in initialization phase: you have to finish it");
            }
        }
        else if(message instanceof InitializationMessage){
            clientSideSocket.initialize(((InitializationMessage) message).getOrder(),((InitializationMessage) message).getLeaderCardsPickedJson());
        }
        else if(message instanceof WhiteToColorMessage){
            clientSideSocket.whiteToColorChoices(((WhiteToColorMessage) message).getNumOfBlnks(),((WhiteToColorMessage) message).getType1(),((WhiteToColorMessage) message).getType2());
        }
        else if(message instanceof Notification)    clientSideSocket.manageNotification(message);
    }
}
