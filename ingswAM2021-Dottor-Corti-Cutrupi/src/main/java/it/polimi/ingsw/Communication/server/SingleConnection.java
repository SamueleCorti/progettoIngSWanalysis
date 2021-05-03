package it.polimi.ingsw.Communication.server;

import it.polimi.ingsw.market.OutOfBoundException;

import java.io.*;
import java.net.Socket;

public class SingleConnection implements Runnable {
    private final Socket socket;
    private final Server server;
    private boolean isHost = false;
    private GameHandler gameHandler;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private Integer clientID;
    private int order;
    private int gameID;
    private String nickname;
    private boolean active;
    private PrintWriter out;
    private BufferedReader in;


    /**
     * Method isActive returns the state of this SocketClientConnection object.
     *
     * @return the active (type boolean) of this SocketClientConnection object.
     */
    public synchronized boolean isActive() {
        return active;
    }

    /**
     * Constructor SocketClientConnection instantiates an input/output stream from the socket received
     * as parameters, and adds the main server to his attributes too.
     *
     * @param socket of type Socket - the socket that accepted the client connection.
     * @param server of type Server - the main server class.
     */
    public SingleConnection(Socket socket, Server server) {
        this.server = server;
        this.socket = socket;
        this.isHost = false;
        try {
            //line 47 contains error
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            clientID = -1;
            active = true;
            order=-1;
        } catch (IOException e) {
            System.err.println("Error during initialization of the client!");
            System.err.println(e.getMessage());
        }
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method getSocket returns the socket of this SocketClientConnection object.
     *
     * @return the socket (type Socket) of this SocketClientConnection object.
     */
    public Socket getSocket() {
        return socket;
    }

    /**
     * Method close terminates the connection with the client, closing firstly input and output
     * streams, then invoking the server method called "unregisterClient", which will remove the
     * active virtual client from the list.
     *
     */
    public void close() {
        server.unregisterClient(this.getClientID());
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Method readFromStream reads a serializable object from the input stream, using
     * ObjectInputStream library.
     *
     * @throws IOException when the client is not online anymore.
     * @throws ClassNotFoundException when the serializable object is not part of any class.
     */

   public synchronized void readFromStream() throws IOException, ClassNotFoundException {
       //TODO:  we need a way to read from stream

       /*SerializedMessage input = (SerializedMessage) inputStream.readObject();
        if (input.message != null) {
            Message command = input.message;
            actionHandler(command);
        } else if (input.action != null) {
            UserAction action = input.action;
            actionHandler(action);
        }*/
    }

    /**
     * Method run is the overriding runnable class method, which is called on a new client connection.
     *
     * @see Runnable#run()
     */
    @Override
    public void run() {
        createNewConnection();
        createOrJoinMatchChoice();
        try {
            while (isActive()) {
                readFromStream();
            }
        } catch (IOException e) {
            GameHandler game = server.getGameHandlerByID(clientID);
            server.unregisterClient(clientID);
            System.err.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void createOrJoinMatchChoice() {
        try {
            String line;
            do {
                out.println("Create a new game or join an already existing one?");
                line = in.readLine();
                switch (line){
                    case "Create":
                        createMatch();
                        break;
                    case "Join":
                        joinMatch();
                        break;
                    default:
                        out.println("Error: you must insert Create or Join");
                }
            }while (line!="Create" && line!="Join");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Method used to join a random match that is still in lobby phase
     */
    private void joinMatch() {
        out.println("Select number of players(1 to 4): ");
    }

    private void joinMatch(int matchID) {
        gameHandler = server.getGameHandlerByGameID(matchID);
    }

    private void createMatch() {
        out.println("Select number of players(1 to 4): ");
        try {
            int tempMaxPlayers = Integer.parseInt(in.readLine());
            try {
                setTotalPlayers(tempMaxPlayers);
                gameHandler = new GameHandler(server,tempMaxPlayers);
                gameID = gameHandler.getGameID();
                out.println("New match created, ID = "+ gameID + ".\nNumber of players = "
                        + gameHandler.getTotalPlayers());
                while(nickname==null) {
                    out.println("Insert nickname: ");
                    nickname = in.readLine();
                    if(nickname==null) out.println("Invalid nickname, insert something");
                }
                gameHandler.addNewPlayer(clientID,this,nickname);
                gameHandler.setHost(this);
                isHost=true;
                out.println(nickname +": you have been successfully added to the match and set as host");
            } catch (OutOfBoundException e) {
                out.println( "Error: not a valid input! Please provide a value between 1 and 4");
                createMatch();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method setTotalPlayers sets the maximum number of player relying on the input provided by the
     * first user who connects. He's is also called the "lobby host".
     *
     * @param totalPlayers of type int - the number of players provided by the first user connected.
     * @throws OutOfBoundException when the input is not in the correct player range.
     */
    protected int setTotalPlayers(int totalPlayers) throws OutOfBoundException {
        if (totalPlayers < 1 || totalPlayers > 4) {
            throw new OutOfBoundException();
        } else {
            return totalPlayers;
        }
    }


    //TODO: handle all the possible actions
    public void actionHandler() {

    }

    /**
     * Method checkConnection checks the validity of the connection message received from the client.
     *
     */
    private void createNewConnection() {
        clientID = server.registerConnection(this);
        if (clientID == null) {
            active = false;
            return;
        }
        //we have to notify the client that he now has a connection with the server, associated with that ID
    }

    /**
     * Method sendSocketMessage allows dispatching the server's Answer to the correct client. The type
     * SerializedMessage contains an Answer type object, which represents an interface for server
     * answer, like the client Message one.
     *
     * @param serverAnswer of type SerializedAnswer - the serialized server answer (interface Answer).
     */
    //TODO: to make this class we need to define the type of this class
    public void sendSocketMessage(String serverAnswer) {
       try {
            outputStream.reset();
            outputStream.writeObject(serverAnswer);
            outputStream.flush();
        } catch (IOException e) {
            close();
        }
    }

    /**
     * Method getClientID returns the clientID of this SocketClientConnection object.
     *
     * @return the clientID (type Integer) of this SocketClientConnection object.
     */
    public Integer getClientID() {
        return clientID;
    }
}
