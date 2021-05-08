package it.polimi.ingsw.Communication.server;

import it.polimi.ingsw.Communication.client.messages.QuitAction;
import it.polimi.ingsw.Communication.client.messages.actions.Action;
import it.polimi.ingsw.Communication.client.messages.actions.mainActions.DevelopmentAction;
import it.polimi.ingsw.Communication.client.messages.actions.mainActions.MarketAction;
import it.polimi.ingsw.Communication.client.messages.actions.mainActions.ProductionAction;
import it.polimi.ingsw.Communication.client.messages.actions.mainActions.productionActions.BaseProductionAction;
import it.polimi.ingsw.Communication.client.messages.actions.mainActions.productionActions.DevelopmentProductionAction;
import it.polimi.ingsw.Communication.client.messages.actions.mainActions.productionActions.LeaderProduction;
import it.polimi.ingsw.Communication.client.messages.actions.mainActions.productionActions.ResourcesCommunication;
import it.polimi.ingsw.Communication.client.messages.actions.secondaryActions.ActivateLeaderCardAction;
import it.polimi.ingsw.Communication.client.messages.actions.secondaryActions.ViewDashboardAction;
import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Game;
import it.polimi.ingsw.Player;
import it.polimi.ingsw.market.OutOfBoundException;
import it.polimi.ingsw.papalpath.CardCondition;
import it.polimi.ingsw.resource.Resource;
import it.polimi.ingsw.storing.RegularityError;

