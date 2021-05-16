package it.polimi.ingsw.Communication.server;


import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Server class
public class Server {
    /**
     * Part of the server whose task is to accept new clients requests to connect
     */
    private final SocketServer socketServer;

    /** List of the matches still in lobby to the server */
    private final ArrayList<GameHandler> matchesInLobby;

    /** List of the matches already in game */
    private final ArrayList<GameHandler> matchesInGame;

    /**
     * This hashmap permits identifying a GameHandler relying on his gameID, which was set at
     * the game creation.
     */
    private final Map<Integer,GameHandler> gameIDToGameHandler;

    /**
     * This hashmap permits identifying a SingleConnection relying on his clientID
     * The client has to be connected to the server.
     */
    private final Map<Integer, ServerSideSocket> clientIDToConnection;

    /**
     * This hashmap permits identifying a GameHandler relying on the clientID of one of the players connected
     * to the game.
     */
    private final Map<Integer,GameHandler> clientIDToGameHandler;

    /** Unique Client ID reference, which is used in the ID generation method. */
    private int nextClientID=1;

    /** Unique Game ID reference, which is used in the ID generation method. */
    private int nextGameID=1;

    /** List of total clients connected to the server */
    private final List<ServerSideSocket> totalConnections;

    /**
     * Method quitter permits quitting from the server application, closing all connections.
     */
    public void quitter() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (scanner.next().equalsIgnoreCase("QUIT")) {
                getSocketServer().setActive(false);
                System.exit(0);
                break;
            }
        }
    }

    public ServerSideSocket getConnectionFromID(int clientID){
        return clientIDToConnection.get(clientID);
    }

    /**
     * Method returns the gameHandler related to an unique gameID
     *
     * @param gameID is the unique identifier of the gameHandler
     * @return the gameHandler related to gameID
     * @throws ArrayIndexOutOfBoundsException if there's no gameHandler related to that gameID
     */
    public GameHandler getGameHandlerByGameID(int gameID) throws ArrayIndexOutOfBoundsException{
        if(gameIDToGameHandler.get(gameID)==null){
            throw new ArrayIndexOutOfBoundsException();
        }
        return gameIDToGameHandler.get(gameID);
    }

    public ArrayList<GameHandler> getMatchesInGame() {
        return matchesInGame;
    }

    public Map<Integer, GameHandler> getGameIDToGameHandler() {
        return gameIDToGameHandler;
    }

    public Map<Integer, ServerSideSocket> getClientIDToConnection() {
        return clientIDToConnection;
    }

    public Map<Integer, GameHandler> getClientIDToGameHandler() {
        return clientIDToGameHandler;
    }

    public List<ServerSideSocket> getTotalConnections() {
        return totalConnections;
    }

    /**
     * Constructor Server creates the instance of the server, based on a socket and the mapping
     * between VirtualClient, nicknames and client ids. It also creates a new game session.
     */
    public Server(int port) {
        socketServer = new SocketServer(port, this);
        gameIDToGameHandler = new HashMap<>();
        clientIDToConnection = new HashMap<>();
        totalConnections = new ArrayList<>();
        clientIDToGameHandler = new HashMap<>();
        matchesInLobby = new ArrayList<>();
        matchesInGame = new ArrayList<>();
        Thread thread = new Thread(this::quitter);
        thread.start();
    }

    /**
     * Method getSocketServer returns the socketServer of this Server object.
     *
     * @return the socketServer (type SocketServer) of this Server object.
     */
    public synchronized SocketServer getSocketServer() {
        return socketServer;
    }

    /**
     * Method getGameByID returns the game handler by having the client ID. It's useful for getting
     * the game handler from the socket handler.
     *
     * @param id of type int - the client ID.
     * @return GameHandler - the associated game handler.
     */
    public GameHandler getGameHandlerByID(int id) {
        return clientIDToGameHandler.get(id);
    }

    /**
     * Method unregisterClient deletes a client from the hashmaps and active lists, unregistering his
     * connection with the server.
     *
     */
    public synchronized void unregisterClient(int clientID, ServerSideSocket connectionToRemove) {
        System.out.println("Unregistering client identified by ID " + clientID + "...");
        totalConnections.remove(clientIDToConnection.get(clientID));
        clientIDToConnection.remove(clientID);
        if(clientIDToGameHandler.get(clientID)!=null){
            getGameHandlerByID(clientID).unregisterPlayer(clientID);
            clientIDToGameHandler.remove(clientID);
        }
        System.out.println("Client identified by ID "+clientID+" has been successfully unregistered.");
    }

    /**
     * Method registerConnection registers a new connection between the client and the server, by
     * inserting him in the registry hashmaps. If the nickname has already been chosen, it simply
     * ignores this step and notify the client about this fact, asking him to provide a new nickname.
     *
     * @param socketClientHandler of type SocketClientConnection - the active connection between
     *     server socket and client socket.
     * @return Integer - the client ID if everything goes fine, null otherwise.
     */
    public synchronized Integer registerConnection(ServerSideSocket socketClientHandler) {
        int clientID = createClientID();
        clientIDToConnection.put(clientID, socketClientHandler);
        System.out.println("Client identified by ID "+ clientID+ ", has successfully connected!");
        return clientID;
    }

    /**
     * Method createClientID returns a new client ID for a fresh-connected client. It's based on an
     * attribute which considers the number of people connected to this server since his startup.
     *
     * @return int - the generated client id.
     */
    public synchronized int createClientID() {
        int id = nextClientID;
        nextClientID++;
        return id;
    }

    /**
     * Method createGameID returns a new game ID for a new game.
     *
     * @return int - the generated game id.
     */
    public synchronized int createGameID() {
        int id = nextGameID;
        nextGameID++;
        return id;
    }

    /**
     * The main class of the server. It simply creates a new server class, adding a server socket to
     * an executor.
     *
     * @param args of type String[] - the main args, like any Java application.
     */
    public static void main(String[] args) {
        System.out.println("Masters of Renaissance Server | Welcome!");
        Scanner scanner = new Scanner(System.in);
        /*System.out.println(">Insert the port which server will listen on.");
        System.out.print(">");
        if (argc==2){
            hostName = args[0];
            portNumber = Integer.parseInt(args[1]);
        }else{
            hostName = Prefs.ReadHostFromJSON();
            portNumber =Prefs.ReadPortFromJSON();
        }*/
        int port = 1234;
        /*try {
            port = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.err.println("Numeric format requested, application will now close...");
            System.exit(-1);
        }
        if (port < 0 || (port > 0 && port < 1024)) {
            System.err.println("Error: ports accepted started from 1024! Please insert a new value.");
            main(null);
        }*/
        System.err.println("Starting Socket Server");
        Server server = new Server(port);
        ExecutorService executor = Executors.newCachedThreadPool();
        System.out.println("Instantiating server class...");
        executor.submit(server.socketServer);
    }

    public ArrayList<GameHandler> getMatchesInLobby() {
        return matchesInLobby;
    }

    public void addNewMatch(GameHandler newMatch){
        matchesInLobby.add(newMatch);
    }
}
