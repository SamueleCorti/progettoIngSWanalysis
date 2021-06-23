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
import it.polimi.ingsw.server.messages.notifications.MarketNotification;
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
            message.execute(guiSideSocket,isGui);
        }
        else if(message instanceof AddedToGameMessage){
            message.execute(guiSideSocket,isGui);
        }
        else if(message instanceof DevelopmentCardMessage){
            message.execute(guiSideSocket,isGui);
        }
        else if(message instanceof DevelopmentCardsInDashboard){
            message.execute(guiSideSocket,isGui);
        }
        else if(message instanceof ShowingDashboardMessage){
            message.execute(guiSideSocket,isGui);
        }
        else if(message instanceof MultipleLeaderCardsMessage) {
            message.execute(guiSideSocket,isGui);
        }
        else if(message instanceof JoinMatchErrorMessage){
            message.execute(guiSideSocket,isGui);
        }
        else if(message instanceof JoinMatchNameAlreadyTakenError){
            message.execute(guiSideSocket,isGui);

        }
        else if(message instanceof JoinMatchAckMessage){
            message.execute(guiSideSocket,isGui);
        }
        else if(message instanceof GameStartingMessage){
            message.execute(guiSideSocket,isGui);
        }
        else if(message instanceof DisconnectionMessage){
            message.execute(guiSideSocket,isGui);
        }
        else if(message instanceof RejoinErrorMessage){
            message.execute(guiSideSocket,isGui);
        }
        else if(message instanceof GameInitializationFinishedMessage){
            message.execute(guiSideSocket,isGui);
        }
        else if(message instanceof RejoinAckMessage){
            message.execute(guiSideSocket,isGui);
        }
        else if(message instanceof SlotsLeft){
            message.execute(guiSideSocket,isGui);
        }
        else if(message instanceof ResultsMessage){
            message.execute(guiSideSocket,isGui);
        }
        else if(message instanceof NextTurnMessage){
            message.execute(guiSideSocket,isGui);
        }
        else if(message instanceof OrderMessage){
            message.execute(guiSideSocket,isGui);
        }
        else if(message instanceof InitializationMessage){
            message.execute(guiSideSocket,isGui);
        }
        else if(message instanceof WhiteToColorMessage){
            ((WhiteToColorMessage) message).execute(guiSideSocket,isGui);
        }
        else if(message instanceof LorenzoWonMessage) {
            ((LorenzoWonMessage) message).execute(guiSideSocket,isGui);
            guiSideSocket.LorenzoWon();
        }
        else if(message instanceof PlayerWonSinglePlayerMatch) {
            ((PlayerWonSinglePlayerMatch) message).execute(guiSideSocket,isGui);
        }
        else if(message instanceof LorenzoActivatedPapalCardAndYouDidnt){
            ((LorenzoActivatedPapalCardAndYouDidnt) message).execute(guiSideSocket, true);
        }
        else if(message instanceof LorenzoActivatedpapalCardAndYouToo){
            ((LorenzoActivatedpapalCardAndYouToo) message).execute(guiSideSocket, true);
        }
        else if(message instanceof PapalPathMessage)    {
            message.execute(guiSideSocket,isGui);
        }
        else if(message instanceof NotEnoughResourcesToProduce){
            ((NotEnoughResourcesToProduce) message).execute(guiSideSocket, true);
        }
        else if(message instanceof MainActionAlreadyDoneMessage){
            ((MainActionAlreadyDoneMessage) message).execute(guiSideSocket, true);
        }
        else if(message instanceof YouMustDeleteADepot){
            ((YouMustDeleteADepot) message).execute(guiSideSocket, true);
        }
        else if(message instanceof WrongAmountOfResources){
            ((WrongAmountOfResources) message).execute(guiSideSocket, true);
        }
        else if(message instanceof ProductionAck){
            ((ProductionAck) message).execute(guiSideSocket, true);
        }
        else if(message instanceof CardIsNotInactive){
            message.execute(guiSideSocket,isGui);
        }
        else if(message instanceof LastRoundOfMatch){
            message.execute(guiSideSocket,isGui);
        }
        else if(message instanceof WrongZoneInBuyMessage){
            message.execute(guiSideSocket,isGui);
        }
        else if(message instanceof DiscardTokenMessage){
            ((DiscardTokenMessage) message).execute(guiSideSocket, isGui);
        }
        else if(message instanceof NotNewResources){
            ((NotNewResources) message).execute(guiSideSocket, true);
        }
        else if(message instanceof DiscardOKDepotOK){
            ((DiscardOKDepotOK) message).execute(guiSideSocket, isGui);
        }
        else if(message instanceof DoubleBlackCrossTokenMessage){
            ((DoubleBlackCrossTokenMessage) message).execute(guiSideSocket, isGui);
        }
        else if(message instanceof IncorrectPhaseMessage){
            message.execute(guiSideSocket,isGui);
        }
        else if(message instanceof YouMustDiscardResources){
            ((YouMustDiscardResources) message).execute(guiSideSocket, true);
        }
        else if(message instanceof BlackCrossTokenMessage){
            ((BlackCrossTokenMessage) message).execute(guiSideSocket, isGui);
        }
        else if(message instanceof ActivatedLeaderCardAck){
            message.execute(guiSideSocket,isGui);
        }
        else if(message instanceof ProductionAlreadyActivatedInThisTurn){
            ((ProductionAlreadyActivatedInThisTurn) message).execute(guiSideSocket, true);
        }
        else if(message instanceof NotEnoughRequirementsToActivate){
            message.execute(guiSideSocket,isGui);
        }
        else if(message instanceof YouMustDoAMainActionFirst){
            message.execute(guiSideSocket,isGui);
        }
        else if(message instanceof YouActivatedPapalCard)   {
            message.execute(guiSideSocket,isGui);
        }
        else if(message instanceof YouActivatedPapalCardToo)   {
            message.execute(guiSideSocket,isGui);
        }
        else if(message instanceof YouDidntActivatePapalCard)   {
            message.execute(guiSideSocket,isGui);
        }
        else if(message instanceof MarketMessage)   {
            message.execute(guiSideSocket,isGui);
        }
        else if(message instanceof DepotMessage)    {
            message.execute(guiSideSocket,isGui);
        }
        else if(message instanceof ExceedingDepotMessage){
            ((ExceedingDepotMessage) message).execute(guiSideSocket,isGui);
        }
        else if(message instanceof StrongboxMessage)    {
            message.execute(guiSideSocket,isGui);

        }
        else if(message instanceof ViewGameboardMessage)    {
            message.execute(guiSideSocket,isGui);
        }
        else if(message instanceof NotYourTurnMessage){
            message.execute(guiSideSocket,isGui);
        }
        else if(message instanceof PrintableMessage){
            System.out.println(((PrintableMessage) message).getString());
        }
        else if(message instanceof BaseProdParametersMessage)   {
            message.execute(guiSideSocket,isGui);
        }
        else if(message instanceof AvailableResourcesForDevMessage){
            message.execute(guiSideSocket,isGui);
        }
    }
}
