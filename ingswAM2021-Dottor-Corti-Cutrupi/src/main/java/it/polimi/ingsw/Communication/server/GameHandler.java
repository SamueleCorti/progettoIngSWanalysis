package it.polimi.ingsw.Communication.server;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.Communication.client.actions.Action;
import it.polimi.ingsw.Communication.client.actions.InitializationActions.BonusResourcesAction;
import it.polimi.ingsw.Communication.client.actions.mainActions.DevelopmentAction;
import it.polimi.ingsw.Communication.client.actions.mainActions.DevelopmentFakeAction;
import it.polimi.ingsw.Communication.client.actions.mainActions.MarketAction;
import it.polimi.ingsw.Communication.client.actions.mainActions.WhiteToColorAction;
import it.polimi.ingsw.Communication.client.actions.mainActions.productionActions.BaseProductionAction;
import it.polimi.ingsw.Communication.client.actions.mainActions.productionActions.DevelopmentProductionAction;
import it.polimi.ingsw.Communication.client.actions.mainActions.productionActions.LeaderProductionAction;
import it.polimi.ingsw.Communication.client.actions.secondaryActions.*;
import it.polimi.ingsw.Communication.server.messages.*;
import it.polimi.ingsw.Communication.server.messages.ConnectionRelatedMessages.DisconnectionMessage;
import it.polimi.ingsw.Communication.server.messages.ConnectionRelatedMessages.RejoinAckMessage;
import it.polimi.ingsw.Communication.server.messages.GameCreationPhaseMessages.GameStartingMessage;
import it.polimi.ingsw.Communication.server.messages.InitializationMessages.GameInitializationFinishedMessage;
import it.polimi.ingsw.Communication.server.messages.InitializationMessages.InitializationMessage;
import it.polimi.ingsw.Communication.server.messages.InitializationMessages.OrderMessage;
import it.polimi.ingsw.Communication.server.messages.JsonMessages.DashboardMessage;
import it.polimi.ingsw.Communication.server.messages.JsonMessages.DevelopmentCardMessage;
import it.polimi.ingsw.Communication.server.messages.JsonMessages.GameBoardMessage;
import it.polimi.ingsw.Communication.server.messages.JsonMessages.LorenzoIlMagnificoMessage;
import it.polimi.ingsw.Communication.server.messages.Notificatios.MarketNotification;
import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Exceptions.WarehouseErrors.FourthDepotWarehouseError;
import it.polimi.ingsw.Exceptions.WarehouseErrors.TooManyResourcesInADepot;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.boardsAndPlayer.Player;
import it.polimi.ingsw.Model.developmentcard.DevelopmentCard;
import it.polimi.ingsw.Model.leadercard.LeaderCard;
import it.polimi.ingsw.Model.market.OutOfBoundException;
import it.polimi.ingsw.Model.requirements.Requirements;
import it.polimi.ingsw.Model.requirements.ResourcesRequirements;
import it.polimi.ingsw.Model.resource.*;
import it.polimi.ingsw.Exceptions.WarehouseErrors.WarehouseDepotsRegularityError;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class GameHandler {
    /** Server that contains this GameHandler */
    private final Server server;

    /** Unique game associated to this GameHandler. It contains the model that will be modified by the players connected */
    private Game game;

    /** Boolean is true if the game has started, false if it is still in lobby */
    private boolean isStarted;

    /** Contains the number of players allowed to play this game, decided by the game creator */
    private int totalPlayers;

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
        turn= new Turn();

        //we import the number of leaderCards for each player
        JsonReader reader1 = null;
        try {
            reader1 = new JsonReader(new FileReader("ingswAM2021-Dottor-Corti-Cutrupi/leadercardsparameters.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        JsonParser parser1 = new JsonParser();
        JsonArray favorArray = parser1.parse(reader1).getAsJsonArray();
        Gson gson1 = new Gson();
        int[] arr = gson1.fromJson(favorArray, int[].class);
        this.numOfLeaderCardsKept = arr[1];
        this.numOfLeaderCardsGiven = arr[0];
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
            for (int i = 3; i > 0; i--) {
                TimeUnit.MILLISECONDS.sleep(500);
                sendAll(new GenericMessage("Match starting in " + i));
            }
            sendAll(new GameStartingMessage());
            setup();
        }

        //room is not full yet, all the player are notified that there is one less empty spot in the room
        else {
            sendAll(new GenericMessage((totalPlayers - clientsInGameConnections.size()) + " slots left."));
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
            InitializationMessage messageToSend = new InitializationMessage(clientIDToConnection.get(id).getOrder(),
                    game.getGameBoard().getPlayerFromNickname(clientIDToNickname.get(id)).getLeaderCardZone().getLeaderCards(),this.numOfLeaderCardsKept,this.numOfLeaderCardsGiven);
            sendMessage(messageToSend, id);
        }

      //  sendAll(new OrderMessage(game));
        if(!isStarted) isStarted=true;
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
        clientIDToNickname.put(clientID,nickname);
        nicknameToClientID.put(nickname,clientID);
        clientIDToConnection.put(clientID,clientSingleConnection);

        //sending a message notifying that a new player has joined the lobby to all the players already in lobby
        sendAllExcept(new GenericMessage("Player "+ nickname+" joined the game"), clientID);
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

    public void printAllResources(Player player){
        String string="Here are all your resources: \n";
        string+="You have "+player.getDashboard().allAvailableResources(new CoinResource())+" coin; of those "+
                player.getDashboard().producedThisTurn(ResourceType.Coin)+ " have just been produced this turn\n";
        string+="You have "+player.getDashboard().allAvailableResources(new StoneResource())+" stone; of those "+
                player.getDashboard().producedThisTurn(ResourceType.Stone)+ " have just been produced this turn\n";
        string+="You have "+player.getDashboard().allAvailableResources(new ServantResource())+" servant; of those "+
                player.getDashboard().producedThisTurn(ResourceType.Servant)+ " have just been produced this turn\n";
        string+="You have "+player.getDashboard().allAvailableResources(new ShieldResource())+" shield; of those "+
                player.getDashboard().producedThisTurn(ResourceType.Shield)+ " have just been produced this turn";
        sendMessageToActivePlayer(new GenericMessage(string));
    }

    public Player[] playersOrderByFaithPosition(){
        Player[] temp = new Player[totalPlayers];
        for(int i=0; i<totalPlayers;i++)  temp[i]=game.getGameBoard().getPlayers().get(i);

        for(int i = 0; i < totalPlayers; i++) {
            boolean flag = false;
            for(int j = 0; j < totalPlayers-1; j++) {
                if(temp[j].getDashboard().getPapalPath().getFaithPosition()>temp[j+1].getDashboard().getPapalPath().getFaithPosition()) {
                    Player k = temp[j];
                    temp[j] = temp[j+1];
                    temp[j+1] = k;
                    flag=true;
                }
            }
            if(!flag) break;
        }
        return temp;
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
        if (game.getGameBoard().checkGameIsEnded()){
            System.out.println("the game reached its final stage (we're in gameboard)");
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
            sendAll(new GenericMessage(clientIDToNickname.get(clientsIDs.get(0)) + " is the new host."));
        }

        //the player was the active player
        if(connectionToRemove.equals(game.getActivePlayer())){
            turn.resetProductions();
            turn.setActionPerformed(0);
            checkGameEnded();
            game.nextTurnWhenActiveDisconnects();
            sendAllExcept(new GenericMessage("It's "+game.getActivePlayer().getNickname()+"'s turn"),id);
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

    public void sendMessageToActivePlayer(GenericMessage message){
        sendMessage(message,getGame().getActivePlayer().getClientID() );
    }

    public void endGame() {
        sendAll(new GenericMessage("Someone has fulfilled the conditions to end the game; the last round of turns will finish then we'll see who is the winner!"));
        game.setOrderOfEndingPLayer(game.getActivePlayer().getOrder());
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


        sendMessage(new RejoinAckMessage(nicknameToHisGamePhase.get(nickname)),newServerSideSocket.getClientID());

        int order= nicknameToOrder.get(nickname);
        newServerSideSocket.setOrder(order);

        //TODO: we will have to add the other messages for the next game phases
        switch (nicknameToHisGamePhase.get(nickname)){
            case 1:
                sendMessage(new GameStartingMessage(),newServerSideSocket.getClientID());
                sendMessage(new InitializationMessage(newServerSideSocket.getOrder(), game.getGameBoard().getPlayerFromNickname(nickname).getLeaderCardZone().getLeaderCards(),this.numOfLeaderCardsKept,this.numOfLeaderCardsGiven),
                        newServerSideSocket.getClientID());
                break;
            case 2: sendMessage(new GenericMessage("You were in effective game phase, you will be able to make your moves " +
                    "once it is your turn"),newServerSideSocket.getClientID());
                game.reconnectAPlayerThatWasInGamePhase();
                sendMessage(new GameStartingMessage(),newServerSideSocket.getClientID());
                game.reorderPlayersTurns();
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sendMessage(new GameInitializationFinishedMessage(),newServerSideSocket.getClientID());
                break;
            default: break;
        }

        sendAllExcept(new GenericMessage("Player "+nickname+" has reconnected to the game"),newServerSideSocket.getClientID());
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



    public void discardExtraResources(DiscardExcedingResourcesAction action, Player player) {
        for(ResourceType resourceType: action.getResources()){
            for(int i=1; i<= player.getDashboard().getWarehouse().sizeOfWarehouse();i++){
                if (player.getDashboard().getWarehouse().returnLengthOfDepot(i)>0 && player.getDashboard().getWarehouse().returnTypeofDepot(i).equals(resourceType)) {
                    try {
                        player.getDashboard().getWarehouse().removeResource(i);
                        sendAllExceptActivePlayer(new GenericMessage("As "+ game.getActivePlayer().getNickname()+ " discarded a resource, you'll now advance of one" +
                                "tile in the papal path"));
                        sendMessageToActivePlayer(new GenericMessage("All players will now advance of one tile in papal path, because you discarded a resource"));
                        moveForwardPapalPath(player);
                        sendMessageToActivePlayer(new GenericMessage("You successfully deleted a resource"));
                    } catch (WarehouseDepotsRegularityError warehouseDepotsRegularityError) {
                        sendMessageToActivePlayer(new GenericMessage("It was impossible to remove a "+ resourceType+ " resource. It's likely that" +
                                "it wasn't a resource just taken from market."));
                    }
                }
            }
        }
        try {
            player.getDashboard().getWarehouse().swapResources();
            sendMessageToActivePlayer(new GenericMessage("You successfully deleted the resources you chose, and there are no more problems with you depots, you can now go on\n"));
            printDepots(player);
            if(game.getActivePlayer().isClientDisconnectedDuringHisTurn()){
                turn.setActionPerformed(0);
                game.getActivePlayer().setClientDisconnectedDuringHisTurn(false);
            }
            else turn.setActionPerformed(1);
        } catch (WarehouseDepotsRegularityError warehouseDepotsRegularityError) {
            if(warehouseDepotsRegularityError instanceof FourthDepotWarehouseError){
                turn.setActionPerformed(3);
                sendMessage(new GenericMessage("There's a fourth depot in the warehouse, you must delete one")
                        ,nicknameToClientID.get(player.getNickname()));
                sendMessageToActivePlayer(new GenericMessage("To do so, you have to perform a delete depot action [e.g. deletedepot 4]"));
                printDepots(player);
            }
            else if(warehouseDepotsRegularityError instanceof TooManyResourcesInADepot){
                turn.setActionPerformed(4);
                sendMessage(new GenericMessage("There's an exceeding amount of resources in one depot of the warehouse," +
                        " you must delete resources to fix this problem"),nicknameToClientID.get(player.getNickname()));
                sendMessageToActivePlayer(new GenericMessage("To do so, you have to perform a discard resource action [e.g. discardresources coin stone]"));
                printDepots(player);
            }
        }
    }

    public void moveForwardPapalPath(Player activePlayer) {
        Player[] players = playersOrderByFaithPosition();
        for(int i=0; i<players.length;i++){
            if( players[i]!=activePlayer) {
                int cardActivated=players[i].getDashboard().getPapalPath().moveForward();
                sendMessage(new GenericMessage("Your faith position is "+players[i].getDashboard().getPapalPath().getFaithPosition()), nicknameToClientID.get(players[i].getNickname()));
                if(cardActivated!=-1)    {
                    int index=cardActivated+1;
                    sendAllExcept(new GenericMessage(players[i].getNickname()+" has just activated the papal card number "+ index),
                            getNicknameToClientID().get(players[i].getNickname()));
                    sendMessage(new GenericMessage("You just activated the papal favor card number: "+index), nicknameToClientID.get(players[i].getNickname()));
                    checkPapalCards(cardActivated, players[i]);
                }
            }
        }
    }

    public void moveForwardPapalPathActivePlayer(){
        Player player= game.getGameBoard().getPlayerFromNickname(game.getActivePlayer().getNickname());
        int cardActivated=player.getDashboard().getPapalPath().moveForward();
        sendMessageToActivePlayer(new GenericMessage("Your faith position is "+player.getDashboard().getPapalPath().getFaithPosition()));
        if(cardActivated!=-1)    {
            int index=cardActivated+1;
            sendAllExcept(new GenericMessage(player.getNickname()+" has just activated the papal card number "+ index),
                    getNicknameToClientID().get(player.getNickname()));
            sendMessage(new GenericMessage("You just activated the leader card number: "+index), nicknameToClientID.get(player.getNickname()));
            checkPapalCards(cardActivated, player);
        }
    }

    private void checkPapalCards(int cardActivated, Player playerThatActivatedThePapalCard) {
        int index;
        for(Player player: game.getGameBoard().getPlayers()){
            if  (player!=playerThatActivatedThePapalCard) {
                index=player.getDashboard().getPapalPath().checkPosition(cardActivated);
                if(index!=0){
                    sendMessage(new GenericMessage("You have activated papal favore card number "+index+" as well!"), getNicknameToClientID().get(player.getNickname()));
                }
                else sendMessage(new GenericMessage("Unfortunately you weren't far enough in the papal to activate it too"), getNicknameToClientID().get(player.getNickname()));
            }
        }
    }

    public void printDepots(Player player){
        String string="Here are your depots: \n";
        for(int i=1;i<=player.getDashboard().getWarehouse().sizeOfWarehouse();i++){
            string+= i +": ";
            for(int j=0; j<player.getDashboard().getWarehouse().returnLengthOfDepot(i);j++){
                string+="\t"+player.getDashboard().getWarehouse().returnTypeofDepot(i);
            }
            string+="\n";
        }
        if( player.getDashboard().getExtraDepots().size()!=0){
            string+= "You also have the following extra depots: \n";
            for(int i=0; i<player.getDashboard().getExtraDepots().size(); i++){
                for(int j=0; j<player.getDashboard().getExtraDepots().get(i).getAllResources().size();j++)
                    string+= "\t"+player.getDashboard().getExtraDepots().get(i).getAllResources().get(i).getResourceType();
                string+="\n";
            }
        }
        sendMessageToActivePlayer(new GenericMessage(string));
    }

    public void printMarket(){
        String string= "Here's the market:\n";
        for(int row=0; row<3; row++){
            for(int column=0;column<4;column++){
                string+=game.getGameBoard().getMarket().getSingleResource(row,column).getResourceType()+"\t";
            }
            string+="\n";
        }
        string+="\t\t\t\t\t\t\t\t"+game.getGameBoard().getMarket().getFloatingMarble().getResourceType();
        sendMessageToActivePlayer(new GenericMessage(string));
    }

    public void printPapalPosition(Player player){
        sendMessageToActivePlayer(new GenericMessage("Your papal position is: "+ player.getDashboard().getPapalPath().getFaithPosition()));
    }

    /**
     * Method used to get resources from market. Sets actionPerformed in turn to 1 if all goes well.
     * @param action: see {@link MarketAction}
     */
    public void marketAction(MarketAction action, String nickname){
        Player player = game.getGameBoard().getPlayerFromNickname(nickname);
        try {
            player.getResourcesFromMarket(getGame().getGameBoard(), action.isRow(), action.getIndex());
            sendMessageToActivePlayer(new GenericMessage("You've successfully performed you action"));
            printDepots(player);
            sendMessageToActivePlayer(new GenericMessage("Your faith position is "+player.getDashboard().getPapalPath().getFaithPosition()));
            turn.setActionPerformed(1);
        } catch (OutOfBoundException e) {
            e.printStackTrace();
        } catch (WarehouseDepotsRegularityError e){
            //TODO: server must wait for the user to delete exceeding depot/resources to end his turn
            if(e instanceof FourthDepotWarehouseError){
                turn.setActionPerformed(3);
                sendMessage(new GenericMessage("There's a fourth depot in the warehouse, you must delete one")
                        ,nicknameToClientID.get(nickname));
                sendMessageToActivePlayer(new GenericMessage("To do so, you have to perform a delete depot action [e.g. deletedepot 4]"));
                printDepots(player);
            }
            else if(e instanceof TooManyResourcesInADepot){
                turn.setActionPerformed(4);
                sendMessage(new GenericMessage("There's an exceeding amount of resources in one depot of the warehouse," +
                                " you must delete resources to fix this problem"),nicknameToClientID.get(nickname));
                sendMessageToActivePlayer(new GenericMessage("To do so, you have to perform a discard resource action [e.g. discardresources coin stone]"));
                printDepots(player);
            }
        }
        sendAllExceptActivePlayer(new MarketNotification(action.getIndex(), action.isRow(),player.getNickname()));
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sendAllExceptActivePlayer(new GenericMessage(game.getGameBoard().getMarket().getStringMarket()));
    }

    /**
     * Method used to buy the player the requested development card. Sets actionPerformed in turn to 1 if all goes well.
     * @param action: see {@link DevelopmentAction}
     */
    public boolean developmentAction (DevelopmentAction action, Player player){
        try {
            player.buyDevelopmentCard(action.getColor(), action.getCardLevel(), action.getIndex(), this.game.getGameBoard());
            turn.setActionPerformed(1);
            sendMessage(new GenericMessage("you've correctly bought the card!"),game.getActivePlayer().getClientID());
            sendAll(new GenericMessage("the new development card on top of that deck is"));
            sendAll(new DevelopmentCardMessage(this.game.getGameBoard().getDeckOfChoice(action.getColor(), action.getCardLevel()).getFirstCard()));
            return true;
        } catch (NotCoherentLevelException e) {
            sendMessage(new GenericMessage("You cant buy a card of that level in that developmentCardZone"),game.getActivePlayer().getClientID());
        }
        catch(NotEnoughResourcesException e){
            sendMessage(new GenericMessage("you dont have enough resources to buy the card"),game.getActivePlayer().getClientID());
        }
        return false;
    }

    public boolean developmentFakeAction (DevelopmentFakeAction action, Player player){
        try {
            player.buyDevelopmentCardFake(action.getColor(), action.getCardLevel(), action.getIndex(), this.game.getGameBoard());
            sendMessage(new GenericMessage("you've correctly bought the card!"),game.getActivePlayer().getClientID());
            sendAll(new GenericMessage("the new development card on top of that deck is"));
            sendAll(new DevelopmentCardMessage(this.game.getGameBoard().getDeckOfChoice(action.getColor(), action.getCardLevel()).getFirstCard()));
            return true;
        } catch (NotCoherentLevelException e) {
            sendMessage(new GenericMessage("You cant buy a card of that level in that developmentCardZone"),game.getActivePlayer().getClientID());
        }
        catch(NotEnoughResourcesException e){
            sendMessage(new GenericMessage("you dont have enough resources to buy the card"),game.getActivePlayer().getClientID());
        }
        return false;
    }

    /**
     *
     */
    public void productionAction(Action action, String nickname){
        Player player = game.getGameBoard().getPlayerFromNickname(nickname);
        boolean productionMade = false;
        boolean[] productions= turn.getProductions();


        if (action instanceof BaseProductionAction) {

            //CORRECT PATH: USER DIDN'T ACTIVATE BASE PRODUCTION IN THIS TURN
            if(!productions[0]){
                if (baseProduction((BaseProductionAction) action, nickname)) {
                    productionMade=true;
                    sendMessage(new GenericMessage("Base production activated successfully")
                            , nicknameToClientID.get(nickname));
                }
            }

            //WRONG PATH: USER ALREADY ACTIVATED BASE PRODUCTION IN THIS TURN
            else {
                sendMessage(new GenericMessage("You already used base production in this turn, please try something else")
                        ,nicknameToClientID.get(nickname));
            }
        }

        else if (action instanceof LeaderProductionAction){
            int leaderCardZoneIndex= ((LeaderProductionAction) action).getLeaderCardZoneIndex();

            //CORRECT PATH: USER DIDN'T ACTIVATE THE LEADER CARD PRODUCTION OF THE SELECTED CARD IN THIS TURN
            if (!productions[leaderCardZoneIndex] && game.getGameBoard().getPlayerFromNickname(nickname)
                    .getLeaderCardZone().getLeaderCards().size()>leaderCardZoneIndex){
                if(leaderProduction((LeaderProductionAction) action, nickname)) {
                    productionMade=true;
                    sendMessage(new GenericMessage("Production from leader card n. "+leaderCardZoneIndex+
                            " has been made successfully"), nicknameToClientID.get(nickname));
                }
            }

            //WRONG PATH: USER ASKED FOR A PRODUCTION HE ALREADY ACTIVATED IN THIS TURN
            else if(productions[leaderCardZoneIndex])sendMessage(new GenericMessage("You already activated this production in this turn"),
                    nicknameToClientID.get(nickname));
            else {
                sendMessage(new GenericMessage("There's no leader card at the index you selected"),nicknameToClientID.get(nickname));
            }
        }

        else if (action instanceof DevelopmentProductionAction){
            int devCardZoneIndex= ((DevelopmentProductionAction) action).getDevelopmentCardZone();

            //CORRECT PATH: USER ASKED FOR A PRODUCTION HE DIDN'T ACTIVATE IN THIS TURN YET
            if (!productions[devCardZoneIndex + 2] && game.getGameBoard().getPlayerFromNickname(nickname).getDashboard()
            .getDevelopmentCardZones().get(devCardZoneIndex).getLastCard()!=null){

                //CORRECT PATH: USER HAS GOT ENOUGH RESOURCES TO ACTIVATE THE PRODUCTION
                if(devCardProduction(devCardZoneIndex, player)) {
                    productionMade=true;
                    int index=devCardZoneIndex+1;
                    sendMessage(new GenericMessage("Production from development zone "+index+
                                    " has been made successfully"), nicknameToClientID.get(nickname));
                }

                //WRONG PATH: USER HASN'T GOT ENOUGH RESOURCES TO ACTIVATE THE PRODUCTION
                else sendMessage(new GenericMessage("You don't have enough resources to activate this production"),
                        nicknameToClientID.get(nickname));
            }

            //WRONG PATH: USER ALREADY ACTIVATED THIS DEVELOPMENT CARD PRODUCTION IN THIS TURN
            else if(productions[devCardZoneIndex + 2]){
                sendMessage(new GenericMessage("You already activated this production in this turn"),
                        nicknameToClientID.get(nickname));
            }
            else sendMessage(new GenericMessage("There is no card activable in the selected dev zone"),
                        nicknameToClientID.get(nickname));
        }

        //IF THE PRODUCTION HAS BEEN ACTIVATED WITHOUT ERRORS, SERVER SENDS CLIENT AN TEMPORARY VERSION OF THE DEPOTS
        //AND OF THE RESOURCES PRODUCED
        if(productionMade){
            sendMessage(new GenericMessage("Resources available for more productions: "
                    +parseListOfResources(player.getDashboard().getResourcesUsableForProd())),nicknameToClientID.get(nickname));
            sendMessage(new GenericMessage("Resources produced: "
                    +parseListOfResources(player.getDashboard().getResourcesProduced())),nicknameToClientID.get(nickname));
            turn.setActionPerformed(2);
        }
    }

    /**
     * Called when the client selects a BaseProductionAction
     * @param action: see {@link BaseProductionAction}
     * @return  true if the action has been performed correctly, false otherwise
     */
    public boolean baseProduction(BaseProductionAction action,String nickname){
        ArrayList<Resource> used = new ArrayList<>();
        for(ResourceType resourceEnum: action.getResourcesToUse()){
            used.add(parseResourceFromEnum(resourceEnum));
        }
        ArrayList<Resource> created = new ArrayList<>();
        for(ResourceType resourceEnum: action.getResourcesWanted()){
            created.add(parseResourceFromEnum(resourceEnum));
        }
        int resultOfActivation = game.getGameBoard().getPlayerFromNickname(nickname).activateBaseProduction(used, created);
        switch (resultOfActivation){
            case 0: //CASE ACTIVATE WORKED PERFECTLY
                return true;
            case 1: //CASE PLAYER DIDN'T HAVE ENOUGH RESOURCES TO ACTIVATE PROD
                sendMessage(new GenericMessage("You don't have enough of the selected resources to activate the base prod. "
                +"Please try using different resources or try activating another type of production"),nicknameToClientID.get(nickname));
                return false;
            case 2: //CASE PLAYER DIDN'T SELECT A CORRECT AMOUNT OF RESOURCES
                sendMessage(new GenericMessage("You insert an incorrect amount of resources, you must select "
                +game.getGameBoard().getPlayerFromNickname(nickname).getDashboard().getNumOfStandardProdRequirements()+
                        " resources to use and "+game.getGameBoard().getPlayerFromNickname(nickname).getDashboard()
                        .getNumOfStandardProdResults()+" resources to produce!"),nicknameToClientID.get(nickname));
                return false;
            default: return false;
        }
    }

    /**
     * Called when the client selects a LeaderProductionAction
     * @param action: see {@link LeaderProductionAction}
     * @return  true if the action has been performed correctly, false otherwise
     */
    public boolean leaderProduction(LeaderProductionAction action, String nickname){
        Player player = game.getGameBoard().getPlayerFromNickname(nickname);
        Resource resourceWanted = parseResourceFromEnum(action.getResourceWanted());
        int index= action.getLeaderCardZoneIndex();
        try {
            player.checkLeaderProduction(index);
            player.getDashboard().leaderProd(index,resourceWanted);
            turn.setProductionPerformed(index);
            return true;
        } catch (LeaderCardNotActiveException e) {
            sendMessage(new GenericMessage("The card you selected is not active")
                    ,nicknameToClientID.get(nickname));
            return false;
        } catch (WrongTypeOfLeaderPowerException e) {
            sendMessage(new GenericMessage("The card you selected is not a production card, please try again")
                    ,nicknameToClientID.get(nickname));
            return false;
        } catch (NotEnoughResourcesToActivateProductionException e) {
            sendMessage(new GenericMessage("You don't have enough resources to activate this production")
                    ,nicknameToClientID.get(nickname));
            return false;
        }
    }

    /**
     * Called when the client selects a DevelopmentProductionAction
     * @param index: represents the development card zone that contains the wanted production
     * @return  true if the action has been performed correctly, false otherwise
     */
    public boolean devCardProduction(int index, Player player){
        int numOfFaithProduced;
        try {
            numOfFaithProduced=player.activateDevelopmentProduction(index);
            for(int i=0;i<numOfFaithProduced;i++) moveForwardPapalPathActivePlayer();
            turn.setProductionPerformed(2+index);
        } catch (NotEnoughResourcesToActivateProductionException e) {
            return false;
        }
        return true;
    }

    /**
     * Used when the player wants to activate a leader card. This method can get called anytime during the turn, before or after doing a main action, and doesn't
     * influence in any way the player's ability to perform any other action.
     * @param action: see {@link ActivateLeaderCardAction}
     */
    public void activateLeaderCard(Action action, Player player){
        int index= ((ActivateLeaderCardAction) action).getIndex();
        try {
            player.activateLeaderCard(index);
        } catch (NotInactiveException e) {
            game.getActivePlayer().sendSocketMessage(new GenericMessage("The leader card you selected is already active!"));
        } catch (RequirementsUnfulfilledException e) {
            game.getActivePlayer().sendSocketMessage(new GenericMessage("You dont have the requirements to activate this leader card"));
        }
    }

    /**
     * Used when the player wants to take at a dashboard. This method can get called anytime during the turn, before or after doing a main action, and doesn't
     * influence in any way the player's ability to perform any other action.
     * @param action: see {@link ActivateLeaderCardAction}
     */
    public void viewDashboard(ViewDashboardAction action){
        int order= action.getPlayerOrder();
        Player player= game.getGameBoard().getPlayers().get(order-1);
        if(order<1 || order>totalPlayers){
            game.getActivePlayer().sendSocketMessage(new GenericMessage("There's no player associated to the index you insert"));
        }
        /*else {
            Message dashboardAnswer = new DashboardMessage(game.getGameBoard().getPlayerFromNickname(orderToNickname.get(order)).getDashboard());
            game.getActivePlayer().sendSocketMessage(dashboardAnswer);
        }*/
        printDepots(player);
        printStrongbox(player);
        printPapalPath(player);
        printDevCards(player);
        printLeaderCards(player);
        System.out.println("we've sent the dashboard back to the client");
    }

    private void printDevCards(Player player) {
        DevelopmentCard card;
        String string="Here are you development cards: \n";
        for(int i=0; i<3;i++){
            if(player.getDashboard().getDevelopmentCardZones().get(i).getLastCard()!=null){
                card=player.getDashboard().getDevelopmentCardZones().get(i).getLastCard();
                int index=i+1;
                string+="Card on leader zone "+index+" : \n";
                string+="Color: "+ card.getCardStats().getValue1()+"\tlevel: "+card.getCardStats().getValue0()+" \tvictory points: "+card.getVictoryPoints();
                string+="Production cost: \n";
                for(ResourcesRequirements resourcesRequirements: player.getDashboard().getDevelopmentCardZones().get(i).getLastCard().getProdRequirements()){
                    string+= resourcesRequirements.getResourcesRequired().getValue0()+" "+ resourcesRequirements.getResourcesRequired().getValue1()+"s\t";
                }
                string+="\n";
                string+="Resources produced: \n";
                for(Resource resource: player.getDashboard().getDevelopmentCardZones().get(i).getLastCard().getProdResults())
                    string+= resource;
            }
        }
        sendMessageToActivePlayer(new GenericMessage(string));
    }

    public void printLeaderCards(Player player){
        LeaderCard card;
        String string="Here are you development cards: \n";
        for(int i=0;i<numOfLeaderCardsKept;i++){
            if(player.getLeaderCard(i)!=null){
                card= player.getLeaderCard(i);
                string+="Leader card numer "+i+":\n";
                string+="Type of power : "+card.getLeaderPower().returnPowerType()+"\n";
                string+="Activation requirements: ";
                for(Requirements requirements: card.getCardRequirements()){
                    string+=requirements;
                }
                string+="Victory points "+card.getVictoryPoints()+":\n";
                string+="\nThis card is currently "+ card.getCondition()+"\n";
            }
        }
        sendMessageToActivePlayer(new GenericMessage(string));
    }

    public void printPapalPath(Player player){
        String string="Here's your papal path:  (x=papal card zone, X=papal card, o=your position normally, O=your position when you're on a papal path card (or zone))\n ";
        string+="|";
        for(int i=0;i<=24;i++){
            if(player.getDashboard().getPapalPath().getFaithPosition()!=i){
                if(player.getDashboard().getPapalPath().getPapalTiles().get(i).isPopeSpace()) string+="X|";
                else if(player.getDashboard().getPapalPath().getPapalTiles().get(i).getNumOfReportSection()!=0) string+="x|";
                else string+=" |";
            }
            else if(player.getDashboard().getPapalPath().getPapalTiles().get(i).isPopeSpace()) string+="O|";
            else if(player.getDashboard().getPapalPath().getPapalTiles().get(i).getNumOfReportSection()!=0) string+="O|";
            else string+="o|";
        }
        sendMessageToActivePlayer(new GenericMessage(string));
    }

    public void printStrongbox(Player player){
        int i=0;
        String string="Here is your strongbox: \n";
        for(Resource resource : player.getDashboard().getStrongbox().getAllResources()){
            i++;
            if(i%5==0) string+="\n";
            string+= resource +"\t";
        }
        sendMessageToActivePlayer(new GenericMessage(string));
    }

    public void viewGameBoard() {
        System.out.println("we've received a gameBoard request");
        Message gameBoardAnswer = new GameBoardMessage(game.getGameBoard());
        System.out.println("we've created a gameBoard answer");
        game.getActivePlayer().sendSocketMessage(gameBoardAnswer);
        System.out.println("we've sent it to client");
    }

    public void viewLorenzo(Action action) {
        if(clientsIDs.size()==1) {
            System.out.println("we've received a ViewLorenzo request");
            Message lorenzoAnswer = new LorenzoIlMagnificoMessage(game.getGameBoard().getLorenzoIlMagnifico());
            game.getActivePlayer().sendSocketMessage(lorenzoAnswer);
            System.out.println("we've sent it to client");
        }else{
            sendMessageToActivePlayer(new GenericMessage("We cant show Lorenzo, because this is not a single player game!"));
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
        turn.resetProductions();
        turn.setActionPerformed(0);
        checkGameEnded();
        game.nextTurn();
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
            sendAll(new GenericMessage("It's "+game.getActivePlayer().getNickname()+"'s turn"));
        }
    }

    public void marketSpecialAction(WhiteToColorAction message, Player player){
        for(int i=0;i<message.getResourceTypes().size();i++){
            player.getDashboard().getWarehouse().addResource(parseResourceFromEnum(message.getResourceTypes().get(i)));}
    }

    public void startingResources(BonusResourcesAction action, Player player){
        ResourceType resourceType= action.getResourceType1();
        if(action.getResourceType1()!=null) player.getDashboard().getWarehouse().addResource(parseResourceFromEnum(action.getResourceType1()));
        if(action.getResourceType2()!=null) player.getDashboard().getWarehouse().addResource(parseResourceFromEnum(action.getResourceType2()));
        try {
            game.getGameBoard().getPlayerFromNickname(player.getNickname()).getDashboard().getWarehouse().swapResources();
        } catch (WarehouseDepotsRegularityError warehouseDepotsRegularityError) {
            warehouseDepotsRegularityError.printStackTrace();
        }
    }

    public boolean twoWhiteToColorCheck(Player player) {
        return player.getDashboard().getWhiteToColorResources() != null && player.getDashboard().getWhiteToColorResources().size() == 2;
    }

    public void test(Player player) {
        /*for (LeaderCard card:player.getLeaderCardZone().getLeaderCards()) {
            card.activateCardPower(player.getDashboard());
        }
        if(player.getDashboard().getWhiteToColorResources()!=null && player.getDashboard().getWhiteToColorResources().size()==2) System.out.println("Activated 2 wtc leaders");
        if(player.getDashboard().getResourcesForExtraProd()!=null && player.getDashboard().getResourcesForExtraProd().size()==2) System.out.println("Activated 2 extraProd leaders");
        if(player.getDashboard().getDiscountedResources()!=null && player.getDashboard().getDiscountedResources().size()==2) System.out.println("Activated 2 discount leaders");
        if(player.getDashboard().getExtraDepots()!=null && player.getDashboard().getExtraDepots().size()==2) System.out.println("Activated 2 depot leaders");
    */
        for(int i=0; i<5; i++){
            moveForwardPapalPathActivePlayer();
        }
    }

    public void addInfiniteResources() {
        System.out.println("giving the current player infinite resources");
        for(int i=0;i<5;i++){
            game.getGameBoard().getPlayerFromNickname(game.getActivePlayer().getNickname()).getDashboard().getStrongbox().addResource(new CoinResource());
            game.getGameBoard().getPlayerFromNickname(game.getActivePlayer().getNickname()).getDashboard().getStrongbox().addResource(new StoneResource());
            game.getGameBoard().getPlayerFromNickname(game.getActivePlayer().getNickname()).getDashboard().getStrongbox().addResource(new ShieldResource());
            game.getGameBoard().getPlayerFromNickname(game.getActivePlayer().getNickname()).getDashboard().getStrongbox().addResource(new ServantResource());
        }
        //System.out.println("we're going to move papalpath forward for 24 moves");
        //game.getGameBoard().getPlayerFromNickname(game.getActivePlayer().getNickname()).getDashboard().getPapalPath().moveForward(24);
        //System.out.println("we did it");
    }

    public void discardLeaderCard(DiscardLeaderCard action, String nickname) {
        Player player = game.getGameBoard().getPlayerFromNickname(nickname);
        int index = action.getIndex();
        if(player.getLeaderCardZone().getLeaderCards()==null || player.getLeaderCardZone().getLeaderCards().size()<index+1){
            sendMessage(new GenericMessage("There's no card at the index you inserted"),nicknameToClientID.get(nickname));
        }
        else {
            player.getLeaderCardZone().getLeaderCards().remove(index);
            moveForwardPapalPathActivePlayer();
            sendMessage(new GenericMessage("You have successfully removed card at index "+index),nicknameToClientID.get(nickname));
            if(index==0 && player.getLeaderCardZone().getLeaderCards().size()>0){
                sendMessage(new GenericMessage("Now card at index 0 is the card that previously was at index 1"),nicknameToClientID.get(nickname));
            }
        }
    }


    public void discardDepot(DiscardExcedingDepotAction action, Player player) {
        try {
            int removedSize=player.getDashboard().getWarehouse().removeExceedingDepot(action.getIndex());
            for(int i=0; i<removedSize;i++) {
                sendAllExceptActivePlayer(new GenericMessage("As "+ game.getActivePlayer().getNickname()+ " discarded a resource, you'll now advance of one" +
                        "tile in the papal path"));
                sendMessageToActivePlayer(new GenericMessage("All players will now advance of one tile in papal path, because you discarded a resource"));
                moveForwardPapalPath(player);
            }
            printDepots(player);
            player.getDashboard().getWarehouse().swapResources();
            sendMessageToActivePlayer(new GenericMessage("Depot deletion was successful, and there are no more problems with you depots, you can now go on"));
            if(game.getActivePlayer().isClientDisconnectedDuringHisTurn()){
                turn.setActionPerformed(0);
                game.getActivePlayer().setClientDisconnectedDuringHisTurn(false);
            }
            else turn.setActionPerformed(1);
        } catch (WarehouseDepotsRegularityError warehouseDepotsRegularityError) {
            printDepots(player);
            if(warehouseDepotsRegularityError instanceof TooManyResourcesInADepot){
                sendMessageToActivePlayer(new GenericMessage("You now have to discard resources (discardresources coin stone)"));
                turn.setActionPerformed(4);
            }
            else     sendMessageToActivePlayer(new GenericMessage("There was a problem, you tried to eliminate a depot with resources not just taken from market"));
        }
    }


    public void papalInfo(Player activePlayer) {
        String info= "Here are some infos about the papal path in this exact  moment: \n";
        for (Player player: game.getGameBoard().getPlayers()){
            if(player!=activePlayer){
                info+= player.getNickname()+ " is in position "+ player.getDashboard().getPapalPath().getFaithPosition();
                if(player.getDashboard().getPapalPath().cardsActivated()!=null) {
                    info+= " and has activated papal favor card number ";
                    for(int i=0;i<player.getDashboard().getPapalPath().cardsActivated().size();i++)
                        info+= (i+1) + ", ";
                    info+= " \n";
                }
                else info+= " and hasn't activated any papal favor card yet, \n";
            }
        }
        info+="Your position is "+activePlayer.getDashboard().getPapalPath().getFaithPosition();
        if(activePlayer.getDashboard().getPapalPath().cardsActivated()!=null) {
            info+= " and you've activated papal favor card number ";
            for(int i=0;i<activePlayer.getDashboard().getPapalPath().cardsActivated().size();i++)
                info+= (i+1) + ", ";
            info+= " \n";
        }
        else info+= " and you haven't activated any papal favor card yet, \n";
        info+= "The next papal favor card still to be activated by anyone is in position "+ activePlayer.getDashboard().getPapalPath().getNextCardToActivatePosition();
        sendMessageToActivePlayer(new GenericMessage(info));
    }
}
