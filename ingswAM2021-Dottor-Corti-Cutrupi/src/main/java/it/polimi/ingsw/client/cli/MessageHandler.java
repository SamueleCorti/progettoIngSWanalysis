package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.actions.initializationActions.NotInInitializationAnymoreAction;
import it.polimi.ingsw.client.actions.matchManagementActions.NotInLobbyAnymore;
import it.polimi.ingsw.server.messages.LorenzoWonMessage;
import it.polimi.ingsw.server.messages.Message;
import it.polimi.ingsw.server.messages.PlayerWonSinglePlayerMatch;
import it.polimi.ingsw.server.messages.connectionRelatedMessages.DisconnectionMessage;
import it.polimi.ingsw.server.messages.connectionRelatedMessages.RejoinAckMessage;
import it.polimi.ingsw.server.messages.gameCreationPhaseMessages.*;
import it.polimi.ingsw.server.messages.gameplayMessages.ResultsMessage;
import it.polimi.ingsw.server.messages.gameplayMessages.WhiteToColorMessage;
import it.polimi.ingsw.server.messages.initializationMessages.GameInitializationFinishedMessage;
import it.polimi.ingsw.server.messages.initializationMessages.InitializationMessage;
import it.polimi.ingsw.server.messages.initializationMessages.OrderMessage;
import it.polimi.ingsw.server.messages.jsonMessages.DashboardMessage;
import it.polimi.ingsw.server.messages.jsonMessages.DevelopmentCardMessage;
import it.polimi.ingsw.server.messages.jsonMessages.GameBoardMessage;
import it.polimi.ingsw.server.messages.jsonMessages.LorenzoIlMagnificoMessage;
import it.polimi.ingsw.server.messages.notifications.Notification;
import it.polimi.ingsw.server.messages.printableMessages.PrintableMessage;
import it.polimi.ingsw.server.messages.rejoinErrors.RejoinErrorMessage;

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
        if(message instanceof PrintableMessage){
            System.out.println(((PrintableMessage) message).getString());
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
           // System.out.println(((DevelopmentCardMessage) message).getLeaderCardJson());
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
            clientSideSocket.loopRequest();
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
        else if(message instanceof ResultsMessage){
            System.out.println(((ResultsMessage) message).getResult());
            clientSideSocket.close();
        }
        else if(message instanceof OrderMessage){
            System.out.println(((OrderMessage) message).getPlayerOrder());
        }
        else if(message instanceof InitializationMessage){
            clientSideSocket.initialize(((InitializationMessage) message).getOrder(),((InitializationMessage) message).getLeaderCardsPickedJson(),((InitializationMessage) message).getLeaderCardsKept(),((InitializationMessage) message).getLeaderCardsGiven());
        }
        else if(message instanceof WhiteToColorMessage){
            clientSideSocket.whiteToColorChoices(((WhiteToColorMessage) message).getNumOfBlnks());
        }
        else if(message instanceof Notification)    clientSideSocket.manageNotification(message);
        else if(message instanceof LorenzoWonMessage) clientSideSocket.LorenzoWon();
        else if(message instanceof PlayerWonSinglePlayerMatch) clientSideSocket.playerWonSinglePlayerMatch((PlayerWonSinglePlayerMatch) message);
    }
}
