package it.polimi.ingsw.Communication.server;

import it.polimi.ingsw.market.OutOfBoundException;

import java.io.*;
import java.net.Socket;

public class ServerSideSocket implements Runnable {
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
    public ServerSideSocket(Socket socket, Server server) {
        this.server = server;
        this.socket = socket;
        this.isHost = false;
        try {
            //line 47 contains error
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
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
            gameHandler.lobby(clientID,this,nickname);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
            Thread.currentThread().interrupt();
        }
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

    /**
     * Method used once the connection with the client is made. Server asks the user if he wants to create a new game or join
     * an already existing one, parsing the method based on his choice
     */
    private void createOrJoinMatchChoice() {
        try {
            String line;
            do {
                out.println("Create a new game or join an already existing one?");
                line = in.readLine();
                switch (line){
                    case "Create":
                        out.println("You chose create");
                        createMatch();
                        break;
                    case "Join":
                        joinMatch();
                        out.println("You chose join");
                        break;
                    default:
                        out.println("Error: you must insert Create or Join");
                }
            }while (!line.equals("Create") && !line.equals("Join"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method called when a player wants to join a match. The match he will join is chosen by the server, that looks if there is
     * a match still in lobby, and if there is, adds the player to it, asking to insert a nickname that is not used by any
     * other player in the same lobby.
     */
    private void joinMatch() {
        out.println("Searching a game...");

        //I want to implement the possibility of looking for a match for a specified amount of time
        while(server.getMatchesInLobby().size()==0){
            //there is no match available
        }

        gameHandler = server.getMatchesInLobby().get(0);
        gameID = gameHandler.getGameID();
        out.println("You joined a match\nmatchID = "+gameID);
        try {
            boolean nicknameAlreadyTaken = true;
            while((nickname==null || nickname.equals("")) && nicknameAlreadyTaken) {
                out.println("Insert nickname: ");
                nickname = in.readLine();
                nicknameAlreadyTaken = gameHandler.isNicknameAlreadyTaken(nickname);
                if(nickname==null|| nickname.equals("")) out.println("Invalid nickname, insert something");
                if(nicknameAlreadyTaken==true) out.println("Error: " + nickname + "is already taken, please choose another name");
            }

            server.getClientIDToConnection().put(clientID,this);
            server.getClientIDToGameHandler().put(clientID, gameHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Method called when the player decides to create a new match. It asks the player how many other players he wants in his
     * room and the nickname he wants to use. If all is correctly insert, a new GameHandler is created and the player is set
     * as host.
     */
    private void createMatch() {
        out.println("Select number of players(1 to 4): ");
        try {
            int tempMaxPlayers = Integer.parseInt(in.readLine());
            try {

                //setTotalPlayers throws an error if the choice of the host is not a number between 1 and 4
                setTotalPlayers(tempMaxPlayers);

                //effective creation of the game
                gameHandler = new GameHandler(server,tempMaxPlayers);
                gameID = gameHandler.getGameID();

                //part for inserting the nickname of players for this game, loops until name is valid
                out.println("New match created, ID = "+ gameID + ".\nNumber of players = "
                        + gameHandler.getTotalPlayers());
                while(nickname==null || nickname.equals("")) {
                    out.println("Insert nickname: ");
                    nickname = in.readLine();
                    if(nickname==null|| nickname.equals("")) out.println("Invalid nickname, insert something");
                }

                //setting all the maps and lists of the server with the new values just created for this game
                server.getGameIDToGameHandler().put(gameID,gameHandler);
                server.getMatchesInLobby().add(gameHandler);
                server.getClientIDToConnection().put(clientID,this);
                server.getClientIDToGameHandler().put(clientID, gameHandler);

                //setting the match creator as host
                gameHandler.setHost(this);
                isHost=true;
                out.println(nickname +", you have been successfully added to the match and set as host!");
                return;

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
        out.println("Connection was successfully set-up! You are now connected.");
    }

    /**
     * Method sendSocketMessage allows dispatching the server's Answer to the correct client. The type
     * SerializedMessage contains an Answer type object, which represents an interface for server
     * answer, like the client Message one.
     *
     * @param serverAnswer of type SerializedAnswer - the serialized server answer (interface Answer).
     */
    //TODO: to make this class we need to define the type of this class
    public void sendSocketMessage(Object serverAnswer) {
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
