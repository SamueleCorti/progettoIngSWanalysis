package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.actions.initializationActions.NotInInitializationAnymoreAction;
import it.polimi.ingsw.client.actions.matchManagementActions.NotInLobbyAnymore;
import it.polimi.ingsw.client.actions.secondaryActions.ViewDashboardAction;
import it.polimi.ingsw.client.actions.secondaryActions.ViewGameboardAction;
import it.polimi.ingsw.client.shared.ClientSideSocket;
import it.polimi.ingsw.server.messages.LorenzoWonMessage;
import it.polimi.ingsw.server.messages.Message;
import it.polimi.ingsw.server.messages.PlayerWonSinglePlayerMatch;
import it.polimi.ingsw.server.messages.connectionRelatedMessages.DisconnectionMessage;
import it.polimi.ingsw.server.messages.connectionRelatedMessages.RejoinAckMessage;
import it.polimi.ingsw.server.messages.gameCreationPhaseMessages.*;
import it.polimi.ingsw.server.messages.gameplayMessages.AvailableResourcesForDevMessage;
import it.polimi.ingsw.server.messages.gameplayMessages.ResultsMessage;
import it.polimi.ingsw.server.messages.gameplayMessages.ViewGameboardMessage;
import it.polimi.ingsw.server.messages.gameplayMessages.WhiteToColorMessage;
import it.polimi.ingsw.server.messages.initializationMessages.*;
import it.polimi.ingsw.server.messages.jsonMessages.*;
import it.polimi.ingsw.server.messages.notifications.Notification;
import it.polimi.ingsw.server.messages.printableMessages.*;
import it.polimi.ingsw.server.messages.rejoinErrors.RejoinErrorMessage;
import javafx.application.Platform;

import java.util.concurrent.TimeUnit;

/**
 * the ActionHandler handles the messages coming from the Server
 */
public class MessageHandlerForGUI implements Runnable{
    private ClientSideSocket guiSideSocket;
    private Message message;
    private Boolean isGui;



    public MessageHandlerForGUI(ClientSideSocket guiSideSocket, Message messageToHandle,Boolean isGui) {
        this.guiSideSocket = guiSideSocket;
        this.message = messageToHandle;
        this.isGui = isGui;
    }