import java.io.IOException;
import java.io.PrintWriter;
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
    private int gameID;
    private ServerSideSocket hostConnection;
    private final ArrayList<String> clientsNicknames;
    private final ArrayList<ServerSideSocket> clientsInGameConnections;
    private final Map<Integer, String> orderToNickname;
    private final Map<Integer, ServerSideSocket> clientIDToConnection;
    private final Map<Integer, String> clientIDToNickname;
    private final Map<String,Integer> nicknameToClientID;

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
     * @param ClientSingleConnection socket used by that client
     * @param nickname string used to identify a player in the room (players in the same room can't have the same name)
     * @throws InterruptedException when TimeUnit throws it
     */
    public void lobby(int clientID, ServerSideSocket ClientSingleConnection, String nickname) throws InterruptedException {
        //gameHandler is updated with the new client values
        addNewPlayer(clientID,ClientSingleConnection, nickname);

        //case game is full, match is ready to start and all the players are notified of the event
        if(clientsInGameConnections.size()==totalPlayers){
            System.err.println("Number of players required for the game ("+totalPlayers+") reached. The match is starting.");
            for (int i = 3; i > 0; i--) {
                sendAll("Match starting in " + i);
                TimeUnit.MILLISECONDS.sleep(500);
            }
            sendAll("The match has started!");

            setup();
        }

        //room is not full yet, all the player are notified that there is one less empty spot in the room
        else {
            sendAll((totalPlayers - clientsInGameConnections.size()) + " slots left.");
        }
    }

    public int generateNewGameID(){
        return server.createGameID();
    }

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


    /**
     * Method makeAction handles an action received from a single client.
     * It makes several instance checks. It's based on the value of "started", which represents the current
     * game phase, in this order:
     * - 0: color phase
     * - 1: challenger phase;
     * - 2: players select their god powers;
     * - 3: board worker placement;
     * - 4: the game has started.
     */
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
        sendAllExcept("Player "+ nickname+" joined the game", clientID);
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

    public void reconnectPlayer(ServerSideSocket newServerSideSocket, String nickname) {
        clientsIDs.add(newServerSideSocket.getClientID());
        clientsInGameConnections.add(newServerSideSocket);
        clientIDToConnection.put(newServerSideSocket.getClientID(),newServerSideSocket);
        clientIDToNickname.put(newServerSideSocket.getClientID(),nickname);
        nicknameToClientID.replace(nickname,newServerSideSocket.getClientID());
    }


    public boolean isNicknameAlreadyTaken(String nickname){
        if(clientsNicknames.contains(nickname)) return true;
        return false;
    }

    public int getTotalPlayers() {
        return totalPlayers;
    }

    public void playerAction(Action action){
        int actionPerformed=0;
        boolean[] productions= new boolean[6]; //represents, in order, base prod, leader1 prod, leader2 prod, and the 3 dev card zone prod, used to avoid using
                                                //the same production more than one time in each turn
        for(int i=0; i<6;i++)   productions[i]=false;
        do {
            if (action instanceof MarketAction && actionPerformed==0) actionPerformed=marketAction((MarketAction) action);
            else if (action instanceof DevelopmentAction && actionPerformed==0) actionPerformed=developmentAction( (DevelopmentAction) action);
            else if (action instanceof ProductionAction && actionPerformed!=1) actionPerformed=productionAction(action,productions);
            else if (action instanceof ActivateLeaderCardAction) activateLeaderCard(action);
            else if (action instanceof ViewDashboardAction)      viewDashboard(action);
        }while (!(actionPerformed!=0 && (action instanceof QuitAction)));
    }

    public int marketAction(MarketAction message){
        try {
            game.getActivePlayer().getResourcesFromMarket(game.getGameBoard(), message.isRow(), message.getIndex());
            return 1;
        } catch (OutOfBoundException e) {
            e.printStackTrace();
        } catch (RegularityError regularityError) {
            regularityError.printStackTrace();
        }
        return 0;
    }

    public int developmentAction (DevelopmentAction message){
        try {
            game.getActivePlayer().buyDevelopmentCard(message.getColor(), message.getCardLevel(), message.getIndex(), game.getGameBoard());
            return 1;
        } catch (NotCoherentLevelException e) {
            e.printStackTrace();
        } catch (NotEnoughResourcesException e) {
            e.printStackTrace();
        } catch (RegularityError regularityError) {
            regularityError.printStackTrace();
        } catch (NotEnoughResourcesToActivateProductionException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int productionAction(Action message, boolean[] productions){
        if (message instanceof BaseProductionAction && productions[0]==false) {
            if (baseProduction(productions)) return 2;
            else return 0;
        }
        if (message instanceof LeaderProduction){
            int leaderCardZoneIndex= ((LeaderProduction) message).getLeaderCardZoneIndex();
            if (productions[leaderCardZoneIndex]==false){
                if(leaderProduction(productions, leaderCardZoneIndex)) return 2;
            }
        }
        if (message instanceof DevelopmentProductionAction){
            int devCardZoneIndex= ((DevelopmentProductionAction) message).getDevelopmentCardZone();
            if (productions[devCardZoneIndex+2]==false){
                if(devCardProduction(productions,devCardZoneIndex)) return 2;
            }
        }
        return 0;
    }

    public boolean baseProduction(boolean[] productions){
        ArrayList<Resource> used= new ArrayList<>();
        ArrayList<Resource> created= new ArrayList<>();
        used = hostConnection.resourcesRequest(game.getActivePlayer().getDashboard().getNumOfStandardProdRequirements(), true);
        created= hostConnection.resourcesRequest(game.getActivePlayer().getDashboard().getResourcesForExtraProd().size(), false);
        if (game.getActivePlayer().activateStandardProduction(used, created)) {
            productions[0] = true;
            return true;
        }
        //TODO: else notifies the client that something went wrong
        return false;
    }

    public boolean leaderProduction(boolean[] productions, int index){
        ArrayList<Resource> used= new ArrayList<>();
        ArrayList<Resource> created= new ArrayList<>();
        used = hostConnection.resourcesRequest(game.getActivePlayer().getDashboard().getResourcesForExtraProd().size(), true);
        created= hostConnection.resourcesRequest(game.getActivePlayer().getDashboard().getResourcesForExtraProd().size(), false);
        try {
            if (game.getActivePlayer().activateLeaderProduction(index)) {
                productions[index] = true;
                return true;
            }
        } catch (ActivatingLeaderCardsUsingWrongIndexException e) {
            e.printStackTrace();
        } catch (WrongTypeOfLeaderPowerException e) {
            e.printStackTrace();
        } catch (NotEnoughResourcesToActivateProductionException e) {
            e.printStackTrace();
        } catch (LeaderCardNotActiveException e) {
            e.printStackTrace();
        }
        //TODO: else notifies the client that something went wrong
        return false;
    }

    public boolean devCardProduction(boolean[] productions, int index){
        try {
            game.getActivePlayer().activateDevelopmentProduction(index);
            productions[2+index]=true;
            return true;
        } catch (RegularityError regularityError) {
            regularityError.printStackTrace();
        } catch (NotEnoughResourcesToActivateProductionException e) {
            e.printStackTrace();
        }
        return false;
    }

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

    public void viewDashboard(Action action){
        int playerID= ((ViewDashboardAction) action).getPlayerID();
        //TODO
    }

    public Game getGame() {
        return game;
    }

    public Map<Integer, ServerSideSocket> getClientIDToConnection() {
        return clientIDToConnection;
    }

    public Map<String, Integer> getNicknameToClientID() {
        return nicknameToClientID;
    }

    public boolean allThePlayersAreConnected() {
        if(totalPlayers==clientsInGameConnections.size())return true;
        return false;
    }
}
