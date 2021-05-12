package it.polimi.ingsw.Communication.server;

import it.polimi.ingsw.Communication.client.messages.Message;
import it.polimi.ingsw.Communication.client.messages.NewTurnAction;
import it.polimi.ingsw.Communication.client.messages.QuitAction;
import it.polimi.ingsw.Communication.client.messages.actions.Action;
import it.polimi.ingsw.Communication.client.messages.actions.mainActions.DevelopmentAction;
import it.polimi.ingsw.Communication.client.messages.actions.mainActions.MarketAction;
import it.polimi.ingsw.Communication.client.messages.actions.mainActions.MarketDoubleWhiteToColorAction;
import it.polimi.ingsw.Communication.client.messages.actions.mainActions.ProductionAction;
import it.polimi.ingsw.Communication.client.messages.actions.secondaryActions.ActivateLeaderCardAction;
import it.polimi.ingsw.Communication.client.messages.actions.secondaryActions.ViewDashboardAction;
import it.polimi.ingsw.Exceptions.GameWithSpecifiedIDNotFoundException;
import it.polimi.ingsw.Exceptions.NoGameFoundException;
import it.polimi.ingsw.Exceptions.allThePlayersAreConnectedException;
import it.polimi.ingsw.Exceptions.nicknameNotInGameException;
import it.polimi.ingsw.market.OutOfBoundException;
import it.polimi.ingsw.resource.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Locale;

public class ServerSideSocket implements Runnable {
    private final Socket socket;
    private final Server server;
    private boolean isHost;
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

