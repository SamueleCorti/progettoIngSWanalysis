package it.polimi.ingsw.Communication.server;

import it.polimi.ingsw.Communication.client.actions.Action;
import it.polimi.ingsw.Communication.client.actions.BonusResourcesAction;
import it.polimi.ingsw.Communication.client.actions.DiscardTwoLeaderCardsAction;
import it.polimi.ingsw.Communication.client.actions.TestAction;
import it.polimi.ingsw.Communication.client.actions.mainActions.WhiteToColorAction;
import it.polimi.ingsw.Communication.client.actions.mainActions.DevelopmentAction;
import it.polimi.ingsw.Communication.client.actions.mainActions.MarketAction;
import it.polimi.ingsw.Communication.client.actions.mainActions.productionActions.BaseProductionAction;
import it.polimi.ingsw.Communication.client.actions.mainActions.productionActions.DevelopmentProductionAction;
import it.polimi.ingsw.Communication.client.actions.mainActions.productionActions.LeaderProductionAction;
import it.polimi.ingsw.Communication.client.actions.secondaryActions.ActivateLeaderCardAction;
import it.polimi.ingsw.Communication.client.actions.secondaryActions.DiscardLeaderCard;
import it.polimi.ingsw.Communication.server.messages.*;
import it.polimi.ingsw.Communication.server.messages.ConnectionRelatedMessages.DisconnectionMessage;
import it.polimi.ingsw.Communication.server.messages.ConnectionRelatedMessages.RejoinAckMessage;
import it.polimi.ingsw.Communication.server.messages.GameCreationPhaseMessages.GameStartingMessage;
import it.polimi.ingsw.Model.boardsAndPlayer.Dashboard;
import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.boardsAndPlayer.Player;
import it.polimi.ingsw.Model.market.OutOfBoundException;
import it.polimi.ingsw.Model.resource.*;
import it.polimi.ingsw.Model.storing.RegularityError;

