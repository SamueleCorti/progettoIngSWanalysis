package it.polimi.ingsw.Communication.server;

import it.polimi.ingsw.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameHandler {
    private final Server server;
    //Should maybe place here the parameter setter
    private Game game;
    private boolean isStarted;
    private int totalPlayers;
    private final ArrayList<Integer> clientsID;
    private int gameID;
    private ArrayList<SingleConnection> clientsInGame;
    private Map<Integer,SingleConnection> orderToConnection;
    private Map<String,SingleConnection> nicknameToConnection;
    private Map<String,Integer> nicknameToClientID;

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
        clientsID= new ArrayList<>();
        clientsInGame= new ArrayList<>();
        orderToConnection= new HashMap<>();
        nicknameToConnection= new HashMap<>();
        clientIDToNickname= new HashMap<>();
        generateNewGameID();
    }

    public void generateNewGameID(){
        gameID = server.createGameID();
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
        for(int clientID: clientsID ) {
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
        for(int clientID: clientsID) {
            if(clientID!=excludedID) {
                singleSend(message, clientID);
            }
        }
    }


    /**
     * Method setup handles the preliminary player setup phase; in this phase the color of workers' markers will be
     * asked the player, with a double check (on both client and server sides) of the validity of them
     * (also in case of duplicate colors).
     */
    public void setup() {
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

    public void addNewPlayer(int clientID,SingleConnection clientInGame,String nickname){
        clientsID.add(clientID);
        clientsInGame.add(clientInGame);
        this.orderToConnection = orderToConnection;
        nicknameToConnection.put(nickname,clientInGame);
        nicknameToClientID.put(nickname,clientID);
    }

    /**
     * Method unregisterPlayer unregisters a player identified by his unique ID after a disconnection event or message.
     *
     * @param id of type int - the unique id of the client to be unregistered.
     */
    public void unregisterPlayer(int id) {
        //removing clientID?
    }

    public void endGame() {
    }

    public int getTotalPlayers() {
        return totalPlayers;
    }
}
