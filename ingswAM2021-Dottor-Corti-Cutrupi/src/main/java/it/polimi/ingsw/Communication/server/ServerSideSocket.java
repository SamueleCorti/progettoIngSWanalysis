package it.polimi.ingsw.Communication.server;

import it.polimi.ingsw.Communication.client.actions.*;
import it.polimi.ingsw.Communication.client.actions.mainActions.DevelopmentAction;
import it.polimi.ingsw.Communication.client.actions.mainActions.MarketAction;
import it.polimi.ingsw.Communication.client.actions.mainActions.MarketDoubleWhiteToColorAction;
import it.polimi.ingsw.Communication.client.actions.mainActions.ProductionAction;
import it.polimi.ingsw.Communication.client.actions.secondaryActions.ActivateLeaderCardAction;
import it.polimi.ingsw.Communication.client.actions.secondaryActions.ViewDashboardAction;
import it.polimi.ingsw.Communication.server.messages.*;
import it.polimi.ingsw.Exceptions.GameWithSpecifiedIDNotFoundException;
import it.polimi.ingsw.Exceptions.NoGameFoundException;
import it.polimi.ingsw.Exceptions.allThePlayersAreConnectedException;
import it.polimi.ingsw.Exceptions.nicknameNotInGameException;
import it.polimi.ingsw.market.OutOfBoundException;

import java.io.*;
import java.net.Socket;
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
        //todo see if this method is actually needed/correctly implemented
        //server.unregisterClient(this.getClientID());
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Method readFromStream reads an action from the input stream
     */
    public synchronized void readFromStream() throws IOException, ClassNotFoundException {
        System.out.println("we're reading obj from stream");
        Action action  = (Action) inputStream.readObject();
        System.out.println("we've received the action: we're going to handle it");
        playerAction(action);

        //this part is just to check if the message is delivered properly
        /*if(action instanceof QuitAction){
            System.out.println("quit message received");
        }
        if(action instanceof NewTurnAction){
            System.out.println("new turn message received");
        }*/

        //TODO has to be fixed
        /*if(nickname.equals(gameHandler.getGame().getActivePlayer().getNickname())){
            if( message instanceof Action) playerAction((Action) message);
        }
        else out.println("Wait for your turn! At the moment "+ nickname+ " is playing his turn.");*/
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
            System.out.println("we're reading from stream!");
            while (isActive()) {
                readFromStream();
            }
        } catch (IOException e) {
            GameHandler game = server.getGameHandlerByID(clientID);
            //todo see if this method is actually needed/correctly implemented
            //server.unregisterClient(clientID);
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
            Object line;
            line=inputStream.readObject();
            if(line instanceof CreateMatchAction){
                createMatch((CreateMatchAction) line);
            }
            else if(line instanceof JoinMatchAction){
                joinMatch((JoinMatchAction) line);
            }
            else if(line instanceof RejoinMatchAction){
                rejoinMatch();
            }
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
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
    private void joinMatch(JoinMatchAction message) throws NoGameFoundException {

        //I want to implement the possibility of looking for a match for a specified amount of time
        if(server.getMatchesInLobby().size()==0){
            //there is no match available
            try {
                outputStream.writeObject(new JoinMatchErrorMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        gameHandler = server.getMatchesInLobby().get(0);
        gameID = gameHandler.getGameID();
        nickname = message.getNickname();
        server.getClientIDToConnection().put(clientID,this);
        server.getClientIDToGameHandler().put(clientID, gameHandler);

        try {
            outputStream.writeObject(new JoinMatchAckMessage(gameID));
            outputStream.writeObject(new AddedToGameMessage(nickname,false));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method called when the player decides to create a new match. It asks the player how many other players he wants in his
     * room and the nickname he wants to use. If all is correctly insert, a new GameHandler is created and the player is set
     * as host.
     */
    private void createMatch(CreateMatchAction message) {
        //TODO: create gameHandler using Json file
        //effective creation of the game
        gameHandler = new GameHandler(server,message.getGameSize());
        gameID = gameHandler.getGameID();
        nickname= message.getNickname();

        CreateMatchAckMessage createMatchAckMessage= new CreateMatchAckMessage(gameID, message.getGameSize());

        //setting all the maps and lists of the server with the new values just created for this game
        server.getGameIDToGameHandler().put(gameID,gameHandler);
        server.getMatchesInLobby().add(gameHandler);
        server.getClientIDToConnection().put(clientID,this);
        server.getClientIDToGameHandler().put(clientID, gameHandler);

        //setting the match creator as host
        gameHandler.setHost(this);
        isHost=true;
        AddedToGameMessage addedToGameMessage= new AddedToGameMessage(nickname,isHost);
        try {
            outputStream.writeObject(createMatchAckMessage);
            outputStream.writeObject(addedToGameMessage);
        } catch (IOException e) {
            e.printStackTrace();
            close();
        }
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
     */
    //TODO: to make this class we need to define the type of this class
    public void sendSocketMessage(Message message) {
       try {
            outputStream.reset();
            outputStream.writeObject(message);
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


    /**
     * Gets a message from the client and, depending on the its type, calls the right method to perform the action selected.
     * @param action: generic message sent from the client.
     */

    public void playerAction(Action action){
        Turn turn = gameHandler.getTurn();
        int actionPerformed= turn.getActionPerformed();
        boolean[] productions= turn.getProductions();
        if (action instanceof DevelopmentAction && actionPerformed==0) gameHandler.developmentAction( (DevelopmentAction) action);
        else if (action instanceof MarketDoubleWhiteToColorAction && actionPerformed==0)      gameHandler.marketSpecialAction((MarketDoubleWhiteToColorAction) action);
        else if (action instanceof MarketAction && actionPerformed==0) gameHandler.marketAction((MarketAction) action);
        else if (action instanceof ProductionAction && actionPerformed!=1 ) gameHandler.productionAction(action,productions);
        else if (action instanceof ActivateLeaderCardAction) gameHandler.activateLeaderCard(action);
        else if (action instanceof ViewDashboardAction)      gameHandler.viewDashboard(action);
        else if (action instanceof QuitAction && actionPerformed!=0) {
            turn.resetProductions();
            turn.setActionPerformed(0);
            gameHandler.getGame().changeTurn();
        }
        else if (actionPerformed==1)    sendString("You already did one of the main actions. Try with something else or end your turn");
        else if (actionPerformed==2 )    sendString("This turn you're activating your productions. You can either pass your turn or keep on activating them");
    }
}