import java.io.IOException;
import java.util.ArrayList;
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

    /**
     * This hashmap permits identifying the gamePhase of a player relying on his nickname
     * If the player related to that nickname is disconnected, the value is null
     */
    private final Map<String,Integer> nicknameToHisGamePhase;

    private final Map<String,Integer> nicknameToOrder;

    private int gamePhase=0;

    private int numOfInitializedClients=0;

    private Turn turn;



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
        gameID = generateNewGameID();
        turn= new Turn();
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
     * Methos used to send a message to all the clients connected to the game
     * @param message contains the message to send
     */
    public void sendAll(Message message){
        for(int clientId: clientsIDs){
            sendMessage(message,clientId);
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
     * Method setPlayersNumber sets the active players number of the match, decided by the first connected player.
     *
     * @param playersNumber of type int - the number of the playing clients.
     *
     */
    public void setPlayersNumber(int playersNumber) {
        this.totalPlayers = playersNumber;
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
                    game.getGameBoard().getPlayerFromNickname(clientIDToNickname.get(id)).getLeaderCardZone().getLeaderCards());
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


    /**
     * Method unregisterPlayer unregisters a player identified by his unique ID after a disconnection event or message.
     * @param id is the ID of the player to disconnect
     */
    public void unregisterPlayer(int id) {

        //All the lists and maps are updated removing the client who disconnected

        removeID(id);

        //If the room is empty, game ends
        if(clientsIDs.size()==0){
            System.out.println("Not anymore players");
            removeGameHandler();
        }

        sendAll(new DisconnectionMessage(clientIDToNickname.get(id)));
        nicknameToClientID.replace(clientIDToNickname.get(id),null);

        if(gamePhase==1 && nicknameToHisGamePhase.get(clientIDToNickname.get(id))==2){
            numOfInitializedClients--;
        }

        clientIDToNickname.remove(id);
        removeConnection(clientIDToConnection.get(id));
        clientIDToNickname.remove(id);

        ServerSideSocket connectionToRemove = clientIDToConnection.get(id);

        //If the player was the host, another player is set as new host.
        if(connectionToRemove.isHost()){
            setHost(clientIDToConnection.get(clientsIDs.get(0)));
            hostConnection.setHost(true);
            sendAll(new GenericMessage(clientIDToNickname.get(clientsIDs.get(0)) + " is the new host."));
        }

        //the player was the active player
        if(connectionToRemove.equals(game.getActivePlayer())){
            game.nextTurn();
        }

        game.removeConnection(connectionToRemove);
        clientIDToConnection.remove(id);
    }

    private void removeGameHandler() {
    }

    public void endGame() {
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
                sendMessage(new InitializationMessage(newServerSideSocket.getOrder(), game.getGameBoard().getPlayerFromNickname(nickname).getLeaderCardZone().getLeaderCards()),
                        newServerSideSocket.getClientID());
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

    /**
     * Method used to get resources from market. Sets actionPerformed in turn to 1 if all goes well.
     * @param message: see {@link MarketAction}
     */
    public void marketAction(MarketAction message, Player player){
        try {
            player.getResourcesFromMarket(getGame().getGameBoard(), message.isRow(), message.getIndex());
            turn.setActionPerformed(1);
        } catch (OutOfBoundException | RegularityError e) {
            e.printStackTrace();
        }
    }

    public boolean twoWhiteToColorCheck(Player player){
        if (player.getDashboard().getWhiteToColorResources().size()==2) {
            return true;
        }
        return false;
    }


    /**
     * Method used to get resources from market when the player has two WhiteToColor leader cards active . Sets actionPerformed in turn to 1 if all goes well.
     * @param message: see {@link WhiteToColorAction}
     */
    public void marketSpecialAction(WhiteToColorAction message, Player player){
        for(int i=0;i<message.getResourceTypes().size();i++){
            player.getDashboard().getWarehouse().addResource(parseResourceFromEnum(message.getResourceTypes().get(i)));
        }
    }

    /**
     * Method used to buy the player the requested development card. Sets actionPerformed in turn to 1 if all goes well.
     * @param message: see {@link DevelopmentAction}
     */
    public void developmentAction (DevelopmentAction message, Player player){
        try {
            player.buyDevelopmentCard(message.getColor(), message.getCardLevel(), message.getIndex(), this.game.getGameBoard());
            turn.setActionPerformed(1);
        } catch (NotCoherentLevelException | NotEnoughResourcesException | RegularityError | NotEnoughResourcesToActivateProductionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when the client sends a production action message. This method itself can call {@link #baseProduction(BaseProductionAction, Player)}, {@link #leaderProduction(LeaderProductionAction, Player)},
     * {@link #devCardProduction(int, Player)} depending on the Action type of message the controller receives
     * @param action
     */

    public void productionAction(Action action, Player player){
        boolean[] productions= turn.getProductions();
        if (action instanceof BaseProductionAction && !productions[0]) {
            if (baseProduction((BaseProductionAction) action, player)) turn.setActionPerformed(2);
        }
        if (action instanceof LeaderProductionAction){
            int leaderCardZoneIndex= ((LeaderProductionAction) action).getLeaderCardZoneIndex();
            if (!productions[leaderCardZoneIndex]){
                if(leaderProduction((LeaderProductionAction) action, player)) turn.setActionPerformed(2);
            }
        }
        if (action instanceof DevelopmentProductionAction){
            int devCardZoneIndex= ((DevelopmentProductionAction) action).getDevelopmentCardZone();
            if (!productions[devCardZoneIndex + 2]){
                if(devCardProduction(devCardZoneIndex, player)) turn.setActionPerformed(2);
            }
        }
        return;
    }

    /**
     * Called when the client selects a BaseProductionAction
     * @param action: see {@link BaseProductionAction}
     * @return  true if the action has been performed correctly, false otherwise
     */
    public boolean baseProduction(BaseProductionAction action, Player player){
        ArrayList<Resource> used = new ArrayList<Resource>();
        for(ResourceType resourceEnum: action.getResourcesUsed()){
            used.add(parseResourceFromEnum(resourceEnum));
        }
        ArrayList<Resource> created = new ArrayList<Resource>();
        for(ResourceType resourceEnum: action.getResourcesWanted()){
            created.add(parseResourceFromEnum(resourceEnum));
        }
        if (player.activateStandardProduction(used, created)) {
            turn.setProductionPerformed(0);
            return true;
        }
        //TODO: else notifies the client that something went wrong
        return false;
    }

    /**
     * Called when the client selects a LeaderProductionAction
     * @param action: see {@link LeaderProductionAction}
     * @return  true if the action has been performed correctly, false otherwise
     */
    public boolean leaderProduction(LeaderProductionAction action, Player player){
        Resource resourceWanted = parseResourceFromEnum(action.getResourcesWanted());
        int index= action.getLeaderCardZoneIndex();
        if (player.getDashboard().leaderProd(action.getLeaderCardZoneIndex(),resourceWanted)){
            turn.setProductionPerformed(index+1);
            return true;
        }
        //TODO: else notifies the client that something went wrong
        return false;
    }

    /**
     * Called when the client selects a DevelopmentProductionAction
     * @param index: represents the development card zone that contains the wanted production
     * @return  true if the action has been performed correctly, false otherwise
     */
    public boolean devCardProduction(int index, Player player){
        try {
            player.activateDevelopmentProduction(index);
            turn.setProductionPerformed(2+index);
            return true;
        } catch (RegularityError regularityError) {
            regularityError.printStackTrace();
        } catch (NotEnoughResourcesToActivateProductionException e) {
            e.printStackTrace();
        }
        return false;
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
            e.printStackTrace();
        } catch (RequirementsUnfulfilledException e) {
            e.printStackTrace();
        }
    }

    /**
     * Used when the player wants to take at a dashboard. This method can get called anytime during the turn, before or after doing a main action, and doesn't
     * influence in any way the player's ability to perform any other action.
     * @param action: see {@link ActivateLeaderCardAction}
     */
    public void viewDashboard(Action action,ServerSideSocket serverSideSocket){
        //todo
        /*int playerID= ((ViewDashboardAction) action).getPlayerID();
        Message dashboardAnswer = new DashboardMessage(game.getPlayers().get(playerID).getDashboard());
        sendMessage(dashboardAnswer,nicknameToClientID.get(game.getActivePlayer().getNickname()));*/
        Dashboard dashboard = new Dashboard(1);
        System.out.println("we've received a dashboard request");
        DashboardMessage dashboardAnswer = new DashboardMessage(dashboard);
            try {
                serverSideSocket.getOutputStream().writeObject(dashboardAnswer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        System.out.println("we've sent the dashboard back to the client");
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
        if(totalPlayers==clientsInGameConnections.size())return true;
        return false;
    }

    /**
     * @return: {@link Turn}
     */
    public Turn getTurn() {
        return turn;
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

    public void endTurn() {
        //case the turn can end
        if(turn.getActionPerformed()!=0){
            turn.resetProductions();
            turn.setActionPerformed(0);
            game.nextTurn();
        }
        else{
            sendMessage(new GenericMessage("You can't end your turn until you make a main action"), game.getActivePlayer().getClientID());
        }

    }

    public void newInitialization(String nickname) {
        numOfInitializedClients++;
        nicknameToHisGamePhase.replace(nickname,2);
        if(numOfInitializedClients==clientsInGameConnections.size()){
            isStarted=true;
            gamePhase++;
            sendAll(new GameInitializationFinishedMessage());
            sendAll(new GenericMessage("It's "+game.getActivePlayer().getNickname()+"'s turn"));
        }
    }

    public void startingResources(BonusResourcesAction action, Player player){
        ResourceType resourceType= action.getResourceType1();
        Resource resource= parseResourceFromEnum(resourceType);
        if(action.getResourceType1()!=null) player.getDashboard().getWarehouse().addResource(parseResourceFromEnum(action.getResourceType1()));
        if(action.getResourceType2()!=null) player.getDashboard().getWarehouse().addResource(parseResourceFromEnum(action.getResourceType2()));
    }

    public void discardLeaderCardForFaith(DiscardLeaderCard action, Player player) {
        player.discardLeaderCardToGainFaith(action.getIndex());
    }

    public void discard2LeaderCards(DiscardTwoLeaderCardsAction action, Player player) {
        player.discard2LeaderCards(action.getIndex1(),action.getIndex2());
    }

    public void test(TestAction action, Player player) {
        player.testingMethod();
    }

    public void printMarket() {
        game.getGameBoard().getMarket().printMarket();
    }
}