    /**
     * Method used to handle the message based on the type of the message received
     */
    public void run(){
        if(message instanceof CreateMatchAckMessage){
            CreateMatchAckMessage createMatchAckMessage = (CreateMatchAckMessage) message;
            guiSideSocket.setGameID(createMatchAckMessage.getGameID());
            guiSideSocket.setSizeOfLobby(createMatchAckMessage.getSize());
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
        else if(message instanceof DevelopmentCardMessage){
            guiSideSocket.refreshGameboard((DevelopmentCardMessage) message);
        }
        else if(message instanceof DevelopmentCardsInDashboard){
            for(DevelopmentCardMessage developmentCardMessage: ((DevelopmentCardsInDashboard) message).getMessages()){
                if(guiSideSocket.checkShowingOtherPlayerDashboard()){
                    //todo: add the received card to anotherPlayerDashboard
                    guiSideSocket.addCardToAnotherPlayerDevCardZone((DevelopmentCardsInDashboard) message);
                }else{
                    //todo: add the received card to your dashboard
                    guiSideSocket.addCardToYourDevCardZone((DevelopmentCardsInDashboard) message);
                }
            }
        }
        else if(message instanceof ShowingDashboardMessage){
            System.out.println("we set to true the showing other player dash");
            guiSideSocket.setTrueShowingOtherPlayerDashboard();
            guiSideSocket.resetAnotherPlayerDashboard();
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    guiSideSocket.changeStage("anotherPlayerDashboard.fxml");
                }
            });
        }
        else if(message instanceof MultipleLeaderCardsMessage) {
            guiSideSocket.addCardToLeaderTables((MultipleLeaderCardsMessage) message);
        }
        else if(message instanceof JoinMatchErrorMessage){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    guiSideSocket.addErrorAlert("No game found","0 games found, please try later.");
                }
            });
        }
        else if(message instanceof JoinMatchNameAlreadyTakenError){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    guiSideSocket.addErrorAlert("Nickname already used","The nickname you selected is already used " +
                            "in the game we tried to connect you to. Please try with another nickname.");
                }
            });

        }
        else if(message instanceof JoinMatchAckMessage){
            guiSideSocket.setGameID(((JoinMatchAckMessage) message).getGameID());
            guiSideSocket.setSizeOfLobby(((JoinMatchAckMessage) message).getSize());
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
            guiSideSocket.setGameStarted();
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.MILLISECONDS.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    guiSideSocket.changeStage("dashboard.fxml");
                    guiSideSocket.setupChoiceBoxAndNickname();
                }
            });
            guiSideSocket.send(new ViewDashboardAction());
            guiSideSocket.send(new ViewGameboardAction());
            //TODO: REMOVE THE NEXT LINE
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
                            guiSideSocket.addOkAlert("Rejoined successfully!","You are still in lobby" +
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
                    guiSideSocket.addErrorAlert("A player connected to the game!",slotsLeft.getString());
                }
            });
        }
        else if(message instanceof ResultsMessage){
            guiSideSocket.updateResultPage((ResultsMessage) message);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    guiSideSocket.changeStage("endGamePage.fxml");
                }
            });
            guiSideSocket.close();
        }
        else if(message instanceof NextTurnMessage){
            guiSideSocket.resetBaseProd();

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    guiSideSocket.addOkAlert("Turn changed", ((NextTurnMessage) message).getString());
                }
            });
        }
        else if(message instanceof OrderMessage){
            guiSideSocket.addPlayersNicknamesAndOrder(((OrderMessage) message).getPlayersNicknamesInOrder());
        }
        else if(message instanceof InitializationMessage){
            guiSideSocket.initializeForGUI(((InitializationMessage) message).getOrder(),((InitializationMessage) message).getLeaderCardsKept(),((InitializationMessage) message).getLeaderCardsGiven());
        }
        else if(message instanceof WhiteToColorMessage){
            ((WhiteToColorMessage) message).execute(guiSideSocket);
        }
        else if(message instanceof Notification)    guiSideSocket.manageNotification(message);
        else if(message instanceof LorenzoWonMessage) {
            ((LorenzoWonMessage) message).execute(guiSideSocket,isGui);
            guiSideSocket.LorenzoWon();
        }
        else if(message instanceof PlayerWonSinglePlayerMatch) {
            ((PlayerWonSinglePlayerMatch) message).execute(guiSideSocket);
        }
        else if(message instanceof LorenzoActivatedPapalCardAndYouDidnt){
            ((LorenzoActivatedPapalCardAndYouDidnt) message).execute(guiSideSocket);
        }
        else if(message instanceof LorenzoActivatedpapalCardAndYouToo){
            ((LorenzoActivatedpapalCardAndYouToo) message).execute(guiSideSocket);
        }
        else if(message instanceof PapalPathMessage)    {
            if(!guiSideSocket.checkShowingOtherPlayerDashboard())   guiSideSocket.printPapalPath((PapalPathMessage) message);
            else                                                    guiSideSocket.refreshPapalPath((PapalPathMessage) message);
        }
        else if(message instanceof NotEnoughResourcesToProduce){
            ((NotEnoughResourcesToProduce) message).execute(guiSideSocket);
        }
        else if(message instanceof MainActionAlreadyDoneMessage){
            ((MainActionAlreadyDoneMessage) message).execute(guiSideSocket);
        }
        else if(message instanceof YouMustDeleteADepot){
            ((YouMustDeleteADepot) message).execute(guiSideSocket);
        }
        else if(message instanceof WrongAmountOfResources){
            ((WrongAmountOfResources) message).execute(guiSideSocket);
        }
        else if(message instanceof ProductionAck){
            ((ProductionAck) message).execute(guiSideSocket);
        }
        else if(message instanceof CardIsNotInactive){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    guiSideSocket.addErrorAlert("You can't discard the selected card!","You already activated this card " +
                            "so you can't discard it!");
                }
            });
        }
        else if(message instanceof LastRoundOfMatch){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    guiSideSocket.addErrorAlert("Someone has fulfilled the conditions to end the game",
                            "The last round of turns will finish then we'll see who is the winner!");
                }
            });
        }
        else if(message instanceof WrongZoneInBuyMessage){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    guiSideSocket.addErrorAlert("You can't do that!",
                            "You cant put a card of that level in that developmentCardZone");
                }
            });
        }
        else if(message instanceof DiscardTokenMessage){
            ((DiscardTokenMessage) message).execute(guiSideSocket);
        }
        else if(message instanceof NotNewResources){
            ((NotNewResources) message).execute(guiSideSocket);
        }
        else if(message instanceof DiscardOKDepotOK){
            ((DiscardOKDepotOK) message).execute(guiSideSocket);
        }
        else if(message instanceof DoubleBlackCrossTokenMessage){
            ((DoubleBlackCrossTokenMessage) message).execute(guiSideSocket);
        }
        else if(message instanceof IncorrectPhaseMessage){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    guiSideSocket.addErrorAlert("Incorrect phase!","There is a time and a place for everything, " +
                            "but not now, Ash!");
                }
            });
        }
        else if(message instanceof YouMustDiscardResources){
            ((YouMustDiscardResources) message).execute(guiSideSocket);
        }
        else if(message instanceof BlackCrossTokenMessage){
            ((BlackCrossTokenMessage) message).execute(guiSideSocket, isGui);
        }
        else if(message instanceof ActivatedLeaderCardAck){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    guiSideSocket.activateCardGivenItsIndex(((ActivatedLeaderCardAck) message).getIndex());
                    guiSideSocket.activateIfDepot((ActivatedLeaderCardAck) message);
                }
            });
        }
        else if(message instanceof ProductionAlreadyActivatedInThisTurn){
            ((ProductionAlreadyActivatedInThisTurn) message).execute(guiSideSocket);
        }
        else if(message instanceof NotEnoughRequirementsToActivate){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    guiSideSocket.addErrorAlert("Error!","You dont have the requirements to activate this leader card");
                }
            });
        }
        else if(message instanceof YouMustDoAMainActionFirst){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    guiSideSocket.addErrorAlert("Unable to end turn", ((YouMustDoAMainActionFirst) message).getString());
                }
            });
        }
        else if(message instanceof YouActivatedPapalCard)   guiSideSocket.activatePapalCard(((YouActivatedPapalCard) message).getIndex());
        else if(message instanceof YouActivatedPapalCardToo)   guiSideSocket.activatePapalCard(((YouActivatedPapalCardToo) message).getIndex());
        else if(message instanceof YouDidntActivatePapalCard)   guiSideSocket.discardPapalCard(((YouDidntActivatePapalCard) message).getIndex());
        else if(message instanceof MarketMessage)   guiSideSocket.refreshMarket((MarketMessage) message);
        else if(message instanceof DepotMessage)    {
            System.out.println("we received a depot message");
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                if(!guiSideSocket.checkShowingOtherPlayerDashboard()){
                    guiSideSocket.refreshYourDepot((DepotMessage) message);
                }else if(guiSideSocket.checkShowingOtherPlayerDashboard()){
                    guiSideSocket.refreshAnotherPlayerDepot((DepotMessage) message);
                }
                }
            });
        }
        else if(message instanceof ExceedingDepotMessage){
            ((ExceedingDepotMessage) message).execute(guiSideSocket);
        }
        else if(message instanceof StrongboxMessage)    {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    guiSideSocket.refreshStrongbox((StrongboxMessage) message);
                }
            });

        }
        else if(message instanceof ViewGameboardMessage)    {
            guiSideSocket.refreshGameboard((ViewGameboardMessage) message);
        }
        else if(message instanceof NotYourTurnMessage){

        }
        else if(message instanceof PrintableMessage){
            System.out.println(((PrintableMessage) message).getString());
        }
        else if(message instanceof BaseProdParametersMessage)   {
            guiSideSocket.setBaseProd((BaseProdParametersMessage) message);
        }
        else if(message instanceof AvailableResourcesForDevMessage){
            guiSideSocket.refreshResourcesForDevelopment(((AvailableResourcesForDevMessage) message).getResources());
        }
    }
}
