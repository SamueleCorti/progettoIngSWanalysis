package it.polimi.ingsw.Communication.server;


import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Server class
public class Server {
    private final SocketServer socketServer;


    /**
     * This hashmap permits identifying a GameHandler relying on his gameID, which was set at
     * the game creation.
     */
    private Map<Integer,GameHandler> gameIDToGameHandler;

    /**
     * This hashmap permits identifying a SingleConnection relying on his clientID
     * The client has to be connected to the server.
     */
    private Map<Integer, SingleConnection> clientIDToConnection;

    /**
     * This hashmap permits identifying a GameHandler relying on the clientID of one of the players connected
     * to the game.
     */
    private Map<Integer,GameHandler> clientIDToGameHandler;

    /** Unique Client ID reference, which is used in the ID generation method. */
    private int nextClientID=1;

    /** Unique Game ID reference, which is used in the ID generation method. */
    private int nextGameID=1;

    /** List of clients waiting in the lobby. */
    private final List<SingleConnection> waitingConnections;

    /** List of total clients connected to the server */
    private final List<SingleConnection> totalConnections;

    /**
     * Method quitter permits quitting from the server application, closing all active connections.
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

    public SingleConnection getConnectionFromID(int clientID){
        return clientIDToConnection.get(clientID);
    }

    /**
     * Constructor Server creates the instance of the server, based on a socket and the mapping
     * between VirtualClient, nicknames and client ids. It also creates a new game session.
     */
    public Server(int port) {
        socketServer = new SocketServer(port, this);
        gameIDToGameHandler = new HashMap<Integer,GameHandler>();
        clientIDToConnection = new HashMap<Integer,SingleConnection>();
        waitingConnections = new ArrayList<>();
        totalConnections = new ArrayList<>();
        clientIDToGameHandler = new HashMap<Integer,GameHandler>();
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
     * @param clientID of type int - the ID of the virtual client to be removed.
     */
    public synchronized void unregisterClient(int clientID) {
        getGameHandlerByID(clientID).unregisterPlayer(clientID);
        System.out.println("Unregistering client " + clientID + "...");
        waitingConnections.remove(clientIDToConnection.get(clientID));
        totalConnections.remove(clientIDToConnection.get(clientID));
        clientIDToConnection.remove(clientID);
        clientIDToGameHandler.remove(clientID);
        System.out.println("Client has been successfully unregistered.");
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
    public synchronized Integer registerConnection(SingleConnection socketClientHandler) {
        int clientID = createClientID();
        clientIDToConnection.put(clientID, socketClientHandler);
        System.out.println("Client identified by ID "+ clientID+ ", has successfully connected!");
        socketClientHandler.sendSocketMessage(
                    new ConnectionMessage("Connection was successfully set-up! You are now connected.", 0));
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
        int id = nextClientID;
        nextClientID++;
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
        System.out.println(">Insert the port which server will listen on.");
        System.out.print(">");
        /*if (argc==2){
            hostName = args[0];
            portNumber = Integer.parseInt(args[1]);
        }else{
            hostName = Prefs.ReadHostFromJSON();
            portNumber =Prefs.ReadPortFromJSON();
        }*/
        int port = 0;
        try {
            port = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.err.println("Numeric format requested, application will now close...");
            System.exit(-1);
        }
        if (port < 0 || (port > 0 && port < 1024)) {
            System.err.println("Error: ports accepted started from 1024! Please insert a new value.");
            main(null);
        }
        port=1234;
        System.err.println("Starting Socket Server");
        Server server = new Server(port);
        ExecutorService executor = Executors.newCachedThreadPool();
        System.out.println("Instantiating server class...");
        executor.submit(server.socketServer);
    }
}