        Message message= (Message) inputStream.readObject();
        if(nickname.equals(gameHandler.getGame().getActivePlayer().getNickname())){
            if( message instanceof Action) playerAction((Action) message);
        }
        else out.println("Wait for your turn! At the moment "+ nickname+ " is playing his turn.");
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
                out.println("Create a new game, join or rejoin an already existing one?");
                line = in.readLine();
                switch (line){
                    case "Create":
                        out.println("You chose create");
                        createMatch();
                        break;
                    case "Join":
                        out.println("You chose join");
                        joinMatch();
                        break;
                    case "Rejoin":
                        out.println("You chose rejoin");
                        rejoinMatch();
                        out.println("Rejoin operation worked perfectly! You are connected back to your game");
                        break;
                    default:
                        out.println("Error: you must insert Create or Join");
                }
            }while (!line.equals("Create") && !line.equals("Join") && !line.equals("Rejoin"));
        }

          catch (IOException e) {
            e.printStackTrace();
        }
          catch (GameWithSpecifiedIDNotFoundException | allThePlayersAreConnectedException | nicknameNotInGameException | NoGameFoundException e) {
            //GameWithSpecifiedIDNotFoundException catch when the GameID insert by the user in Rejoin is not
              // correct (there's no match with the specified id in game)

            //allThePlayersAreConnectedException error catch when the players in the selected gameID (in rejoin) are all connected

            //nicknameNotInGameException error catch when the nickname selected by the user (in rejoin) is not in the game

            //NoGameFoundException error catch when there's no match in lobby (in join)

            out.println(e);
            createOrJoinMatchChoice();
        }
    }

    /**
     * Method called when user wants to rejoin a game he was previously connected to. It asks the client to insert the id of
     * that game and the nickname he was using in that.
     *
     * @throws GameWithSpecifiedIDNotFoundException when ID specified by the user doesn't correspond to a match in game
     * @throws allThePlayersAreConnectedException when the room of that ID is full
     * @throws nicknameNotInGameException when there's no in game nickname corresponding to the nickname insert by the user
     */
    private void rejoinMatch() throws GameWithSpecifiedIDNotFoundException, allThePlayersAreConnectedException, nicknameNotInGameException {
        out.println("What's the ID of the game you want to rejoin?");
        try {
            int idToSearch = Integer.parseInt(in.readLine());

            gameHandler = server.getGameHandlerByGameID(idToSearch);
            //case no match found with the specified ID
            if(gameHandler==null){
                throw new GameWithSpecifiedIDNotFoundException();
            }

            //case match found
            else {
                //but all the players are connected
                if(gameHandler.allThePlayersAreConnected()) {
                    throw new allThePlayersAreConnectedException();
                }
                //there is at least one left spot
                else{
                    out.println("Match found. What was your name in this game?");
                    String nickname = in.readLine();

                    //User has insert a valid nickname (there is an open spot in the game with the specified name)
                    if(gameHandler.isNicknameAlreadyTaken(nickname) &&
                            gameHandler.getClientIDToConnection().get(gameHandler.getNicknameToClientID().get(nickname))==null)
                        gameHandler.reconnectPlayer(this, nickname);

                    //invalid nickname
                    else {
                        throw new nicknameNotInGameException();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method called when a player wants to join a match. The match he will join is chosen by the server, that looks if there is
     * a match still in lobby, and if there is, adds the player to it, asking to insert a nickname that is not used by any
     * other player in the same lobby.
     */
    private void joinMatch() throws NoGameFoundException {
        out.println("Searching a game...");

        //I want to implement the possibility of looking for a match for a specified amount of time
        while(server.getMatchesInLobby().size()==0){
            //there is no match available
            throw new NoGameFoundException();
        }

        gameHandler = server.getMatchesInLobby().get(0);
        gameID = gameHandler.getGameID();
        out.println("You joined a match\nmatchID = "+gameID);
        try {
            boolean nicknameAlreadyTaken = true;
            while((nickname==null || nickname.equals("")) || nicknameAlreadyTaken) {
                out.println("Insert nickname: ");
                nickname = in.readLine();
                nicknameAlreadyTaken = gameHandler.isNicknameAlreadyTaken(nickname);
                if(nickname==null|| nickname.equals("")) out.println("Invalid nickname, insert something");
                if(nicknameAlreadyTaken) out.println("Error: " + nickname + " is already taken, please choose another name");
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
    protected void setTotalPlayers(int totalPlayers) throws OutOfBoundException {
        if (totalPlayers < 1 || totalPlayers > 4) {
            throw new OutOfBoundException();
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
     * Method used to send a string to the client
     *
     * @param string of type String: string to send to the client
     */
    public void sendString(String string){
        out.println(string);
    }

    /**
     * Method getClientID returns the clientID of this SocketClientConnection object.
     *
     * @return the clientID (type Integer) of this SocketClientConnection object.
     */
    public Integer getClientID() {
        return clientID;
    }

    public ArrayList<Resource> resourcesRequest(int size, boolean askingWhatToUse){
        ArrayList<Resource> resources=new ArrayList<>();
        for(int i=0;i<size;i++){
            if(askingWhatToUse)   out.println("Insert what resource you want to use [coin/shield/servant/stone] ");
            else                  out.println("Insert what resource you want to produce [coin/shield/servant/stone] ");
            try {
                Resource resource= parseResource(in.readLine().toLowerCase(Locale.ROOT));
                resources.add(resource);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resources;
    }

    public Resource parseResource(String string){
        switch (string){
            case "coin": return new CoinResource();
            case "stone": return new StoneResource();
            case "servant": return new ServantResource();
            case "shield": return new ShieldResource();
        }
        return null;
    }



    public void playerAction(Action action){
        Turn turn= gameHandler.getTurn();
        int actionPerformed= turn.getActionPerformed();
        boolean[] productions= turn.getProductions();
        if (action instanceof NewTurnAction) {
            turn= new Turn();
        };
        if (action instanceof DevelopmentAction && actionPerformed==0) if(gameHandler.developmentAction( (DevelopmentAction) action))   turn.setActionPerformed(1);
        else if (action instanceof MarketDoubleWhiteToColorAction && actionPerformed==0) actionPerformed=gameHandler.marketSpecialAction((MarketDoubleWhiteToColorAction) action);
        else if (action instanceof MarketAction && actionPerformed==0) actionPerformed=gameHandler.marketAction((MarketAction) action);
        else if (action instanceof ProductionAction && actionPerformed!=1) actionPerformed=gameHandler.productionAction(action,productions);
        else if (action instanceof ActivateLeaderCardAction) gameHandler.activateLeaderCard(action);
        else if (action instanceof ViewDashboardAction)      gameHandler.viewDashboard(action);
        else if (action instanceof QuitAction && actionPerformed!=0) gameHandler.getGame().changeTurn();
    }
}
