package it.polimi.ingsw.Communication.server;

import it.polimi.ingsw.Communication.client.actions.Action;
import it.polimi.ingsw.Communication.client.actions.mainActions.DevelopmentAction;
import it.polimi.ingsw.Communication.client.actions.mainActions.MarketAction;
import it.polimi.ingsw.Communication.client.actions.mainActions.MarketDoubleWhiteToColorAction;
import it.polimi.ingsw.Communication.client.actions.mainActions.productionActions.BaseProductionAction;
import it.polimi.ingsw.Communication.client.actions.mainActions.productionActions.DevelopmentProductionAction;
import it.polimi.ingsw.Communication.client.actions.mainActions.productionActions.LeaderProductionAction;
import it.polimi.ingsw.Communication.client.actions.secondaryActions.ActivateLeaderCardAction;
import it.polimi.ingsw.Communication.client.actions.secondaryActions.ViewDashboardAction;
import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Game;
import it.polimi.ingsw.Player;
import it.polimi.ingsw.market.OutOfBoundException;
import it.polimi.ingsw.resource.*;
import it.polimi.ingsw.storing.RegularityError;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class GameHandler {
    private final Server server;
    //Should maybe place here the parameter setter
    private final Game game;
    private boolean isStarted;
    private int newPlayerOrder = 1;
    private int totalPlayers;
    private final ArrayList<Integer> clientsIDs;
    private final int gameID;
    private ServerSideSocket hostConnection;
    private final ArrayList<String> clientsNicknames;
    private final ArrayList<ServerSideSocket> clientsInGameConnections;
    private final Map<Integer, String> orderToNickname;
    private final Map<Integer, ServerSideSocket> clientIDToConnection;
    private final Map<Integer, String> clientIDToNickname;
    private final Map<String,Integer> nicknameToClientID;
    private Turn turn;
    /**
     * Constructor GameHandler creates a new GameHandler instance.
     *
     * @param server of type Server - the main server class.
     */
    public GameHandler(Server server, int totalPlayers) {
        game= new Game();
        this.server = server;
        this.totalPlayers = totalPlayers;
        isStarted = false;
        clientsIDs = new ArrayList<>();
        clientsInGameConnections = new ArrayList<>();
        orderToNickname = new HashMap<>();
        clientIDToConnection = new HashMap<>();
        clientIDToNickname = new HashMap<>();
        nicknameToClientID = new HashMap<>();
        clientsNicknames = new ArrayList<>();
        gameID = generateNewGameID();
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
                sendAllString("Match starting in " + i);
                TimeUnit.MILLISECONDS.sleep(500);
            }
            sendAllString("The match has started!");

            setup();
        }

        //room is not full yet, all the player are notified that there is one less empty spot in the room
        else {
            sendAllString((totalPlayers - clientsInGameConnections.size()) + " slots left.");
        }
    }

    /**
     * @return the gameID generated by the server for this game
     */
    public int generateNewGameID(){
        return server.createGameID();
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
     * Method singleSend sends a message to a client, identified by his ID number, through the server socket.
     *
     * @param message of type Answer - the message to be sent to the client.
     * @param id of type int - the unique identification number of the client to be contacted.
     */
    public void singleSend(String message, int id) {
        server.getConnectionFromID(id).sendSocketMessage(message);
    }


    /**
     * Method sendAll does the same as the previous method, but it iterates on all the clients present in the game.
     * It's a full effects broadcast.
     *
     * @param message of type Answer - the message to broadcast (at single match participants' level).
     */
    public void sendAll(String message) {
        for(int clientID: clientsIDs ) {
            singleSend(message, clientID);
        }
    }

    /**
     * Method sendAllString does the same as the previous method, but with strings instead of messages
     *
     * @param message of type String - the message to broadcast (at single match participants' level).
     */
    private void sendAllString(String message) {
        for(int clientID: clientsIDs ) {
            singleSendString(message, clientID);
        }
    }

    /**
     * Method singleSend sends a string to a client, identified by his ID number, through the server socket.
     *
     * @param s of type String - the message to be sent to the client.
     * @param clientID of type int - the unique identification number of the client to be contacted.
     */
    private void singleSendString(String s, int clientID) {
        server.getConnectionFromID(clientID).sendString(s);
    }


    /**
     * Method sendAllExcept makes the same as the previous method, but it iterates on all the clients present in the
     * game, except the declared one.
     *
     * @param message of type Answer - the message to be transmitted.
     * @param excludedID of type int - the client which will not receive the communication.
     */
    public void sendAllExcept(String message, int excludedID) {
        for(int clientID: clientsIDs) {
            if(clientID!=excludedID) {
                singleSend(message, clientID);
            }
        }
    }

    /**
     * Method sendAllExceptString makes the same as the previous method, but with strings and not with messages
     *
     * @param message of type String - the message to be transmitted.
     * @param excludedID of type int - the client which will not receive the communication.
     */
    private void sendAllExceptString(String message, int excludedID) {
        for(int clientID: clientsIDs) {
            if(clientID!=excludedID) singleSendString(message, clientID);
        }
    }

    public void playerDisconnectedNotify(String nickname){
        for(ServerSideSocket connections: clientsInGameConnections){
            connections.sendSocketMessage("Unfortunately player (nickname: " + nickname+ ") " +
                    "has just lost his connection. The game will go on, and you'll be notified whenever " +
                    "he reconnects again");
        }
    }

    public void gameFinishedNotifyPlayers(){
        int winnerID= nicknameToClientID.get(getWinner());
        sendAllExcept("Game finished! Player " + getWinner()+ " won with " +
                getWinnerPoints()+ " points!",winnerID);
        singleSend("Congratulations! You won this match with " + getWinnerPoints()+
                " points!", winnerID);
    }

    public String getWinner(){
        int max=0;
        String winner= new String();
        for(Player player: game.getPlayers()){
            if(player.getVictoryPoints()>max){
                winner= player.getNickname();
                max= player.getVictoryPoints();
            }
        }
        return winner;
    }

    public int getWinnerPoints(){
        int max=0;
        for(Player player: game.getPlayers()){
            if(player.getVictoryPoints()>max){
                max= player.getVictoryPoints();
            }
        }
        return max;
    }

    /**
     * Method setup handles the preliminary player setup phase; in this phase the color of workers' markers will be
     * asked the player, with a double check (on both client and server sides) of the validity of them
     * (also in case of duplicate colors).
     */
    public void setup() {
        //Since the game has started, we must update the lists of the server
        server.getMatchesInLobby().remove(this);
        server.getMatchesInGame().add(this);

        if(!isStarted) isStarted=true;
    }



    /**
     * @return the server class.
     */
    public Server getServer() {
        return server;
    }

    /*
    public void makeAction(UserAction action, String type) {
        switch (type) {
            case "ChallengerPhase" -> challengerPhase(action);
            case "WorkerPlacement" -> workerPlacement((WorkerSetupAction) action);
            case "turnController" -> controllerListener.firePropertyChange(type, null, action);
            default -> singleSend(new GameError(ErrorsType.INVALIDINPUT), getCurrentPlayerID());
        }
    }*/

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

        //the player is new, we add his connection to a map whose key is his player order (decided by the game handler)
        orderToNickname.put(newPlayerOrder,nickname);
        newPlayerOrder++;

        //updating maps with new player's values
        clientIDToNickname.put(clientID,nickname);
        nicknameToClientID.put(nickname,clientID);
        clientIDToConnection.put(clientID,clientSingleConnection);

        //sending a message notifying that a new player has joined the lobby to all the players already in lobby
        sendAllExceptString("Player "+ nickname+" joined the game", clientID);
        System.err.println("Player "+nickname+" joined gameID="+gameID);
    }


    /**
     * Method unregisterPlayer unregisters a player identified by his unique ID after a disconnection event or message.
     *
     * @param id of type int - the unique id of the client to be unregistered.
     */
    public void unregisterPlayer(int id) {

        //All the lists and maps are updated removing the client who disconnected
        clientsIDs.remove(id);

        //If the room is empty, game ends
        if(clientsIDs.size()>0){
            removeGameHandler();
        }

        sendAll("Player "+clientIDToNickname.get(id)+"disconnected from the game.");
        clientIDToNickname.remove(id);
        clientsInGameConnections.remove(clientIDToConnection.get(id));
        clientIDToConnection.remove(id);
        clientIDToNickname.remove(id);

        //If the player was the host, another player is set as new host.
        if(clientIDToConnection.get(id)==hostConnection){
            hostConnection = clientIDToConnection.get(clientsIDs.get(0));
            sendAll(clientIDToNickname.get(clientsIDs.get(0)) + " is the new host.");
        }
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
    public void marketAction(MarketAction message){
        try {
            game.getActivePlayer().getResourcesFromMarket(game.getGameBoard(), message.isRow(), message.getIndex());
            turn.setActionPerformed(1);
        } catch (OutOfBoundException | RegularityError e) {
            e.printStackTrace();
        }
    }


    /**
     * Method used to get resources from market when the player has two WhiteToColor leader cards active . Sets actionPerformed in turn to 1 if all goes well.
     * @param message: see {@link MarketDoubleWhiteToColorAction}
     */
    public void marketSpecialAction(MarketDoubleWhiteToColorAction message){
            ArrayList<Resource> resources = new ArrayList<Resource>();
            for(ResourceType resourceEnum: message.getResources()){
                resources.add(parseResourceFromEnum(resourceEnum));
            }
        try {
            game.getActivePlayer().getResourcesFromMarket(getGame().getGameBoard(),message.isRow(),message.getIndex(), resources);
            turn.setActionPerformed(1);
        } catch (OutOfBoundException e) {
            e.printStackTrace();
        } catch (RegularityError regularityError) {
            regularityError.printStackTrace();
        } catch (NotCoherentResourceInArrayWhiteToColorException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method used to buy the player the requested development card. Sets actionPerformed in turn to 1 if all goes well.
     * @param message: see {@link DevelopmentAction}
     */
    public void developmentAction (DevelopmentAction message){
        try {
            game.getActivePlayer().buyDevelopmentCard(message.getColor(), message.getCardLevel(), message.getIndex(), game.getGameBoard());
            turn.setActionPerformed(1);
        } catch (NotCoherentLevelException | NotEnoughResourcesException | RegularityError | NotEnoughResourcesToActivateProductionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when the client sends a production action message. This method itself can call {@link #baseProduction(BaseProductionAction)}, {@link #leaderProduction(LeaderProductionAction)},
     * {@link #devCardProduction(int)} depending on the Action type of message the controller receives
     * @param action
     * @param productions
     */

    public void productionAction(Action action, boolean[] productions){
        if (action instanceof BaseProductionAction && !productions[0]) {
            if (baseProduction((BaseProductionAction) action)) turn.setActionPerformed(2);
        }
        if (action instanceof LeaderProductionAction){
            int leaderCardZoneIndex= ((LeaderProductionAction) action).getLeaderCardZoneIndex();
            if (!productions[leaderCardZoneIndex]){
                if(leaderProduction((LeaderProductionAction) action)) turn.setActionPerformed(2);
            }
        }
        if (action instanceof DevelopmentProductionAction){
            int devCardZoneIndex= ((DevelopmentProductionAction) action).getDevelopmentCardZone();
            if (!productions[devCardZoneIndex + 2]){
                if(devCardProduction(devCardZoneIndex)) turn.setActionPerformed(2);
            }
        }
        return;
    }

    /**
     * Called when the client selects a BaseProductionAction
     * @param action: see {@link BaseProductionAction}
     * @return  true if the action has been performed correctly, false otherwise
     */
    public boolean baseProduction(BaseProductionAction action){
        ArrayList<Resource> used = new ArrayList<Resource>();
        for(ResourceType resourceEnum: action.getResourcesUsed()){
            used.add(parseResourceFromEnum(resourceEnum));
        }
        ArrayList<Resource> created = new ArrayList<Resource>();
        for(ResourceType resourceEnum: action.getResourcesWanted()){
            created.add(parseResourceFromEnum(resourceEnum));
        }
        if (game.getActivePlayer().activateStandardProduction(used, created)) {
            turn.setProductions(0,true);
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
    public boolean leaderProduction(LeaderProductionAction action){
        Resource resourceWanted = parseResourceFromEnum(action.getResourcesWanted());
        int index= action.getLeaderCardZoneIndex();
        if (game.getActivePlayer().getDashboard().leaderProd(action.getLeaderCardZoneIndex(),resourceWanted)){
            turn.setProductions(index+1, true);
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
    public boolean devCardProduction(int index){
        try {
            game.getActivePlayer().activateDevelopmentProduction(index);
            turn.setProductions(2+index, true);
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
    public void activateLeaderCard(Action action){
        int index= ((ActivateLeaderCardAction) action).getIndex();
        try {
            game.getActivePlayer().activateLeaderCard(index);
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
    public void viewDashboard(Action action){
        int playerID= ((ViewDashboardAction) action).getPlayerID();
        //TODO
        System.out.println("we've received a view dashboard message");
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
}
