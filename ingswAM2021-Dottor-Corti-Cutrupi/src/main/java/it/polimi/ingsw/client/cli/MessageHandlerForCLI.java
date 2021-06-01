package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.actions.initializationActions.NotInInitializationAnymoreAction;
import it.polimi.ingsw.client.actions.matchManagementActions.NotInLobbyAnymore;
import it.polimi.ingsw.client.shared.ClientSideSocket;
import it.polimi.ingsw.model.resource.*;
import it.polimi.ingsw.server.messages.LorenzoWonMessage;
import it.polimi.ingsw.server.messages.Message;
import it.polimi.ingsw.server.messages.PlayerWonSinglePlayerMatch;
import it.polimi.ingsw.server.messages.connectionRelatedMessages.DisconnectionMessage;
import it.polimi.ingsw.server.messages.connectionRelatedMessages.RejoinAckMessage;
import it.polimi.ingsw.server.messages.gameCreationPhaseMessages.*;
import it.polimi.ingsw.server.messages.gameplayMessages.ResultsMessage;
import it.polimi.ingsw.server.messages.gameplayMessages.ViewGameboardMessage;
import it.polimi.ingsw.server.messages.gameplayMessages.WhiteToColorMessage;
import it.polimi.ingsw.server.messages.initializationMessages.MultipleLeaderCardsMessage;
import it.polimi.ingsw.server.messages.initializationMessages.GameInitializationFinishedMessage;
import it.polimi.ingsw.server.messages.initializationMessages.InitializationMessage;
import it.polimi.ingsw.server.messages.initializationMessages.OrderMessage;
import it.polimi.ingsw.server.messages.jsonMessages.*;
import it.polimi.ingsw.server.messages.notifications.Notification;
import it.polimi.ingsw.server.messages.printableMessages.PrintableMessage;
import it.polimi.ingsw.server.messages.printableMessages.ShowingDashboardMessage;
import it.polimi.ingsw.server.messages.rejoinErrors.RejoinErrorMessage;

import java.util.ArrayList;

/**
 * the ActionHandler handles the messages coming from the Server
 */
public class MessageHandlerForCLI implements Runnable{
    private ClientSideSocket clientSideSocket;
    private Message message;

