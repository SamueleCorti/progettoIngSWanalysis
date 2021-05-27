package it.polimi.ingsw.gui;

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
import it.polimi.ingsw.server.messages.initializationMessages.CardsToDiscardMessage;
import it.polimi.ingsw.server.messages.initializationMessages.GameInitializationFinishedMessage;
import it.polimi.ingsw.server.messages.initializationMessages.InitializationMessage;
import it.polimi.ingsw.server.messages.initializationMessages.OrderMessage;
import it.polimi.ingsw.server.messages.jsonMessages.*;
import it.polimi.ingsw.server.messages.notifications.Notification;
import it.polimi.ingsw.server.messages.printableMessages.PrintableMessage;
import it.polimi.ingsw.server.messages.printableMessages.SlotsLeft;
import it.polimi.ingsw.server.messages.rejoinErrors.RejoinErrorMessage;
import javafx.application.Platform;

/**
 * the ActionHandler handles the messages coming from the Server
 */
public class MessageHandlerForGUI implements Runnable{
    private GuiSideSocket guiSideSocket;
    private Message message;

    public MessageHandlerForGUI(GuiSideSocket guiSideSocket, Message messageToHandle) {
        this.guiSideSocket = guiSideSocket;
        this.message = messageToHandle;
    }

    /**
     * Method used to handle the message based on the type of the message received
     */
    public void run(){
        if(message instanceof CreateMatchAckMessage){
            CreateMatchAckMessage createMatchAckMessage = (CreateMatchAckMessage) message;
            guiSideSocket.setGameID(createMatchAckMessage.getGameID());
            System.out.println(createMatchAckMessage.getMessage());
        }
        else if(message instanceof AddedToGameMessage){
            AddedToGameMessage addedToGameMessage = (AddedToGameMessage) message;
            System.out.println(addedToGameMessage.getMessage());
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    guiSideSocket.changeStage("lobby.fxml");
                }
            });
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
        else if(message instanceof LeaderCardMessage){
           /* LeaderCardForGUI card = new LeaderCardForGUI((LeaderCardMessage) message);
            if(guiSideSocket.isStillInitializing()) {
                guiSideSocket.addCardToTable(card);
            }*/
        }
        else if(message instanceof JoinMatchErrorMessage){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    guiSideSocket.addAlert("No game found","0 games found, please try later.");
                }
            });
        }
        else if(message instanceof JoinMatchNameAlreadyTakenError){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    guiSideSocket.addAlert("Nickname already used","The nickname you selected is already used " +
                            "in the game we tried to connect you to. Please try with another nickname.");
                }
            });

        }
        else if(message instanceof JoinMatchAckMessage){
            guiSideSocket.setGameID(((JoinMatchAckMessage) message).getGameID());
            System.out.println("You joined match n."+((JoinMatchAckMessage) message).getGameID());
        }
        else if(message instanceof GameStartingMessage){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    guiSideSocket.changeStage("discardleadercards.fxml");
                }
            });
            guiSideSocket.send(new NotInLobbyAnymore());
        }
        else if(message instanceof DisconnectionMessage){
            System.out.println(((DisconnectionMessage) message).getMessage());
        }
        else if(message instanceof RejoinErrorMessage){
            System.out.println(((RejoinErrorMessage) message).getString());
            //clientSideSocket.createOrJoinMatchChoice();
        }
        else if(message instanceof GameInitializationFinishedMessage){
            System.out.println("All the players have initialized their boards, game is now ready to effectively begin");
            guiSideSocket.send(new NotInInitializationAnymoreAction());
            guiSideSocket.loopRequest();
        }
        else if(message instanceof RejoinAckMessage){
            System.out.println("You have been correctly reconnected to the game");
            switch (((RejoinAckMessage) message).getGamePhase()){
                case 0:
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            guiSideSocket.changeStage("lobby.fxml");
                            guiSideSocket.addAlert("Rejoined successfully!","You are still in lobby" +
                                    " so you simply have to wait for the room to full");
                        }
                    });
                    break;
                case 1:
                    System.out.println("You were in initialization phase: you have to finish it");
            }
        }
        else if(message instanceof SlotsLeft){
            SlotsLeft slotsLeft = (SlotsLeft) message;
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    guiSideSocket.addAlert("A player connected to the game!",slotsLeft.getString());
                }
            });
        }
        else if(message instanceof ResultsMessage){
            System.out.println(((ResultsMessage) message).getResult());
            guiSideSocket.close();
        }
        else if(message instanceof OrderMessage){
            System.out.println(((OrderMessage) message).getPlayerOrder());
        }
        else if(message instanceof InitializationMessage){
            guiSideSocket.initialize(((InitializationMessage) message).getOrder(),((InitializationMessage) message).getLeaderCardsKept(),((InitializationMessage) message).getLeaderCardsGiven());
        }
        else if(message instanceof WhiteToColorMessage){
            guiSideSocket.whiteToColorChoices(((WhiteToColorMessage) message).getNumOfBlnks());
        }
        else if(message instanceof Notification)    guiSideSocket.manageNotification(message);
        else if(message instanceof LorenzoWonMessage) guiSideSocket.LorenzoWon();
        else if(message instanceof PlayerWonSinglePlayerMatch) guiSideSocket.playerWonSinglePlayerMatch((PlayerWonSinglePlayerMatch) message);
        else if(message instanceof CardsToDiscardMessage) guiSideSocket.addCardToTable((CardsToDiscardMessage) message);
        else if(message instanceof PrintableMessage){
            System.out.println(((PrintableMessage) message).getString());
        }
    }
}
