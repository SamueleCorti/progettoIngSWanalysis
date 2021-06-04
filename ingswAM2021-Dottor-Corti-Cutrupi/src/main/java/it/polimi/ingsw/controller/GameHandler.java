package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.client.actions.Action;
import it.polimi.ingsw.client.actions.initializationActions.BonusResourcesAction;
import it.polimi.ingsw.client.actions.initializationActions.DiscardLeaderCardsAction;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.ServerSideSocket;
import it.polimi.ingsw.server.Turn;
import it.polimi.ingsw.server.messages.connectionRelatedMessages.DisconnectionMessage;
import it.polimi.ingsw.server.messages.connectionRelatedMessages.RejoinAckMessage;
import it.polimi.ingsw.server.messages.gameCreationPhaseMessages.GameStartingMessage;
import it.polimi.ingsw.server.messages.gameplayMessages.AvailableResourcesForDevMessage;
import it.polimi.ingsw.server.messages.gameplayMessages.ViewGameboardMessage;
import it.polimi.ingsw.server.messages.gameplayMessages.WhiteToColorMessage;
import it.polimi.ingsw.server.messages.initializationMessages.*;
import it.polimi.ingsw.server.messages.jsonMessages.DevelopmentCardMessage;
import it.polimi.ingsw.server.messages.jsonMessages.LeaderCardMessage;
import it.polimi.ingsw.server.messages.jsonMessages.LorenzoIlMagnificoMessage;
import it.polimi.ingsw.server.messages.jsonMessages.MarketMessage;
import it.polimi.ingsw.server.messages.jsonMessages.*;
import it.polimi.ingsw.server.messages.notifications.MarketNotification;
import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.exception.warehouseErrors.FourthDepotWarehouseError;
import it.polimi.ingsw.exception.warehouseErrors.TooManyResourcesInADepot;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.adapters.NicknameFaithPosition;
import it.polimi.ingsw.model.boardsAndPlayer.Player;
import it.polimi.ingsw.model.developmentcard.Color;
import it.polimi.ingsw.model.developmentcard.DevelopmentCard;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.leadercard.leaderpowers.PowerType;
import it.polimi.ingsw.model.market.OutOfBoundException;
import it.polimi.ingsw.model.papalpath.CardCondition;
import it.polimi.ingsw.model.resource.*;
import it.polimi.ingsw.exception.warehouseErrors.WarehouseDepotsRegularityError;
import it.polimi.ingsw.server.messages.LorenzoWonMessage;
import it.polimi.ingsw.server.messages.Message;
import it.polimi.ingsw.server.messages.printableMessages.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class GameHandler {
    /** Server that contains this GameHandler */
    private final Server server;

    /** Unique game associated to this GameHandler. It contains the model that will be modified by the players connected */
    private Game game;

    /** Boolean is true if the game has started, false if it is still in lobby */
    private boolean isStarted;

    /** Contains the number of players allowed to play this game, decided by the game creator */
    private final int totalPlayers;

    private final Map<Integer,String> originalOrderToNickname;

    /** List of the clientsID CONNECTED to the game */
    private final ArrayList<Integer> clientsIDs;

    /** Unique identifier of the game associated to this gameHandler */
    private final int gameID;

    /** Unique socket related to the host of the game */
    private ServerSideSocket hostConnection;

    /** List of the nicknames of the players who joined the game */
    private final ArrayList<String> clientsNicknames;

    /** List of the connection of the player connected to the game*/
    private final ArrayList<ServerSideSocket> clientsInGameConnections;

    /**
     * This hashmap permits identifying a nickname relying on his game order
     */
    private final Map<Integer, String> orderToNickname;

    /**
     * This hashmap permits identifying a ServerSideSocket relying on his clientID
     * The client has to be connected to the game and to the server.
     */
    private final Map<Integer, ServerSideSocket> clientIDToConnection;

    /**
     * This hashmap permits identifying a nickname relying on his clientID
     * The client has to be connected to the game and to the server.
     */
    private final Map<Integer, String> clientIDToNickname;

    /**
     * This hashmap permits identifying a clientID relying on his nickname
     * If the player related to that nickname is disconnected, the value is null
     */
    private final Map<String,Integer> nicknameToClientID;

    private final Map<String,Integer> nicknameToHisTurnPhase;

    /**
     * This hashmap permits identifying the gamePhase of a player relying on his nickname
     * If the player related to that nickname is disconnected, the value is null
     */
    private final Map<String,Integer> nicknameToHisGamePhase;

    public Map<String, Integer> getNicknameToHisGamePhase() {
        return nicknameToHisGamePhase;
    }

    private final Map<String,Integer> nicknameToOrder;

    private int gamePhase=0;

    private int numOfInitializedClients=0;

    private final Turn turn;

    private final int numOfLeaderCardsKept;

    public int getNumOfLeaderCardsKept() {return numOfLeaderCardsKept;}

    private final int numOfLeaderCardsGiven;

    /**
     * Constructor GameHandler creates a new GameHandler instance.
     *
     * @param server of type Server - the main server class.
     */
    public GameHandler(Server server, int totalPlayers) {
        this.server = server;
        this.totalPlayers = totalPlayers;
        isStarted = false;
        clientsIDs = new ArrayList<>();
        clientsInGameConnections = new ArrayList<>();
        orderToNickname = new HashMap<>();
        clientIDToConnection = new HashMap<>();
        clientIDToNickname = new HashMap<>();
        nicknameToClientID = new HashMap<>();
        nicknameToOrder= new HashMap<>();
        clientsNicknames = new ArrayList<>();
        nicknameToHisGamePhase = new HashMap<>();
        nicknameToHisTurnPhase = new HashMap<>();
        originalOrderToNickname = new HashMap<>();
        gameID = generateNewGameID();


        //we import the number of leaderCards for each player
        JsonReader reader1 = null;
        try {
            reader1 = new JsonReader(new FileReader("src/main/resources/leadercardsparameters.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        JsonParser parser1 = new JsonParser();
        JsonArray favorArray = parser1.parse(reader1).getAsJsonArray();
        Gson gson1 = new Gson();
        int[] arr = gson1.fromJson(favorArray, int[].class);
        this.numOfLeaderCardsKept = arr[1];
        this.numOfLeaderCardsGiven = arr[0];
        turn = new Turn(numOfLeaderCardsKept);
    }

    /**
     * Method used when a new player connects to the gameHandler, firstly adds the player's attributes to the gameHandler (by
     * using method @addNewPlayer) then checks if the new player was the last one required to full the match or not. If he was,
     * method notifies all the player that the match is starting; else notifies all the player that a new player has connected
     *
     * @param clientID int provided by the server that identifies uniquely a client
     * @param connection socket used by that client
     * @param nickname string used to identify a player in the room (players in the same room can't have the same name)
     * @throws InterruptedException when TimeUnit throws it
     */
    public void lobby(int clientID, ServerSideSocket connection, String nickname) throws InterruptedException {
        //gameHandler is updated with the new client values
        addNewPlayer(clientID,connection, nickname);

        //case game is full, match is ready to start and all the players are notified of the event
        if(clientsInGameConnections.size()==totalPlayers){
            System.err.println("Number of players required for the gameID=" +gameID+" reached. The match is starting.");
            //TimeUnit.MILLISECONDS.sleep(500);
            sendAll(new GameStartingMessage());
            setup();
        }

        //room is not full yet, all the player are notified that there is one less empty spot in the room
        else {
            sendAllExcept(new SlotsLeft(totalPlayers - clientsInGameConnections.size()),clientID);
        }
    }

    /**
     * @return the gameID generated by the server for this game
     */
    public int generateNewGameID(){
        return server.createGameID();
    }


    /**
     * Method used to send a message to all the clients connected to the game
     * @param message contains the message to send
     */
    public void sendAll(Message message){
        for(int clientId: clientsIDs){
            sendMessage(message,clientId);
        }
    }

    public void sendAllExceptActivePlayer(Message message){
        for(int clientId: clientsIDs){
            if(!(game.getActivePlayer().getNickname()).equals(clientIDToNickname.get(clientId)))    sendMessage(message,clientId);
        }
    }

    /**
     * @return gameID
     */
    public int getGameID() {
        return gameID;
    }

    /**
     * Method isStarted returns true if the game has started (the started attribute becomes true after the challenger
     * selection phase).
     */
    public boolean isStarted() {
        return isStarted;
    }

    /**
     * Method used to set a connection as the connection of the host of the game
     * @param serverSideSocket is the connection to set as host
     */
    public void setHost(ServerSideSocket serverSideSocket){
        this.hostConnection = serverSideSocket;
    }

    /**
     * Method sends a message to every client connected to the game except one
     * @param s is the message to send
     * @param clientID the ID of the player not to send the message of
     */
    private void sendAllExcept(Message s, int clientID) {
        for (int id:clientsIDs) {
            if(id!=clientID) sendMessage(s,id);
        }
    }


    /**
     * Method used to send a message to a client relying on his ID
     * @param message is the message to send
     * @param id is the clientID of the player the method must send the message of
     */
    public void sendMessage(Message message, int id){
        clientIDToConnection.get(id).sendSocketMessage(message);
    }


    /**
     * Method setup handles the preliminary actions
     */
    public void setup() {
        //Since the game has started, we must update the lists of the server
        server.getMatchesInLobby().remove(this);
        server.getMatchesInGame().add(this);
        //With this command we create a game class and its model
        game = new Game(clientsInGameConnections, gameID);

        for(int i = 0; i< game.getPlayers().size(); i++){
            nicknameToOrder.put(game.getPlayers().get(i).getNickname(), i+1);
            orderToNickname.put(i+1, game.getPlayers().get(i).getNickname());
        }

        for (String nickname:clientsNicknames) {
            //We set each player to a new phase of the game (initialization phase)
            nicknameToHisGamePhase.replace(nickname,1);
        }

        gamePhase++;
        for (int id: clientsIDs) {
            ArrayList<LeaderCardMessage> messages=new ArrayList<>();
            int i=0;
            for(LeaderCard leaderCard: game.playerIdentifiedByHisNickname(clientIDToNickname.get(id)).getLeaderCardsCopy()){
                messages.add(new LeaderCardMessage(leaderCard,i));
                i++;
            }
            MultipleLeaderCardsMessage cardsToDiscardMessage= new MultipleLeaderCardsMessage(messages);
            sendMessage(cardsToDiscardMessage,id);
            InitializationMessage messageToSend = new InitializationMessage(clientIDToConnection.get(id).getOrder(),numOfLeaderCardsKept,numOfLeaderCardsGiven);
            sendMessage(messageToSend, id);
        }

        //  sendAll(new OrderMessage(game));
        if(!isStarted) isStarted=true;
        setBaseProd();
        sendAll(new MarketMessage(game.getMarket()));
    }


    /**
     * @return the server class.
     */
    public Server getServer() {
        return server;
    }


    /**
     * Method used when a new player connects to the room, so the gameHandler saves his attributes locally and assigns
     * the order in the game to the new player
     *
     * @param clientID int provided by the server that identifies uniquely a client
     * @param clientSingleConnection socket used by that client
     * @param nickname string used to identify a player in the room (players in the same room can't have the same name)
     */
    public void addNewPlayer(int clientID, ServerSideSocket clientSingleConnection, String nickname){
        //the new player's client ID is added to the list
        clientsIDs.add(clientID);

        //the new player's nickname is added to the list
        clientsNicknames.add(nickname);

        //the player's connection is added to list
        clientsInGameConnections.add(clientSingleConnection);

        //updating maps with new player's values
        nicknameToHisGamePhase.put(nickname,0);
        nicknameToHisTurnPhase.put(nickname,0);
        clientIDToNickname.put(clientID,nickname);
        nicknameToClientID.put(nickname,clientID);
        clientIDToConnection.put(clientID,clientSingleConnection);

        //sending a message notifying that a new player has joined the lobby to all the players already in lobby
        sendAllExcept(new PlayerJoinedTheMatch(nickname), clientID);
        System.err.println("Player "+nickname+" joined gameID="+gameID);
    }


    /**
     * Method used to remove an ID from clientsIDs
     * @param idToRemove is the ID to remove from the list
     */
    public void removeID(int idToRemove){
        for(int i=0;i<clientsIDs.size();i++){
            if(clientsIDs.get(i)==idToRemove){
                clientsIDs.remove(i);
                return;
            }
        }
    }

    public void printAllResources(){
        Player player = activePlayer();
        String string="Here are all your resources: \n";
        string+="You have "+player.availableResourceOfType(ResourceType.Coin)+" coin; of those, "+
                player.producedThisTurn(ResourceType.Coin)+ " have just been produced this turn\n";
        string+="You have "+player.availableResourceOfType(ResourceType.Stone)+" stone; of those, "+
                player.producedThisTurn(ResourceType.Stone)+ " have just been produced this turn\n";
        string+="You have "+player.availableResourceOfType(ResourceType.Servant)+" servant; of those, "+
                player.producedThisTurn(ResourceType.Servant)+ " have just been produced this turn\n";
        string+="You have "+player.availableResourceOfType(ResourceType.Shield)+" shield; of those, "+
                player.producedThisTurn(ResourceType.Shield)+ " have just been produced this turn";
        sendMessageToActivePlayer(new PrintAString(string));
    }

    public Player[] playersOrderByFaithPosition(){
        NicknameFaithPosition[] temp= new NicknameFaithPosition[totalPlayers];


        for(int i=0; i<totalPlayers;i++)  {
            temp[i]= new NicknameFaithPosition(game.getNickname(i), game.getFaith(i));
        }

        for(int i = 0; i < totalPlayers; i++) {
            boolean flag = false;
            for(int j = 0; j < totalPlayers-1; j++) {
                if(temp[j].getFaithPosition()>temp[j+1].getFaithPosition()) {
                    NicknameFaithPosition k = temp[j];
                    temp[j] = temp[j+1];
                    temp[j+1] = k;
                    flag=true;
                }
            }
            if(!flag) break;
        }
        Player[] players= new Player[totalPlayers];
        for(int i=0; i<totalPlayers;i++){
            players[i]=game.getPlayerFromNickname(temp[i].getNickname());

        }
        return players;
    }


    /**
     * Method used to remove an ID from clientsIDs connection from clientsInGameConnections
     * @param connectionToRemove is the connection to remove from the list
     */
    public void removeConnection(ServerSideSocket connectionToRemove){
        for(int i=0;i<clientsInGameConnections.size();i++){
            if(clientsInGameConnections.get(i)==connectionToRemove){
                clientsInGameConnections.remove(i);
                return;
            }
        }
    }

    public void checkGameEnded(){
        if (game.isGameEnded()){
            endGame();
        }
    }

    /**
     * Method unregisterPlayer unregisters a player identified by his unique ID after a disconnection event or message.
     * @param id is the ID of the player to disconnect
     */
    public void unregisterPlayer(int id) {

        //All the lists and maps are updated removing the client who disconnected

        String nickname = clientIDToNickname.get(id);
        removeID(id);

        //If the room is empty, game ends
        if(clientsIDs.size()==0){
            System.out.println("Not anymore players");
            removeGameHandler();
        }

        sendAll(new DisconnectionMessage(nickname));
        nicknameToClientID.replace(nickname,null);

        if(gamePhase==1 && nicknameToHisGamePhase.get(clientIDToNickname.get(id))==2){
            numOfInitializedClients--;
        }

        if(gamePhase==2 && nicknameToHisGamePhase.get(clientIDToNickname.get(id))==2){
            nicknameToHisTurnPhase.put(nickname,turn.getActionPerformed());
        }


        ServerSideSocket connectionToRemove = clientIDToConnection.get(id);

        //If the player was the host, another player is set as new host.
        if(connectionToRemove.isHost()){
            setHost(clientIDToConnection.get(clientsIDs.get(0)));
            hostConnection.setHost(true);
            sendAll(new NewHostMessage(clientIDToNickname.get(clientsIDs.get(0))));
        }

        //the player was the active player
        if(connectionToRemove.equals(game.getActivePlayer())){
            turn.resetProductions();
            turn.setActionPerformed(0);
            checkGameEnded();
            game.nextTurnWhenActiveDisconnects();
            sendAllExcept(new NextTurnMessage(game.getActivePlayer().getNickname()),id);
        }

        clientIDToNickname.remove(id);
        removeConnection(clientIDToConnection.get(id));
        clientIDToConnection.remove(id);

        if(gamePhase==1 && nicknameToHisGamePhase.get(nickname)==1){
            checkInitializationIsOver();
        }
    }

    private void removeGameHandler() {
    }

    public void sendMessageToActivePlayer(Message message){
        sendMessage(message,getGame().getActivePlayer().getClientID() );
    }

    public void endGame() {
        if(game.getOrderOfEndingPLayer()==0) {
            sendAll(new LastRoundOfMatch());
            game.setOrderOfEndingPLayer(game.getActivePlayer().getOrder());
        }
    }


    /**
     * Method used to update the nickname of the player who was previously disconnected with all the new attributes related to it
     * (since the same player reconnected using a different ServerSideSocket and a different clientID)
     *
     * @param newServerSideSocket of type ServerSideSocket: the new ServerSideSocket of the player reconnected
     * @param nickname of type String: the nickname of the player who has reconnected
     */
    public void reconnectPlayer(ServerSideSocket newServerSideSocket, String nickname) {
        clientsIDs.add(newServerSideSocket.getClientID());
        clientsInGameConnections.add(newServerSideSocket);
        clientIDToConnection.put(newServerSideSocket.getClientID(),newServerSideSocket);
        clientIDToNickname.put(newServerSideSocket.getClientID(),nickname);
        nicknameToClientID.replace(nickname,newServerSideSocket.getClientID());

        int id = nicknameToClientID.get(nickname);


        sendMessage(new RejoinAckMessage(nicknameToHisGamePhase.get(nickname),totalPlayers),newServerSideSocket.getClientID());

        int order= nicknameToOrder.get(nickname);
        newServerSideSocket.setOrder(order);

        sendMessage(new OrderMessage(game),newServerSideSocket.getClientID());

        switch (nicknameToHisGamePhase.get(nickname)){
            case 1:
                sendMessage(new GameStartingMessage(),newServerSideSocket.getClientID());


                ArrayList<LeaderCardMessage> messages=new ArrayList<>();
                int i=0;
                for(LeaderCard leaderCard: game.playerIdentifiedByHisNickname(nickname).getLeaderCardsCopy()){
                    messages.add(new LeaderCardMessage(leaderCard,i));
                    i++;
                }
                MultipleLeaderCardsMessage cardsToDiscardMessage= new MultipleLeaderCardsMessage(messages);
                sendMessage(cardsToDiscardMessage,nicknameToClientID.get(nickname));
                InitializationMessage messageToSend = new InitializationMessage(clientIDToConnection.get(id).getOrder(),numOfLeaderCardsKept,numOfLeaderCardsGiven);
                sendMessage(messageToSend, id);
                break;
            case 2:
                if(gamePhase==1){
                    numOfInitializedClients++;
                }

                sendMessage(new ReconnectedDuringGamePhase(),newServerSideSocket.getClientID());
                game.reconnectAPlayerThatWasInGamePhase();
                sendMessage(new GameStartingMessage(),id);
                game.reorderPlayersTurns();
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sendMessage(new GameInitializationFinishedMessage(),id);
                sendMessage(new NextTurnMessage(activePlayer().getNickname()),id);
                break;
            default: break;

        }
        sendMessage(new BaseProdParametersMessage(activePlayer().getDashboardCopy().getNumOfStandardProdRequirements()
                ,activePlayer().getDashboardCopy().getNumOfStandardProdResults()),id);
        if(nicknameToHisTurnPhase.get(nickname)==3){
            System.out.println("Caso 3");
            newServerSideSocket.sendSocketMessage(new YouMustDeleteADepot());
            sendMessage(new ExceedingDepotMessage(game.playerIdentifiedByHisNickname(nickname).getDashboardCopy()),id);
        }
        else if(nicknameToHisTurnPhase.get(nickname)==4){
            System.out.println("Caso 4");
            newServerSideSocket.sendSocketMessage(new YouMustDiscardResources());
            sendMessage(new ExceedingDepotMessage(game.playerIdentifiedByHisNickname(nickname).getDashboardCopy()),id);
        }
        else if(nicknameToHisTurnPhase.get(nickname)==5){
            System.out.println("Caso 5");
            newServerSideSocket.sendSocketMessage(new YouMustSelectWhiteToColorsFirst());
        }
        sendAllExcept(new PlayerRejoinedTheMatch(nickname),newServerSideSocket.getClientID());
    }

    /**
     *
     * @param nickname of type String: name to look for
     * @return true if there is already a player with the same name as the parameter nickname
     */
    public boolean isNicknameAlreadyTaken(String nickname){
        return clientsNicknames.contains(nickname);
    }

    /**
     * @return number of totalPlayer set by the host at the match creation
     */
    public int getTotalPlayers() {
        return totalPlayers;
    }



    public void discardExtraResources(ArrayList<ResourceType> resources, int clientID) {
        Player player;
        if(clientID==(-1)) player=activePlayer();
        else player = game.playerIdentifiedByHisNickname(clientIDToNickname.get(clientID));
        for(ResourceType resourceType: resources){
            for(int i=1; i<= player.sizeOfWarehouse();i++){
                try {
                    if (player.lengthOfDepot(i)>0 && player.depotType(i)==resourceType) {
                        player.removeResource(i);
                        sendAllExcept(new YouWillMoveForward(game.getActivePlayer().getNickname()),clientID);
                        sendMessage(new DiscardedSuccessfully(),clientID);
                        moveForwardPapalPath(player);
                    }
                } catch (WarehouseDepotsRegularityError warehouseDepotsRegularityError) {
                    sendMessage(new NotNewResources(),clientID);
                }
            }
        }
        try {
            player.swapResources();
            sendMessage(new DiscardOKDepotOK(),clientID);
            sendMessage(new DepotMessage(player.getDashboardCopy()),clientID);
            for(Player playerAdvancing: game.playersInGame()){
                if (playerAdvancing!=activePlayer())    sendMessage(new PapalPathMessage(playerAdvancing.getPapalPath()), getNicknameToClientID().get(playerAdvancing.getNickname()));
            }
            if(game.isPlayerJustReconnected()){
                nicknameToHisTurnPhase.replace(clientIDToNickname.get(clientID),0);
                game.setClientDisconnectedDuringHisTurn(false);
            }
            else nicknameToHisTurnPhase.replace(clientIDToNickname.get(clientID),1);
        } catch (WarehouseDepotsRegularityError warehouseDepotsRegularityError) {
            if(warehouseDepotsRegularityError instanceof FourthDepotWarehouseError){
                nicknameToHisTurnPhase.replace(clientIDToNickname.get(clientID),3);
                sendMessage(new YouMustDeleteADepot(),clientID);
                sendMessageToActivePlayer(new ExceedingDepotMessage(player.getDashboardCopy()));
                sendMessage(new DepotMessage(player.getDashboardCopy()),clientID);
            }
            else if(warehouseDepotsRegularityError instanceof TooManyResourcesInADepot){
                nicknameToHisTurnPhase.replace(clientIDToNickname.get(clientID),4);
                sendMessage(new YouMustDiscardResources(),clientID);
                sendMessage(new ExceedingDepotMessage(player.getDashboardCopy()),clientID);
            }
        }
    }

    public void moveForwardPapalPath(Player activePlayer){
        Player[] players = playersOrderByFaithPosition();
        for(int i=0; i<players.length;i++){
            if( players[i]!=activePlayer) {
                try {
                    players[i].moveForwardFaith();
                } catch (PapalCardActivatedException e) {
                    int index=e.getIndex()+1;
                    sendAllExcept(new PlayerActivatePapalCard(players[i].getNickname(),index),
                            getNicknameToClientID().get(players[i].getNickname()));
                    sendMessage(new YouActivatedPapalCard(index), nicknameToClientID.get(players[i].getNickname()));
                    checkPapalCards(e.getIndex(), players[i]);

                }
                sendMessage(new NewFaithPosition(players[i].getFaithPosition()), nicknameToClientID.get(players[i].getNickname()));
            }
        }
        if(game.isSinglePlayer()) {
            try {
                activePlayer.moveForwardLorenzo();
            } catch (LorenzoWonTheMatch lorenzoWonTheMatch) {
                sendAll(new LorenzoWonMessage());
            } catch (LorenzoActivatesPapalCardException e) {
                sendAll(new LorenzoActivatedPapalCardAndYouDidnt(e.getCardIndex()));
            } catch (BothPlayerAndLorenzoActivatePapalCardException e) {
                sendAll(new LorenzoActivatedpapalCardAndYouToo(e.getCardIndex()));
            }
        }
    }
    /*
        public void moveForwardPapalPathActivePlayer(){
            Player player= game.getGameBoard().getPlayerFromNickname(game.getActivePlayer().getNickname());
            int cardActivated=player.getDashboard().getPapalPath().moveForward();
            sendMessageToActivePlayer(new PrintableMessage("Your faith position is "+player.getDashboard().getPapalPath().getFaithPosition()));
            if(cardActivated!=-1)    {
                int index=cardActivated+1;
                sendAllExcept(new PrintableMessage(player.getNickname()+" has just activated the papal card number "+ index),
                        getNicknameToClientID().get(player.getNickname()));
                sendMessage(new PrintableMessage("You just activated the leader card number: "+index), nicknameToClientID.get(player.getNickname()));
                checkPapalCards(cardActivated, player);
            }
        }
    */
    private void checkPapalCards(int cardActivated, Player playerThatActivatedThePapalCard) {
        int index;
        for(Player player: game.playersInGame()){
            if  (player!=playerThatActivatedThePapalCard) {
                index=player.checkPositionOfGivenPapalCard(cardActivated);
                if(index!=0){
                    sendMessage(new YouActivatedPapalCardToo(index+1), clientsInGameConnections.get(player.getOrder()).getClientID());
                }
                else{
                    sendMessage(new YouDidntActivatePapalCard(index+1), clientsInGameConnections.get(player.getOrder()-1).getClientID());
                }
            }
        }
    }


    public void printDepotsOfActivePlayer(){
        Player player = activePlayer();
        StringBuilder string= new StringBuilder("Here are your depots: \n");
        for(int i=1;i<=player.sizeOfWarehouse();i++){
            string.append(i).append(": ");
            for(int j=0; j<player.lengthOfDepotGivenItsIndex(i);j++){
                string.append("\t").append(player.typeOfDepotGivenItsIndex(i));
            }
            string.append("\n");
        }
        if(player.numberOfExtraDepots()!=0){
            string.append("You also have the following extra depots: \n");
            for(int i=0; i<player.numberOfExtraDepots(); i++){
                for(int j=0; j<player.resourcesContainedInAnExtraDepotGivenItsIndex(i);j++)
                    string.append("\t").append(player.typeOfExtraDepotGivenItsIndex(i));
                string.append("\n");
            }
        }
        sendMessageToActivePlayer(new PrintAString(string.toString()));
    }

    public void printMarket(){
        sendMessageToActivePlayer(new MarketMessage(game.getMarket()));
    }

    /**
     * Method used to get resources from market. Sets actionPerformed in turn to 1 if all goes well.
     */
    public void marketAction(int index, boolean isRow) {
        String nickname = activePlayer().getNickname();
        Player player = game.playerIdentifiedByHisNickname(nickname);
        try {
            game.acquireResourcesFromMarket(player,isRow,index);
            printDepotsOfActivePlayer();
            sendMessageToActivePlayer(new NewFaithPosition(player.getFaithPosition()));
            sendMessageToActivePlayer(new DepotMessage(player.getDashboardCopy()));
            sendMessageToActivePlayer(new AvailableResourcesForDevMessage(player));
            sendMessageToActivePlayer(new PapalPathMessage(player.getPapalPath()));
            turn.setActionPerformed(1);
            nicknameToHisTurnPhase.replace(activePlayer().getNickname(), 1);
        } catch (OutOfBoundException e) {
            e.printStackTrace();
        } catch (WarehouseDepotsRegularityError e){
            if(e instanceof FourthDepotWarehouseError){
                turn.setActionPerformed(3);
                nicknameToHisTurnPhase.replace(activePlayer().getNickname(), 3);
                sendMessageToActivePlayer(new YouMustDeleteADepot());
                sendMessageToActivePlayer(new ExceedingDepotMessage(player.getDashboardCopy()));
                printDepotsOfActivePlayer();
            }
            else if(e instanceof TooManyResourcesInADepot){
                turn.setActionPerformed(4);
                nicknameToHisTurnPhase.replace(activePlayer().getNickname(), 4);
                sendMessageToActivePlayer(new YouMustDiscardResources());
                sendMessageToActivePlayer(new ExceedingDepotMessage(player.getDashboardCopy()));
                printDepotsOfActivePlayer();
            }
        } catch (PapalCardActivatedException e) {
            checkPapalCards(e.getIndex(), player);
            printDepotsOfActivePlayer();
            sendMessageToActivePlayer(new NewFaithPosition(player.getFaithPosition()));
            sendMessageToActivePlayer(new PapalPathMessage(activePlayer().getPapalPath()));
            turn.setActionPerformed(1);
        }
        sendAllExceptActivePlayer(new MarketNotification(index, isRow,player.getNickname()));
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sendAllExceptActivePlayer(new MarketMessage(game.getMarket()));
    }

    /**
     * Method used to buy the player the requested development card. Sets actionPerformed in turn to 1 if all goes well.
     * @param color color of the card to buy
     * @param level level of the card to buy
     * @param index index of the Development Zone to put the card into
     * @return true if the acquirement goes well
     */
    public boolean developmentAction (Color color, int level, int index){
        try {
            game.buyDevelopmentCard(activePlayer(),color,level,index);
            turn.setActionPerformed(1);
            nicknameToHisTurnPhase.replace(activePlayer().getNickname(), 1);
            sendMessage(new BuyCardAck(),game.getActivePlayer().getClientID());
            sendAll(new CardBoughtByAPlayer(activePlayer().getNickname(),color,level));
            if(game.deckSize(color, level)>0) {
                //sendAll(new DevelopmentCardMessage(game.getFirstCardCopy(color, level)));
            }
            //else sendAll(new DevelopmentCardMessage(null));
            activePlayer().swapResources();
            return true;
        } catch (NotCoherentLevelException e) {
            sendMessage(new WrongZoneInBuyMessage(),game.getActivePlayer().getClientID());
        }
        catch(NotEnoughResourcesException e){
            sendMessage(new NotEnoughResourcesToBuy(),game.getActivePlayer().getClientID());
        } catch (WarehouseDepotsRegularityError warehouseDepotsRegularityError) {
            warehouseDepotsRegularityError.printStackTrace();
        }
        return false;
    }

    public boolean developmentFakeAction (Color color, int level, int index){
        try {
            activePlayer().buyDevelopmentCardFake(color, level, index);
            sendMessage(new BuyCardAck(),game.getActivePlayer().getClientID());
            sendAll(new CardBoughtByAPlayer(activePlayer().getNickname(),color,level));
            if(game.getGameBoard().getDeckOfChoice(color, level).deckSize()>0) {
                //sendAll(new DevelopmentCardMessage(this.game.getGameBoard().getDeckOfChoice(color, level).getFirstCard()));
            }
            //else sendAll(new DevelopmentCardMessage(null));
            return true;
        } catch (NotCoherentLevelException e) {
            sendMessage(new WrongZoneInBuyMessage(),game.getActivePlayer().getClientID());
        }
        catch(NotEnoughResourcesException e){
            sendMessage(new NotEnoughResourcesToBuy(),game.getActivePlayer().getClientID());
        }
        return false;
    }

    public boolean[] productionsActivatedInThisTurn(){
        return turn.getProductions();
    }

    /**
     *
     */
    /*public void productionAction(Action action,String nickname){

        Player player = activePlayer();

        boolean productionMade = false;
        boolean[] productions= turn.getProductions();


        if (action instanceof BaseProductionAction) {


            //CORRECT PATH: USER DIDN'T ACTIVATE BASE PRODUCTION IN THIS TURN
            if(!productions[0]){
                /*if (baseProduction((BaseProductionAction) action, nickname)) {
                    productionMade=true;
                    sendMessage(new ProductionAck(), nicknameToClientID.get(nickname));
                }
            }

            //WRONG PATH: USER ALREADY ACTIVATED BASE PRODUCTION IN THIS TURN
            else {
                sendMessage(new ProductionAlreadyActivatedInThisTurn(),nicknameToClientID.get(nickname));
            }
        }

        else if (action instanceof LeaderProductionAction){
            int leaderCardZoneIndex= ((LeaderProductionAction) action).getLeaderCardZoneIndex()-1;

            if(leaderCardZoneIndex<0||leaderCardZoneIndex>(numOfLeaderCardsKept-1)){
                sendMessage(new WrongLeaderCardIndex(),nicknameToClientID.get(nickname));
            }else{
                //CORRECT PATH: USER DIDN'T ACTIVATE THE LEADER CARD PRODUCTION OF THE SELECTED CARD IN THIS TURN
                if (!productions[leaderCardZoneIndex+4] && game.getGameBoard().getPlayerFromNickname(nickname)
                        .getLeaderCardZone().getLeaderCards().size()>leaderCardZoneIndex){
                    /*if(leaderProduction((LeaderProductionAction) action, nickname)) {
                        productionMade=true;
                        sendMessage(new ProductionAck(), nicknameToClientID.get(nickname));
                    }
                }

                //WRONG PATH: USER ASKED FOR A PRODUCTION HE ALREADY ACTIVATED IN THIS TURN
                else if(productions[leaderCardZoneIndex+4]) sendMessage(new ProductionAlreadyActivatedInThisTurn(),nicknameToClientID.get(nickname));
                else {
                    sendMessage(new WrongLeaderCardIndex(),nicknameToClientID.get(nickname));
                }
            }
        }

        else if (action instanceof DevelopmentProductionAction){
            int devCardZoneIndex= ((DevelopmentProductionAction) action).getDevelopmentCardZone();

            //CORRECT PATH: USER ASKED FOR A PRODUCTION HE DIDN'T ACTIVATE IN THIS TURN YET
            if (!productions[devCardZoneIndex+1] && game.getGameBoard().getPlayerFromNickname(nickname).isLastCardOfTheSelectedDevZoneNull(devCardZoneIndex)){

                //CORRECT PATH: USER HAS GOT ENOUGH RESOURCES TO ACTIVATE THE PRODUCTION
                if(devCardProduction(devCardZoneIndex)) {
                    productionMade=true;
                    int index=devCardZoneIndex+1;
                    sendMessage(new ProductionAck(), nicknameToClientID.get(nickname));
                }

                //WRONG PATH: USER HASN'T GOT ENOUGH RESOURCES TO ACTIVATE THE PRODUCTION
                else System.out.println("You don't have enough resources to activate this production");
                    sendMessage(new NotEnoughResourcesToProduce(),nicknameToClientID.get(nickname));
            }

            //WRONG PATH: USER ALREADY ACTIVATED THIS DEVELOPMENT CARD PRODUCTION IN THIS TURN
            else if(productions[devCardZoneIndex+1]){
                sendMessage(new ProductionAlreadyActivatedInThisTurn(),nicknameToClientID.get(nickname));
            }
            else
                sendMessage(new WrongZoneInProduce(),nicknameToClientID.get(nickname));
        }

        //IF THE PRODUCTION HAS BEEN ACTIVATED WITHOUT ERRORS, SERVER SENDS CLIENT AN TEMPORARY VERSION OF THE DEPOTS
        //AND OF THE RESOURCES PRODUCED
        if(productionMade){

            sendMessage(new ResourcesUsableForProd(parseListOfResources(player.getDashboard().resourcesUsableForProd())),
                    nicknameToClientID.get(nickname));
             sendMessage(new ResourcesProduced(parseListOfResources(player.getDashboard().getResourcesProduced()))
                     ,nicknameToClientID.get(nickname));
            turn.setActionPerformed(2);
        }
    }*/

    /**
     * Method called when a player wants to activate his base production and he didn't activate it yet
     * @param resourcesToUse are the resources to sacrifice
     * @param resourcesWanted are the resources resulted by the production
     * @return true if the baseProduction goes well
     */
    public boolean baseProduction(ArrayList<ResourceType> resourcesToUse,ArrayList<ResourceType> resourcesWanted){
        ArrayList<Resource> used = new ArrayList<>();
        for(ResourceType resourceEnum: resourcesToUse){
            used.add(parseResourceFromEnum(resourceEnum));
        }
        ArrayList<Resource> created = new ArrayList<>();
        for(ResourceType resourceEnum: resourcesWanted){
            created.add(parseResourceFromEnum(resourceEnum));
        }
        int resultOfActivation = activePlayer().activateBaseProduction(used, created);
        switch (resultOfActivation){
            case 0: //CASE ACTIVATE WORKED PERFECTLY
                try {
                    activePlayer().swapResources();
                } catch (WarehouseDepotsRegularityError warehouseDepotsRegularityError) {
                    warehouseDepotsRegularityError.printStackTrace();
                }
                return true;
            case 1: //CASE PLAYER DIDN'T HAVE ENOUGH RESOURCES TO ACTIVATE PROD
                sendMessageToActivePlayer(new NotEnoughResourcesToProduce());
                return false;
            case 2: //CASE PLAYER DIDN'T SELECT A CORRECT AMOUNT OF RESOURCES
                sendMessageToActivePlayer(new IncorrectAmountOfResources(activePlayer().baseProductionRequirements()
                        ,activePlayer().baseProductionResults()));
                return false;
            default: return false;
        }
    }

    /**
     * Method called when player wants to activate a leader card production and he didn't activate the prod
     * in this turn yet
     * @param leaderCardZoneIndex is the index of the card he wants to activate
     * @param resWanted list of resources wanted
     * @return true if the production goes well
     */
    public boolean leaderProduction(int leaderCardZoneIndex,ArrayList<ResourceType> resWanted) {

        int index= leaderCardZoneIndex;

        ArrayList <Resource> resourcesWanted = new ArrayList<Resource>();
        for(ResourceType resourceToParse: resWanted){
            resourcesWanted.add(parseResourceFromEnum(resourceToParse));
        }

        if(activePlayer().isALeaderProdCard(index)) {
            if (resourcesWanted.size() == activePlayer().resourcesToProduceInTheSpecifiedLeaderCard(index)) {
                try {
                    activePlayer().checkLeaderProduction(index);
                    activePlayer().leaderCardProduction(index, resourcesWanted);
                    for (int j = 0; j < resourcesWanted.size(); j++) {
                        try {
                            activePlayer().moveForwardFaith();
                        } catch (PapalCardActivatedException e) {
                            finishMoveForward(resourcesWanted.size()-j-1);
                            checkPapalCards(e.getIndex(), activePlayer());
                            sendMessageToActivePlayer(new PapalPathMessage(activePlayer().getPapalPath()));
                            sendMessageToActivePlayer(new YouActivatedPapalCard(e.getIndex()));
                        }
                    }
                    turn.setProductionPerformed(index + 4);
                    activePlayer().swapResources();
                    return true;
                } catch (LeaderCardNotActiveException e) {
                    sendMessageToActivePlayer(new CardNotActive());
                    return false;
                } catch (WrongTypeOfLeaderPowerException e) {
                    sendMessageToActivePlayer(new NotAProductionCard());
                    return false;
                } catch (NotEnoughResourcesToActivateProductionException e) {
                    sendMessageToActivePlayer(new NotEnoughResourcesToProduce());
                    return false;
                } catch (WarehouseDepotsRegularityError warehouseDepotsRegularityError) {
                    warehouseDepotsRegularityError.printStackTrace();
                    return false;
                }
            }else{
                sendMessageToActivePlayer(new WrongAmountOfResources(activePlayer().resourcesToProduceInTheSpecifiedLeaderCard(index)));
                return false;
            }
        }else{
            sendMessageToActivePlayer(new LeaderCardIsNotAProduction());
            return false;
        }
    }

    public void finishMoveForward(int index){
        for(int i=0; i<index; i++){
            try {
                activePlayer().moveForwardFaith();
            } catch (PapalCardActivatedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Called when the client selects a DevelopmentProductionAction
     * @param index: represents the development card zone that contains the wanted production
     * @return  true if the action has been performed correctly, false otherwise
     */
    public boolean devCardProduction(int index){
        try {
            activePlayer().activateDevelopmentProduction(index-1);
            activePlayer().swapResources();
            turn.setProductionPerformed(index);
        } catch (NotEnoughResourcesToActivateProductionException e) {
            sendMessageToActivePlayer(new NotEnoughResourcesToProduce());
            return false;
        } catch (PapalCardActivatedException e) {
            checkPapalCards(index,activePlayer());
            sendMessageToActivePlayer(new PapalPathMessage(activePlayer().getPapalPath()));
        } catch (WarehouseDepotsRegularityError warehouseDepotsRegularityError) {
            warehouseDepotsRegularityError.printStackTrace();
        }
        return true;
    }

    /**
     * Used when the player wants to activate a leader card. This method can get called anytime during the turn, before or after doing a main action, and doesn't
     * influence in any way the player's ability to perform any other action.
     * @param index: num of leader card to activate
     */
    public void activateLeaderCard(int index){
        if(index<this.numOfLeaderCardsKept) {
            try {
                activePlayer().activateLeaderCard(index);
                sendMessageToActivePlayer(new ActivatedLeaderCardAck(index+1));
            } catch (NotInactiveException e) {
                sendMessageToActivePlayer(new CardAlreadyActive());
            } catch (RequirementsUnfulfilledException e) {
                sendMessageToActivePlayer(new NotEnoughRequirementsToActivate());
            }
        }else{
            sendMessageToActivePlayer(new WrongLeaderCardIndex());
        }
    }

    /**
     * Used when the player wants to take at a dashboard. This method can get called anytime during the turn, before or after doing a main action, and doesn't
     * influence in any way the player's ability to perform any other action.
     * @param playerOrder: player order
     */
    public void viewDashboard(int playerOrder,int id) {
        if(id==(-1)) {
            id = game.getActivePlayer().getClientID();
        }
        int order = playerOrder;
        if (order == 0) {
            Player player = game.playerIdentifiedByHisNickname(clientIDToNickname.get(id));
            try {
                player.swapResources();
            } catch (WarehouseDepotsRegularityError e) {
                if(e instanceof FourthDepotWarehouseError){
                    nicknameToHisTurnPhase.replace(clientIDToNickname.get(id),3);
                    sendMessage(new YouMustDeleteADepot(),id);
                    sendMessage(new ExceedingDepotMessage(player.getDashboardCopy()),id);
                }
                else if(e instanceof TooManyResourcesInADepot){
                    nicknameToHisTurnPhase.replace(clientIDToNickname.get(id),4);
                    sendMessage(new YouMustDiscardResources(),id);
                    sendMessage(new ExceedingDepotMessage(player.getDashboardCopy()),id);
                }
            }
            sendMessage(new DepotMessage(player.getDashboardCopy()),id);
            sendMessage(new PapalPathMessage(player.getPapalPath()),id);
            sendMessage(new StrongboxMessage(player.getStrongbox(), player.getProducedResources()),id);
            sendMessage(new AvailableResourcesForDevMessage(player),id);
            ArrayList<DevelopmentCardMessage> developmentCardMessages = new ArrayList<>();
            for(int i=0; i<3; i++){
                for (DevelopmentCard developmentCard : player.getDevelopmentCardsInADevCardZone(i))
                    developmentCardMessages.add(new DevelopmentCardMessage(developmentCard,i));
            }
            sendMessage(new DevelopmentCardsInDashboard(developmentCardMessages),id);
            ArrayList<LeaderCardMessage> messages = new ArrayList<>();
            for (LeaderCard leaderCard:player.getLeaderCardsCopy()) {
                LeaderCardMessage leaderCardMessage = new LeaderCardMessage(leaderCard,player.indexOfALeaderCard(leaderCard));
                messages.add(leaderCardMessage);
            }
            MultipleLeaderCardsMessage message = new MultipleLeaderCardsMessage(messages);
            sendMessage(message,id);
        } else {
            Player player = game.playersInGame().get(order - 1);
            if (order < 1 || order > totalPlayers) {
                sendMessage(new NoPlayerAtTheSelectedIndex(),id);
            } else {
                sendMessage(new ShowingDashboardMessage(),id);
                try {
                    player.swapResources();
                } catch (WarehouseDepotsRegularityError warehouseDepotsRegularityError) {
                    warehouseDepotsRegularityError.printStackTrace();
                }
                sendMessage(new DepotMessage(player.getDashboardCopy()),id);
                sendMessage(new PapalPathMessage(player.getPapalPath()),id);
                sendMessage(new StrongboxMessage(player.getStrongbox(), player.getProducedResources()),id);
                ArrayList<DevelopmentCardMessage> developmentCardMessages = new ArrayList<>();
                for(int i=0; i<3; i++){
                    for (DevelopmentCard developmentCard : player.getDevelopmentCardsInADevCardZone(i))
                        developmentCardMessages.add(new DevelopmentCardMessage(developmentCard,i));
                }
                sendMessage(new DevelopmentCardsInDashboard(developmentCardMessages),id);

                ArrayList<LeaderCardMessage> messages = new ArrayList<>();
                for (LeaderCard leaderCard:player.getLeaderCardsCopy()) {
                    LeaderCardMessage leaderCardMessage = new LeaderCardMessage(leaderCard,player.indexOfALeaderCard(leaderCard));
                    messages.add(leaderCardMessage);
                }
                MultipleLeaderCardsMessage message = new MultipleLeaderCardsMessage(messages);
                sendMessage(message,id);
            }
        /*else {
            Message dashboardAnswer = new DashboardMessage(game.getGameBoard().getPlayerFromNickname(orderToNickname.get(order)).getDashboard());
            game.getActivePlayer().sendSocketMessage(dashboardAnswer);
        }*/
        }
    }


    public void printStrongbox(Player player){
        sendMessageToActivePlayer(new StrongboxMessage(player.getStrongbox(),player.getProducedResources()));
    }


    public void viewGameBoard(int id) {
        if(id==(-1)) {
            id=game.getActivePlayer().getClientID();
        }
        //Message gameBoardAnswer = game.createGameBoardMessage();
        int index=0;
        Color[] colors= new Color[]{Color.Blue, Color.Green, Color.Yellow, Color.Purple};
        DevelopmentCardMessage[] messages=new DevelopmentCardMessage[12];
        for(Color color: colors){
            for(int level=1;level<4; level++){
                if(game.getFirstCardCopy(color,level)!=null) {
                    messages[index] = new DevelopmentCardMessage(game.getFirstCardCopy(color, level), 0);
                }
                else{
                    messages[index] = null;
                }
                index++;
            }
        }
        Player player=game.playerIdentifiedByHisNickname(clientIDToNickname.get(id));
        int[] resources=new int[4];
        resources[0]= player.getDashboardCopy().availableResourcesForDevelopment(new CoinResource());
        resources[1]= player.getDashboardCopy().availableResourcesForDevelopment(new StoneResource());
        resources[2]= player.getDashboardCopy().availableResourcesForDevelopment(new ServantResource());
        resources[3]= player.getDashboardCopy().availableResourcesForDevelopment(new ShieldResource());
        ViewGameboardMessage viewGameboardMessage=new ViewGameboardMessage(messages, resources);
        sendMessage(viewGameboardMessage,id);
    }

    public ViewGameboardMessage viewGameBoard() {
        //Message gameBoardAnswer = game.createGameBoardMessage();
        int index=0;
        Color[] colors= new Color[]{Color.Blue, Color.Green, Color.Yellow, Color.Purple};
        DevelopmentCardMessage[] messages=new DevelopmentCardMessage[12];
        for(Color color: colors){
            for(int level=1;level<4; level++){
                if(game.getFirstCardCopy(color,level)!=null) {
                    messages[index] = new DevelopmentCardMessage(game.getFirstCardCopy(color, level), 0);
                }
                else{
                    messages[index] = null;
                }
                index++;
            }
        }
        return new ViewGameboardMessage(messages);
    }

    public void viewLorenzo() {
        if(clientsIDs.size()==1) {
            Message lorenzoAnswer = new LorenzoIlMagnificoMessage(game.getLorenzoIlMagnifico());
            sendMessageToActivePlayer(lorenzoAnswer);
        }
        else{
            sendMessageToActivePlayer(new ViewLorenzoError());
        }
    }

    public Game getGame() {
        return game;
    }

    /**
     * @return map containing ServerSideSocket related to their clientID
     */
    public Map<Integer, ServerSideSocket> getClientIDToConnection() {
        return clientIDToConnection;
    }

    /**
     * @return map containing ClientID related to their nickname
     */
    public Map<String, Integer> getNicknameToClientID() {
        return nicknameToClientID;
    }

    /**
     * @return true if all the players are online and connected to the game
     */
    public boolean allThePlayersAreConnected() {
        return totalPlayers == clientsInGameConnections.size();
    }

    /**
     * @return: {@link Turn}
     */
    public Turn getTurn() {
        return turn;
    }

    public Map<String, Integer> getNicknameToHisTurnPhase() {
        return nicknameToHisTurnPhase;
    }

    /**
     * @param resourceEnum: type of resource
     * @return: instance of a resource of the type selected
     */
    public Resource parseResourceFromEnum(ResourceType resourceEnum){
        switch (resourceEnum){
            case Coin: return new CoinResource();
            case Stone: return new StoneResource();
            case Servant: return new ServantResource();
            case Shield: return new ShieldResource();
        }
        return null;
    }

    public String parseListOfResources(ArrayList<Resource> list){
        StringBuilder string = new StringBuilder();
        for (Resource resource:list) {
            string.append(parseTypeFromResource(resource));
        }
        return string.toString();
    }

    public String parseTypeFromResource(Resource resourceToParse){
        if(resourceToParse instanceof CoinResource) return "coin ";
        if(resourceToParse instanceof StoneResource) return "stone ";
        if(resourceToParse instanceof ShieldResource) return "shield ";
        else return "servant ";
    }

    public void endTurn() {
        if(nicknameToHisTurnPhase.get(activePlayer().getNickname())==1 || nicknameToHisTurnPhase.get(activePlayer().getNickname())==2) {
            nicknameToHisTurnPhase.replace(activePlayer().getNickname(),0);
            turn.resetProductions();
            turn.setActionPerformed(0);
            checkGameEnded();
            game.nextTurn();
        }
        else if(nicknameToHisTurnPhase.get(activePlayer().getNickname())==3){
            sendMessageToActivePlayer(new YouMustDeleteADepotFirst());
        }
        else if(nicknameToHisTurnPhase.get(activePlayer().getNickname())==4){
            sendMessageToActivePlayer(new YouMustDiscardResourcesFirst());
        }
        else if(nicknameToHisTurnPhase.get(activePlayer().getNickname())==5){
            sendMessageToActivePlayer(new YouMustSelectWhiteToColorsFirst());
        }
        else sendMessageToActivePlayer(new YouMustDoAMainActionFirst());
    }

    public void newInitialization(String nickname) {
        numOfInitializedClients++;
        nicknameToHisGamePhase.replace(nickname,2);
        checkInitializationIsOver();
    }

    public void checkInitializationIsOver(){
        if(numOfInitializedClients==clientsInGameConnections.size()){
            isStarted=true;
            gamePhase++;
            sendAll(new GameInitializationFinishedMessage());
            sendAll(new OrderMessage(game));
            sendAll(new NextTurnMessage(game.getActivePlayer().getNickname()));
            sendMessageToActivePlayer(new MarketMessage(game.getMarket()));
        }
    }

    public void marketSpecialAction(ArrayList<Integer> cardsToActivate) {
        for(int i=0;i<cardsToActivate.size();i++){
            if(cardsToActivate.get(i)>activePlayer().numOfLeaderCards()||
                    !activePlayer().returnPowerTypeOfTheSelectedCard(cardsToActivate.get(i)).equals(PowerType.WhiteToColor)){
                sendMessageToActivePlayer(new InvalidIndexWhiteToColor());
                return;
            }
        }
        for(int i = 0; i<cardsToActivate.size(); i++){
            try {
                activePlayer().activateWhiteToColorCardWithSelectedIndex(cardsToActivate.get(i));
            } catch (PapalCardActivatedException e) {
                checkPapalCards(e.getIndex(),activePlayer());
            }
        }
        turn.setActionPerformed(1);
        nicknameToHisTurnPhase.replace(activePlayer().getNickname(),1);
        try {
            activePlayer().swapResourcesToDelete();
        }catch (WarehouseDepotsRegularityError e){
            if(e instanceof FourthDepotWarehouseError){
                turn.setActionPerformed(3);
                nicknameToHisTurnPhase.replace(activePlayer().getNickname(), 3);
                sendMessageToActivePlayer(new FourthDepot());
            }
            else if(e instanceof TooManyResourcesInADepot){
                turn.setActionPerformed(4);
                nicknameToHisTurnPhase.replace(activePlayer().getNickname(), 4);
                sendMessageToActivePlayer(new YouMustDiscardResources());
                sendMessageToActivePlayer(new ExceedingDepotMessage(activePlayer().getDashboardCopy()));
            }
        }
        printDepotsOfActivePlayer();
    }

    public void whiteToColorAction(ArrayList<Integer> resources,int clientID){
        Player player;
        if(clientID==(-1)) player = activePlayer();
        else player = game.playerIdentifiedByHisNickname(clientIDToNickname.get(clientID));
        SerializationConverter serializationConverter= new SerializationConverter();
        for(int resource: resources){
            try {
                serializationConverter.intToResource(resource).effectFromMarket(player.getDashboardCopy());
            } catch (PapalCardActivatedException e) {
                e.printStackTrace();
            }
        }
        nicknameToHisTurnPhase.replace(clientIDToNickname.get(clientID),1);
        try {
            player.swapResourcesToDelete();
        }catch (WarehouseDepotsRegularityError e){
            if(e instanceof FourthDepotWarehouseError){
                nicknameToHisTurnPhase.replace(clientIDToNickname.get(clientID),3);
                sendMessage(new FourthDepot(),clientID);
            }
            else if(e instanceof TooManyResourcesInADepot){
                nicknameToHisTurnPhase.replace(clientIDToNickname.get(clientID),4);
                sendMessage(new YouMustDiscardResources(),clientID);
                sendMessage(new ExceedingDepotMessage(player.getDashboardCopy()),clientID);
            }
        }
        sendMessage(new DepotMessage(player.getDashboardCopy()),clientID);
    }

    public void startingResources(BonusResourcesAction action, Player player){
        ResourceType resourceType= action.getResourceType1();
        if(action.getResourceType1()!=null) player.addResourceInWarehouse(parseResourceFromEnum(action.getResourceType1()));
        if(action.getResourceType2()!=null) player.addResourceInWarehouse(parseResourceFromEnum(action.getResourceType2()));
        try {
            player.swapResources();
        } catch (WarehouseDepotsRegularityError warehouseDepotsRegularityError) {
            warehouseDepotsRegularityError.printStackTrace();
        }
    }

    public boolean twoWhiteToColorCheck(Player player) {
        return player.activatedWhiteToColor() != null && player.activatedWhiteToColor().size() == 2;
    }

    public void test(Player player) {
        /*for (LeaderCard card:player.getLeaderCardZone().getLeaderCards()) {
            card.setCondition(CardCondition.Active);
            card.activateCardPower(player.getDashboard());
            try {
                activePlayer().swapResources();
            } catch (WarehouseDepotsRegularityError warehouseDepotsRegularityError) {
                warehouseDepotsRegularityError.printStackTrace();
            }
        }
        if(player.getDashboard().getWhiteToColorResources()!=null && player.getDashboard().getWhiteToColorResources().size()==2) System.out.println("Activated 2 wtc leaders");
        if(player.getDashboard().getResourcesForExtraProd()!=null && player.getDashboard().getResourcesForExtraProd().size()==2) System.out.println("Activated 2 extraProd leaders");
        if(player.getDashboard().getDiscountedResources()!=null && player.getDashboard().getDiscountedResources().size()==2) System.out.println("Activated 2 discount leaders");
        if(player.getDashboard().getExtraDepots()!=null && player.getDashboard().getExtraDepots().size()==2) System.out.println("Activated 2 depot leaders");
        */
        for(int i=0; i<7; i++){
            FaithResource faithResource= new FaithResource();
            try {
                faithResource.effectFromMarket(player.getDashboardCopy());
            }catch (PapalCardActivatedException e) {
                sendMessageToActivePlayer(new YouActivatedPapalCard(e.getIndex()+1));
                checkPapalCards(e.getIndex(), player);
                sendMessageToActivePlayer(new PapalPathMessage(activePlayer().getPapalPath()));
            }
        }
    }

    public void addInfiniteResources() {
        System.out.println("giving the current player infinite resources");
        for(int i=0;i<5;i++){
            activePlayer().addResourceInStrongbox(new CoinResource());
            activePlayer().addResourceInStrongbox(new StoneResource());
            activePlayer().addResourceInStrongbox(new ShieldResource());
            activePlayer().addResourceInStrongbox(new ServantResource());
        }
        //System.out.println("we're going to move papalpath forward for 24 moves");
        //game.getGameBoard().getPlayerFromNickname(game.getActivePlayer().getNickname()).getDashboard().getPapalPath().moveForward(24);
        //System.out.println("we did it");
    }

    public void discardLeaderCard(int index) {
        Player player = activePlayer();
        String nickname = player.getNickname();
        if(turn.getActionPerformed()!=4&&turn.getActionPerformed()!=5&&turn.getActionPerformed()!=3) {
            if (player.getLeaderCardsCopy() == null || player.getLeaderCardsCopy().size() < index + 1) {
                sendMessage(new WrongLeaderCardIndex(), nicknameToClientID.get(nickname));
            } else if (player.getLeaderCardZone().getLeaderCards().get(index).getCondition().equals(CardCondition.Inactive)) {
                player.removeLeaderCard(index);

                ArrayList<LeaderCardMessage> messages = new ArrayList<>();
                for (LeaderCard leaderCard:player.getLeaderCardsCopy()) {
                    LeaderCardMessage leaderCardMessage = new LeaderCardMessage(leaderCard,player.indexOfALeaderCard(leaderCard));
                    messages.add(leaderCardMessage);
                }
                MultipleLeaderCardsMessage message = new MultipleLeaderCardsMessage(messages);
                sendMessageToActivePlayer(message);

                sendMessage(new LeaderCardDiscardedAck(index), nicknameToClientID.get(nickname));
                try {
                    player.moveForwardFaith();
                } catch (PapalCardActivatedException e) {
                    checkPapalCards(e.getIndex(), player);
                    sendMessageToActivePlayer(new PapalPathMessage(activePlayer().getPapalPath()));
                }
                if (index == 0 && player.getLeaderCardZone().getLeaderCards().size() > 0) {
                    sendMessage(new LeaderCardIndexChanged(), nicknameToClientID.get(nickname));
                }
            } else {
                sendMessage(new CardIsNotInactive(), nicknameToClientID.get(nickname));
            }
        }else{
            sendMessage(new IncorrectPhaseMessage(), nicknameToClientID.get(nickname));
        }
    }


    public void discardDepot(int index, int clientID) {
        try {
            Player player;
            if(clientID==(-1)){
                player= activePlayer();
            }
            else{
                player = game.playerIdentifiedByHisNickname(clientIDToNickname.get(clientID));
            }
            int removedSize=player.removeExceedingDepot(index);
            for(int i=0; i<removedSize;i++) {
                sendAllExcept(new YouWillMoveForward(player.getNickname()),clientID);
                sendMessage(new DiscardedSuccessfully(),clientID);
                moveForwardPapalPath(player);
            }
            sendMessage(new DepotMessage(player.getDashboardCopy()),clientID);
            player.swapResources();
            sendMessage(new DiscardOKDepotOK(),clientID);
            for(Player playerAdvancing: game.playersInGame()){
                if (playerAdvancing!=activePlayer())
                    sendMessage(new PapalPathMessage(playerAdvancing.getPapalPath()), getNicknameToClientID().get(playerAdvancing.getNickname()));
            }
            if(game.isClientDisconnectedDuringHisTurn()){
                nicknameToHisTurnPhase.replace(clientIDToNickname.get(clientID),0);
                game.setClientDisconnectedDuringHisTurn(false);
            }
            else nicknameToHisTurnPhase.replace(clientIDToNickname.get(clientID),1);
        } catch (WarehouseDepotsRegularityError warehouseDepotsRegularityError) {
            printDepotsOfActivePlayer();
            if(warehouseDepotsRegularityError instanceof TooManyResourcesInADepot){
                sendMessage(new YouMustDiscardResources(),clientID);
                sendMessage(new ExceedingDepotMessage(activePlayer().getDashboardCopy()),clientID);
                nicknameToHisTurnPhase.replace(clientIDToNickname.get(clientID),4);
            }
            sendMessage(new NotNewResources(),clientID);
        }
    }


    public void papalInfo(Player activePlayer) {
        StringBuilder info= new StringBuilder("Here are some infos about the papal path in this exact  moment: \n");
        for (Player player: game.playersInGame()){
            if(player!=activePlayer){
                info.append(player.getNickname()).append(" is in position ").append(player.getFaithPosition());
                if(!player.isAtLeastAPapalCardActivated()) {
                    info.append(" and has activated papal favor card number ");
                    for(int i=0;i<player.numberOfActivatedPapalCards();i++)
                        info.append(i + 1).append(", ");
                    info.append(" \n");
                }
                else info.append(" and hasn't activated any papal favor card yet, \n");
            }
        }
        info.append("Your position is ").append(activePlayer.getFaithPosition());
        if(!activePlayer.isAtLeastAPapalCardActivated()) {
            info.append(" and you've activated papal favor card number ");
            for(int i=0;i<activePlayer.numberOfActivatedPapalCards();i++)
                info.append(i + 1).append(", ");
            info.append(" \n");
        }
        else info.append(" and you haven't activated any papal favor card yet, \n");
        info.append("The next papal favor card still to be activated by anyone is in position ").append(activePlayer.nextPapalCardToActivateInfo());
        sendMessageToActivePlayer(new PrintAString(info.toString()));
        sendMessageToActivePlayer(new PapalPathMessage(activePlayer.getPapalPath()));
    }

    public void surrend() {
        if(game.isSinglePlayer()) {
            try {
                game.playersInGame().get(0).moveForwardLorenzo(24);
            } catch (LorenzoWonTheMatch lorenzoWonTheMatch) {
                game.getPlayers().get(0).sendSocketMessage(new LorenzoWonMessage());
            } catch (LorenzoActivatesPapalCardException | BothPlayerAndLorenzoActivatePapalCardException ignored) {
            }
        }
    }

    public void playerAction(Action action, String nickname)  {
        if(!game.getActivePlayer().isClientRejoinedAfterInitializationPhase() && game.getActivePlayer().isClientDisconnectedDuringHisTurn()) {
            if(nicknameToHisTurnPhase.get(nickname)>2)turn.setActionPerformed(nicknameToHisTurnPhase.get(nickname));
            game.getActivePlayer().setClientDisconnectedDuringHisTurn(false);
        }
        Player player = game.playerIdentifiedByHisNickname(nickname);
        if (action instanceof DiscardLeaderCardsAction) game.playerIdentifiedByHisNickname(nickname).discardLeaderCards(((DiscardLeaderCardsAction) action).getIndexes());
        else if(action instanceof BonusResourcesAction) startingResources((BonusResourcesAction) action, player);
    }

    public void marketPreMove(int index,boolean isRow){
        if(turn.getActionPerformed()==0) {
            Player player = activePlayer();
            int numOfBlank = 0;
            try {
                numOfBlank = game.checkNumOfBlank(isRow, index);
            } catch (OutOfBoundException e) {
                e.printStackTrace();
            }
            marketAction(index, isRow);
            if (twoWhiteToColorCheck(player) && numOfBlank != 0) {
                ArrayList<LeaderCardMessage> messages=new ArrayList<>();
                int i=0;
                for(LeaderCard leaderCard: player.getLeaderCardsCopy()){
                    if(leaderCard.getLeaderPower().returnPowerType()== PowerType.WhiteToColor){
                        messages.add(new LeaderCardMessage(leaderCard,i));
                        i++;
                    }
                }
                WhiteToColorMessage message= new WhiteToColorMessage(numOfBlank, messages);
                sendMessageToActivePlayer(message);
                turn.setActionPerformed(5);
                nicknameToHisTurnPhase.replace(activePlayer().getNickname(),5);
            }
        }
        else if(actionPerformedOfActivePlayer()==3){
            sendMessageToActivePlayer(new YouMustDeleteADepotFirst());
        }
        else if(actionPerformedOfActivePlayer()==4){
            sendMessageToActivePlayer(new YouMustDiscardResourcesFirst());
        }
        else if(actionPerformedOfActivePlayer()==5){
            sendMessageToActivePlayer(new YouMustSelectWhiteToColorsFirst());
        }
        else sendMessageToActivePlayer(new MainActionAlreadyDoneMessage());
    }

    public int actionPerformedOfActivePlayer(){
        return turn.getActionPerformed();
    }

    public void updateValueOfActionPerformed(int newValue){
        turn.setActionPerformed(newValue);
    }

    public Player activePlayer(){
        return game.playerActive();
    }

    public int sizeOfLobby(){
        return clientsNicknames.size();
    }

    public void setBaseProd(){
        sendAll(new BaseProdParametersMessage(activePlayer().getDashboardCopy().getNumOfStandardProdRequirements(),activePlayer().getDashboardCopy().getNumOfStandardProdResults()));
    }

    public ArrayList<Integer> idsOfConnectedPlayers(){
        ArrayList<Integer> ids = new ArrayList<>();
        for (ServerSideSocket socket:clientsInGameConnections) {
            int id = socket.getClientID();
            ids.add(id);
        }
        return ids;
    }

    public int turnPhaseGivenNickname(int id){
        String nickname = clientIDToNickname.get(id);
        return nicknameToHisTurnPhase.get(nickname);
    }
}