    public MessageHandlerForCLI(ClientSideSocket clientSideSocket, Message messageToHandle) {
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
        else if(message instanceof DepotMessage) System.out.println(decipherDepot((DepotMessage) message));
        else if(message instanceof  StrongboxMessage){
            printStrongbox((StrongboxMessage) message);
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
            printDevCard((DevelopmentCardMessage) message);
        }
        else if(message instanceof DevelopmentCardsInDashboard){
            for(DevelopmentCardMessage message: ((DevelopmentCardsInDashboard) message).getMessages())
                printDevCard(message);
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
        else if(message instanceof LeaderCardMessage){
            printLeaderCard((LeaderCardMessage) message);
        }
        else if(message instanceof RejoinErrorMessage){
            System.out.println(((RejoinErrorMessage) message).getString());
            clientSideSocket.createOrJoinMatchChoice();
        }
        else if(message instanceof ShowingDashboardMessage){
            System.out.println("The dashboard you requested is:");
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
            printPlayerOrder(((OrderMessage) message).getPlayersNicknamesInOrder());
        }
        else if(message instanceof InitializationMessage){
            clientSideSocket.initialize(((InitializationMessage) message).getOrder(),((InitializationMessage) message).getLeaderCardsKept(),((InitializationMessage) message).getLeaderCardsGiven());
        }
        else if(message instanceof WhiteToColorMessage){
            clientSideSocket.whiteToColorChoices(((WhiteToColorMessage) message).getNumOfBlnks());
        }
        else if(message instanceof Notification)    clientSideSocket.manageNotification(message);
        else if(message instanceof LorenzoWonMessage) clientSideSocket.LorenzoWon();
        else if(message instanceof PlayerWonSinglePlayerMatch) clientSideSocket.playerWonSinglePlayerMatch((PlayerWonSinglePlayerMatch) message);
        else if(message instanceof MarketMessage) System.out.println(decipherMarket(message));
        else if(message instanceof MultipleLeaderCardsMessage)   {
            MultipleLeaderCardsMessage cardsToDiscardMessage= (MultipleLeaderCardsMessage) message;
            for(LeaderCardMessage leaderCardMessage:cardsToDiscardMessage.getMessages()){
                printLeaderCard(leaderCardMessage);
            }
        }
        else if(message instanceof PapalPathMessage) System.out.println(decipherPapalPath(message));
        else if(message instanceof ViewGameboardMessage)    {
            for(DevelopmentCardMessage developmentCardMessage: ((ViewGameboardMessage) message).getMessages()){
                printDevCard(developmentCardMessage);
            }
        }
    }

    private void printPlayerOrder(ArrayList<String> playersNicknamesInOrder) {
        System.out.println("The order has been randomized! Here's the list of players:");
        int gameSize = playersNicknamesInOrder.size();
        for (int i = 0; i < gameSize; i++) {
            switch (i) {
                case 0: {
                    System.out.println("First to play: " + playersNicknamesInOrder.get(0));
                    break;
                }
                case 1: {
                    System.out.println("Second to play: " + playersNicknamesInOrder.get(1));
                    break;
                }
                case 2: {
                    System.out.println("Third to play: " + playersNicknamesInOrder.get(2));
                    break;
                }
                case 3: {
                    System.out.println("Fourth to play: " + playersNicknamesInOrder.get(3));
                    break;
                }
            }
        }
        System.out.println("");
    }

    public void printDevCard(DevelopmentCardMessage message){
        System.out.println("Development card:");
        System.out.println("Card price: " + parseIntArrayToStringOfResources(message.getCardPrice()));
        System.out.println("Card Stats: " + message.getLevel() + " " + parseIntToColorString(message.getColor()) + ",");
        System.out.println("Production requirements: " + parseIntArrayToStringOfResources(message.getProdRequirements()));
        System.out.println("Production results: " + parseIntArrayToStringOfResources(message.getProdResults()));
        System.out.println("VictoryPoints: " + message.getVictoryPoints());
        System.out.println("\n");
    }

    public void printLeaderCard(LeaderCardMessage message){
        System.out.println("\n");
        System.out.println("Leader Card number "+ message.getLeaderCardZone() + ":");
        if(message.isNeedsResources()==true){
            System.out.println("Resources required: " + parseIntArrayToStringOfResources(message.getResourcesRequired()));
        }else{
            System.out.println("Development cards required: " + parseIntToDevCardRequirement(message.getDevelopmentCardsRequired()));
        }
        System.out.println("Special power: " + parseIntToSpecialPower(message.getSpecialPower()));
        System.out.println("Special power resources: " + parseIntArrayToStringOfResources(message.getSpecialPowerResources()));
        System.out.println("Victory points: " + message.getVictoryPoints());
        if(message.isActive())  System.out.println("This card is currently active\n");
        if(!message.isActive())  System.out.println("This card is currently not active\n");
    }

    public void printStrongbox(StrongboxMessage message){
        System.out.println("Resources in the strongbox:");
        System.out.println(parseIntArrayToStringOfResources(message.getResourcesContained()));
    }


    public String parseIntToSpecialPower(int i){
        if(i==0){
            return "discount";
        }else if(i==1){
            return "extraDeposit";
        }else if(i==2){
            return "extraProd";
        }else if(i==3){
            return "whiteToColor";
        }else {
            return "error";
        }
    }


    public String parseIntArrayToStringOfResources(int[] resources){
        String string = new String();
        if(resources[0]!=0) {
            string += resources[0];
            string += " coins, \t";
        }
        if(resources[1]!=0) {
            string += resources[1];
            string += " stones, \t";
        }
        if(resources[2]!=0) {
            string += resources[2];
            string += " servants, \t";
        }
        if(resources[3]!=0) {
            string += resources[3];
            string += " shields, \t";
        }
        if(resources[4]!=0) {
            string += resources[4];
            string += " faith, \t";
        }
        return string;
    }

    public String parseIntToColorString(int i){
        if(i==0){
            return "blue";
        }else if(i==1){
            return "green";
        }else if(i==2){
            return "yellow";
        }else if(i==3){
            return "purple";
        }else {
            return "error";
        }
    }

    public String parseIntToDevCardRequirement(int[] decks){
        String string = new String();
        if(decks[0]!=0) {
            string += decks[0];
            string += " Blue development cards level 1, \t";
        }
        if(decks[1]!=0) {
            string += decks[1];
            string += " Blue development cards level 2, \t";
        }
        if(decks[2]!=0) {
            string += decks[2];
            string += " Blue development cards level 3, \t";
        }
        if(decks[3]!=0) {
            string += decks[3];
            string += " Green development cards level 1, \t";
        }
        if(decks[4]!=0) {
            string += decks[4];
            string += " Green development cards level 2, \t";
        }
        if(decks[5]!=0) {
            string += decks[5];
            string += " Green development cards level 3, \t";
        }
        if(decks[6]!=0) {
            string += decks[6];
            string += " Yellow development cards level 1, \t";
        }
        if(decks[7]!=0) {
            string += decks[7];
            string += " Yellow development cards level 2, \t";
        }if(decks[8]!=0) {
            string += decks[8];
            string += " Yellow development cards level 3, \t";
        }
        if(decks[9]!=0) {
            string += decks[9];
            string += " Purple development cards level 1, \t";
        }
        if(decks[10]!=0) {
            string += decks[10];
            string += " Purple development cards level 2, \t";
        }
        if(decks[11]!=0) {
            string += decks[11];
            string += " Purple development cards level 3, \t";
        }
        return string;
    }

    public String decipherMarket(Message message) {
        SerializationConverter serializationConverter = new SerializationConverter();
        String string= new String("Here's the market:\n");
        MarketMessage marketMessage= (MarketMessage) message;
        Resource floatingMarble= serializationConverter.intToResource(((MarketMessage) message).getFloatingMarbleRepresentation());
        Resource[][] fakeMarket= new Resource[3][4];
        for(int row=0;row<3;row++){
            for(int column=0;column<4;column++){
                switch (marketMessage.getRepresentation()[row][column]){
                    case 0:
                        fakeMarket[row][column]= new CoinResource();
                        break;
                    case 1:
                        fakeMarket[row][column]= new StoneResource();
                        break;
                    case 2:
                        fakeMarket[row][column]= new ServantResource();
                        break;
                    case 3:
                        fakeMarket[row][column]= new ShieldResource();
                        break;
                    case 4:
                        fakeMarket[row][column]= new FaithResource();
                        break;
                    case 5:
                        fakeMarket[row][column]= new BlankResource();
                        break;
                }
            }
        }
        for(int row=0; row<3; row++){
            for(int column=0;column<4;column++){
                string+=(fakeMarket[row][column].getResourceType())+"\t";
            }
            string+="\n";
        }
        string+="\t\t\t\t\t\t\t\t"+floatingMarble.getResourceType();
        return string;
    }

    public String decipherPapalPath(Message message) {
        PapalPathMessage marketMessage= (PapalPathMessage) message;
        StringBuilder string= new StringBuilder("Here's your papal path:  (x=papal card zone, X=papal card, o=your position normally, O=your position when you're on a papal path card (or zone))\n ");
        string.append("|");
        for(int i=0;i<=24;i++){
            if((marketMessage.getPlayerFaithPos()!=i)){
                if(marketMessage.getTiles()[i]>10) string.append("X|");
                else if(marketMessage.getTiles()[i]>0) string.append("x|");
                else string.append(" |");
            }
            else if(marketMessage.getTiles()[i]>10) string.append("O|");
            else if(marketMessage.getTiles()[i]>0) string.append("O|");
            else string.append("o|");
        }
        string.append("\n");
        return String.valueOf(string);
    }

    public String decipherDepot(Message depotMessage){
        SerializationConverter serializationConverter = new SerializationConverter();
        DepotMessage message= (DepotMessage) depotMessage;
        StringBuilder string= new StringBuilder("Here are your depots: \n");
        for(int i=1;i< message.getSizeOfWarehouse();i++){
            string.append(i).append(": ");
            for(int j=0; j<message.getDepots()[i][1];j++){
                string.append("\t").append(serializationConverter.intToResource(message.getDepots()[i][0]));
            }
            string.append("\n");
        }
        if(message.getSizeOfExtraDepots()!=0){
            string.append("You also have the following extra depots: \n");
            for(int i=0; i<message.getSizeOfExtraDepots(); i++){
                for(int j=0; j<message.getDepots()[message.getSizeOfWarehouse()+1+i][1];j++)
                    string.append("\t").append(serializationConverter.intToResource(message.getDepots()[message.getSizeOfWarehouse()+1+i][1]));
                string.append("\n");
            }
        }
        return string.toString();
    }
}
