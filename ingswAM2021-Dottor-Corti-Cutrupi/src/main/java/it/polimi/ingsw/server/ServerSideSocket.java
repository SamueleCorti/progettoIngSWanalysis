package it.polimi.ingsw.server;

import it.polimi.ingsw.client.actions.*;
import it.polimi.ingsw.client.actions.initializationActions.BonusResourcesAction;
import it.polimi.ingsw.client.actions.initializationActions.DiscardLeaderCardsAction;
import it.polimi.ingsw.client.actions.initializationActions.NotInInitializationAnymoreAction;
import it.polimi.ingsw.client.actions.matchManagementActions.CreateMatchAction;
import it.polimi.ingsw.client.actions.matchManagementActions.JoinMatchAction;
import it.polimi.ingsw.client.actions.matchManagementActions.NotInLobbyAnymore;
import it.polimi.ingsw.client.actions.matchManagementActions.RejoinMatchAction;
import it.polimi.ingsw.client.actions.mainActions.*;
import it.polimi.ingsw.client.actions.secondaryActions.*;
import it.polimi.ingsw.server.messages.gameCreationPhaseMessages.*;
import it.polimi.ingsw.controller.GameHandler;
import it.polimi.ingsw.server.messages.rejoinErrors.AllThePlayersAreConnectedMessage;
import it.polimi.ingsw.server.messages.rejoinErrors.GameWithSpecifiedIDNotFoundMessage;
import it.polimi.ingsw.server.messages.rejoinErrors.NicknameNotInGameMessage;
import it.polimi.ingsw.server.messages.Message;
import it.polimi.ingsw.server.messages.printableMessages.IncorrectPhaseMessage;
import it.polimi.ingsw.server.messages.printableMessages.MessageReceivedFromServerMessage;
import it.polimi.ingsw.server.messages.printableMessages.NotYourTurnMessage;

import java.io.*;
import java.net.Socket;

public class ServerSideSocket implements Runnable {
    /** Unique socket used to communicate to the related client */
    private final Socket socket;

    /** Server which contains this ServerSideSocket*/
    private final Server server;

    /** Boolean set as true if the client related asked to create a new game, false if he asked to join/rejoin */
    private boolean isHost;

    private boolean clientDisconnectedDuringHisTurn = false;
    private boolean clientRejoinedAfterInitializationPhase=false;

    /**
     * GameHandler is the controller of the game that the client is playing. It is shared between all the players
     * connected to the same game.
     */
    private GameHandler gameHandler;

    /** Stream to read the actions sent by the client */
    private ObjectInputStream inputStream;

    /** Stream to write the messages and send them to the client */
    private ObjectOutputStream outputStream;

    /** Unique identifier of the client associated */
    private Integer clientID;

    /** Contains the order in the game of the client associated */
    private int order;

    /** Unique identifier for the GameHandler of this ServerSideSocket */
    private int gameID;

    /** Identifier for the client in the game (nickname is unique in a game) */
    private String nickname;

    /** Boolean set as true if the connection is still active, false if it is not */
    private boolean active;

    /** Boolean set as true if the client associated is still in lobby phase */
    private boolean stillInLobby=true;

    private int lobbySize;



    /**
     * Method isActive returns the state of this SocketClientConnection object.
     *
     * @return the active (type boolean) of this SocketClientConnection object.
     */
    public synchronized boolean isActive() {
        return active;
    }

    public boolean isClientDisconnectedDuringHisTurn() {
        return clientDisconnectedDuringHisTurn;
    }

    public void setClientDisconnectedDuringHisTurn(boolean clientDisconnectedDuringHisTurn) {
        this.clientDisconnectedDuringHisTurn = clientDisconnectedDuringHisTurn;
    }

    public String getNickname() {
        return nickname;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
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
        try {
            if(active) {
                socket.close();
                server.unregisterClient(clientID, this);
                active = false;
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Method readFromStream reads an action from the input stream. It is looped for all the time the game has started
     * and is not finished yet
     */
    public synchronized void readFromStream(){
        Action action  = null;
        try {
            action = (Action) inputStream.readObject();
        } catch (IOException e) {
            close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //case correct request
        if((this.equals(gameHandler.getGame().getActivePlayer()) &&
                ((action instanceof MainAction)|| (action instanceof SecondaryAction)))) {
            sendSocketMessage(new MessageReceivedFromServerMessage());
            ((ExecutableAction) action).execute(gameHandler);
            //gameHandler.playerAction(action,nickname);
        }

        else if(action instanceof ViewDashboardAction){
            sendSocketMessage(new MessageReceivedFromServerMessage());
            ((ViewDashboardAction) action).execute(gameHandler,clientID);
        }

        else if(action instanceof ViewGameboardAction){
            sendSocketMessage(new MessageReceivedFromServerMessage());
            ((ViewGameboardAction) action).execute(gameHandler,clientID);
        }

        //case it's not player's turn
        else if(!this.equals(gameHandler.getGame().getActivePlayer())){
            try {
                outputStream.writeObject(new NotYourTurnMessage());
            } catch (IOException e) {
                close();
            }
        }

        //case wrong moment to request for that action
        else{
            try {
                outputStream.writeObject(new IncorrectPhaseMessage());
            } catch (IOException e) {
                close();
            }
        }
    }

    public void initializePhase() {
        Action action  = null;
        while(!(action instanceof DiscardLeaderCardsAction)&&active){
            try {
                action = (Action) inputStream.readObject();
            } catch (IOException e) {
                close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        gameHandler.playerAction(action,nickname);

        if(order>1){
            while(!(action instanceof BonusResourcesAction)&& active){
                try {
                    action = (Action) inputStream.readObject();
                } catch (IOException e) {
                    close();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            if(active)gameHandler.playerAction(action,nickname);
        }
        if(active) {
            gameHandler.newInitialization(nickname);
        }
    }

    /**
     * Method run is the overriding runnable class method, which is called on a new client connection.
     *
     * @see Runnable#run()
     */
    @Override
    public void run() {

        //Creates the socket correctly
        createNewConnection();

        //Loops until the game ends or the player disconnects
        while(active) {

            //Receives an action with the choice of the client and handles it
            createOrJoinMatchChoice();

            try {
                //While the client is in lobby, this method waits for the message saying that lobby ended; if client sends
                // another action, replies that he can't do the required action
                while (stillInLobby && active) {
                    Object actionReceived = inputStream.readObject();
                    if (actionReceived instanceof NotInLobbyAnymore) {
                        stillInLobby = false;
                    } else {
                        outputStream.writeObject(new IncorrectPhaseMessage());
                    }
                }

                //Initialization phase
                if(!clientRejoinedAfterInitializationPhase) initializePhase();
                clientRejoinedAfterInitializationPhase=false;

                boolean stillInInitialization=true;
                while (stillInInitialization && active) {
                    Object actionReceived = inputStream.readObject();
                    if (actionReceived instanceof NotInInitializationAnymoreAction) {
                        stillInInitialization = false;
                    } else {
                        outputStream.writeObject(new IncorrectPhaseMessage());
                    }
                }

                //While the game has started, server is always ready to receive actions from the client and handle them
                while (active) {
                    readFromStream();
                }

            } catch (IOException e) {
                close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
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
                //Client asked to create a new match
                createMatch((CreateMatchAction) line);
            }
            else if(line instanceof JoinMatchAction){
                //Client asked to join a random match
                joinMatch((JoinMatchAction) line);
            }
            else if(line instanceof RejoinMatchAction){
                //Client has asked to rejoin a game, specifying gameID and his old nickname
                rejoinMatch((RejoinMatchAction) line);
            }

        } catch (IOException e) {
            close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method called when user wants to rejoin a game he was previously connected to. It asks the client to insert the id of
     * that game and the nickname he was using in that.
     */
    private void rejoinMatch(RejoinMatchAction rejoinRequest) {
        int idToSearch = rejoinRequest.getGameID();
        nickname = rejoinRequest.getNickname();


        //case no match found with the specified ID
        if(server.getGameIDToGameHandler().size()==0 || server.getGameHandlerByGameID(idToSearch)==null){
            try {
                outputStream.writeObject(new GameWithSpecifiedIDNotFoundMessage(idToSearch));
                createOrJoinMatchChoice();
            } catch (IOException e) {
                close();
            }
        }

        //CORRECT CASE PATH: case match found
        else {
            gameHandler = server.getGameHandlerByGameID(idToSearch);

            //but all the players are connected
            if(gameHandler.allThePlayersAreConnected()) {
                try {
                    outputStream.writeObject(new AllThePlayersAreConnectedMessage());
                    createOrJoinMatchChoice();
                } catch (IOException e) {
                    close();
                }
            }

            //CORRECT CASE PATH: there is at least one left spot
            else{

                //CORRECT CASE PATH: User has insert a valid nickname (there is an open spot in the game with the specified name)
                if(gameHandler.isNicknameAlreadyTaken(nickname) &&
                        gameHandler.getClientIDToConnection().get(gameHandler.getNicknameToClientID().get(nickname))==null){
                    System.out.println("Rejoining player "+nickname+" to game n."+idToSearch);
                    gameHandler.reconnectPlayer(this, nickname);
                    if(gameHandler.isStarted() && gameHandler.getNicknameToHisGamePhase().get(nickname)==2){
                        clientRejoinedAfterInitializationPhase=true;
                        clientDisconnectedDuringHisTurn = true;
                    }
                }


                //invalid nickname
                else {
                    try {
                        outputStream.writeObject(new NicknameNotInGameMessage());
                        createOrJoinMatchChoice();
                    } catch (IOException e) {
                        close();
                    }
                }
            }
        }
    }

    /**
     * Method called when a player wants to join a match. The match he will join is chosen by the server, that looks if there is
     * a match still in lobby, and if there is, adds the player to it, asking to insert a nickname that is not used by any
     * other player in the same lobby.
     */
    private void joinMatch(JoinMatchAction message) {

        //there is no match available
        if(server.getMatchesInLobby().size()==0){
            try {
                outputStream.writeObject(new JoinMatchErrorMessage());
                createOrJoinMatchChoice();
                return;
            } catch (IOException e) {
                close();
            }
        }

        nickname = message.getNickname();
        //the nickname selected is already used in the game
        if(server.getMatchesInLobby().get(0).isNicknameAlreadyTaken(nickname)){
            try {
                outputStream.writeObject(new JoinMatchNameAlreadyTakenError());
                createOrJoinMatchChoice();
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //match found
        //Setting all the attributes to the new values
        gameHandler = server.getMatchesInLobby().get(0);
        gameID = gameHandler.getGameID();
        server.getClientIDToConnection().put(clientID,this);
        server.getClientIDToGameHandler().put(clientID, gameHandler);

        try {
            //notifying the client that the Join request has been approved and he has been connected to a game
            outputStream.writeObject(new JoinMatchAckMessage(gameID,gameHandler.getTotalPlayers()));
            outputStream.writeObject(new AddedToGameMessage(nickname,false));
        } catch (IOException e) {
            close();
        }
        try {
            //adds the player to the game
            gameHandler.lobby(clientID,this,nickname);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
            Thread.currentThread().interrupt();
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
        lobbySize = message.getGameSize();

        CreateMatchAckMessage createMatchAckMessage= new CreateMatchAckMessage(gameID, lobbySize);

        //setting all the maps and lists of the server with the new values just created for this game
        server.getGameIDToGameHandler().put(gameID,gameHandler);
        server.getMatchesInLobby().add(gameHandler);
        server.getClientIDToConnection().put(clientID,this);
        server.getClientIDToGameHandler().put(clientID, gameHandler);

        //setting the match creator as host
        gameHandler.setHost(this);
        isHost=true;
        AddedToGameMessage addedToGameMessage= new AddedToGameMessage(nickname,true);
        try {
            //notifying the client that the Creation request has been approved and a new game has been created
            outputStream.writeObject(createMatchAckMessage);
            outputStream.writeObject(addedToGameMessage);
        } catch (IOException e) {
            close();
        }
        try {
            //Adding the player to the game
            gameHandler.lobby(clientID,this,nickname);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
            Thread.currentThread().interrupt();
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
        }
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
     * Method getClientID returns the clientID of this SocketClientConnection object.
     *
     * @return the clientID (type Integer) of this SocketClientConnection object.
     */
    public Integer getClientID() {
        return clientID;
    }

    /**
     * @return true if the player is the host of the game
     */
    public boolean isHost() {
        return isHost;
    }

    /**
     * Sets host to the new value (usually used when a player becomes the new host of the game)
     */
    public void setHost(boolean host) {
        isHost = host;
    }

    /**
     * Gets a message from the client and, depending on the its type, calls the right method to perform the action selected.
     * @param : generic message sent from the client.
     */

    /*public void playerAction(Action action)  {
        if(!clientRejoinedAfterInitializationPhase && clientDisconnectedDuringHisTurn) {
            gameHandler.getTurn().setActionPerformed(gameHandler.getNicknameToHisTurnPhase().get(nickname));
            clientDisconnectedDuringHisTurn=false;
        }
        Player player = gameHandler.getGame().getGameBoard().getPlayerFromNickname(nickname);
        if (action instanceof DiscardLeaderCardsAction) gameHandler.getGame().getGameBoard().getPlayerFromNickname(nickname).discardLeaderCards(((DiscardLeaderCardsAction) action).getIndexes());
        else if(action instanceof BonusResourcesAction) gameHandler.startingResources((BonusResourcesAction) action, player);
        else if(gameHandler.getTurn().getActionPerformed()==3){
            if(action instanceof DiscardExcedingDepotAction) gameHandler.discardDepot((DiscardExcedingDepotAction) action,player);
            else {
                sendSocketMessage(new YouMustDeleteADepotFirst());
                gameHandler.printDepots(player);
            }
        }
        else if(gameHandler.getTurn().getActionPerformed()==4){
            if(action instanceof DiscardExcedingResourcesAction){
                gameHandler.discardExtraResources((DiscardExcedingResourcesAction) action, player);
            }
            else {
                sendSocketMessage(new YouMustDiscardResourcesFirst());
                gameHandler.printDepots(player);
            }
        }
        else if(gameHandler.getTurn().getActionPerformed()==5){
            if(action instanceof WhiteToColorAction){
                gameHandler.marketSpecialAction((WhiteToColorAction) action, player);
            }
            else if(action instanceof ViewDashboardAction){
                gameHandler.viewDashboard((ViewDashboardAction) action);
            }
            else {
                gameHandler.viewDashboard(new ViewDashboardAction(0));
                sendSocketMessage(new YouMustSelectWhiteToColorsFirst());
            }
        }
        else if(action instanceof DiscardExcedingResourcesAction && gameHandler.getTurn().getActionPerformed()!=4){
            sendSocketMessage(new IncorrectPhaseMessage());
        }
        else if(action instanceof DiscardExcedingDepotAction && gameHandler.getTurn().getActionPerformed()!=3){
            sendSocketMessage(new IncorrectPhaseMessage());
        }
        else if(action instanceof DevelopmentFakeAction){
            if(gameHandler.developmentFakeAction( (DevelopmentFakeAction) action, player))
                gameHandler.sendAllExceptActivePlayer(new DevelopmentNotification(((DevelopmentFakeAction) action)
                        .getIndex(),((DevelopmentFakeAction) action).getCardLevel(), ((DevelopmentFakeAction) action)
                        .getColor(),player.getNickname()));
        }
        else if (action instanceof DevelopmentAction && gameHandler.getTurn().getActionPerformed()==0) {
            if(gameHandler.developmentAction( (DevelopmentAction) action, player))
                gameHandler.sendAllExceptActivePlayer(new DevelopmentNotification(((DevelopmentAction) action)
                        .getIndex(),((DevelopmentAction) action).getCardLevel(), ((DevelopmentAction) action)
                        .getColor(),player.getNickname()));
        }
        else if (action instanceof MarketAction && gameHandler.getTurn().getActionPerformed()==0) marketAction((MarketAction) action, player);
        else if (action instanceof ProductionAction && gameHandler.getTurn().getActionPerformed()!=1 )  gameHandler.productionAction(action, nickname);
        else if (action instanceof ActivateLeaderCardAction) gameHandler.activateLeaderCard(action, player);
        else if (action instanceof TestAction) gameHandler.test(player);
        else if (action instanceof PapalInfoAction) gameHandler.papalInfo(player);
        else if (action instanceof ViewDashboardAction)      gameHandler.viewDashboard((ViewDashboardAction) action);
        else if (action instanceof ViewLorenzoAction)       gameHandler.viewLorenzo(action);
        else if (action instanceof InfiniteResourcesAction) gameHandler.addInfiniteResources();
        else if (action instanceof PrintResourcesAction)    gameHandler.printAllResources(player);
        else if(action instanceof EndTurn){
            //sendSocketMessage(new ProductionNotification(gameHandler.getTurn().getProductions()));
            if(gameHandler.getTurn().getActionPerformed()==1 || gameHandler.getTurn().getActionPerformed()==2) {
                gameHandler.getNicknameToHisTurnPhase().replace(nickname,0);
                gameHandler.endTurn();
            }
            else sendSocketMessage(new YouMustDoAMainActionFirst());
        }
        else if(action instanceof PrintMarketAction)  gameHandler.printMarket();
        else if(action instanceof ViewDepotsAction)     gameHandler.printDepots(player);
        else if(action instanceof PapalPositionCheckAction) gameHandler.printPapalPosition(player);
        else if (action instanceof ViewGameboardAction) gameHandler.viewGameBoard();
        else if (action instanceof DiscardLeaderCard) gameHandler.discardLeaderCard((DiscardLeaderCard)action, nickname);
        else if(action instanceof SurrendAction) gameHandler.surrend();
        else if (gameHandler.getTurn().getActionPerformed()==1)    sendSocketMessage(new MainActionAlreadyDoneMessage());
        else if (gameHandler.getTurn().getActionPerformed()==2 )    sendSocketMessage(new YouActivatedProductionsInThisTurn());
    }*/

    /*public void marketPreMove(MarketAction action, Player player){
        int numOfBlank = 0;
        try {
            numOfBlank = gameHandler.getGame().getGameBoard().getMarket().checkNumOfBlank((action.isRow()), action.getIndex());
        } catch (OutOfBoundException e) {
            e.printStackTrace();
        }
        gameHandler.marketAction(action, nickname);
        if(gameHandler.twoWhiteToColorCheck(player) && numOfBlank!=0){
            sendSocketMessage(new WhiteToColorMessage(numOfBlank));
            gameHandler.getTurn().setActionPerformed(5);
        }
    }*/

    public boolean isClientRejoinedAfterInitializationPhase() {
        return clientRejoinedAfterInitializationPhase;
    }

    public boolean clientDisconnectedDuringHisTurn() {
        return clientDisconnectedDuringHisTurn;
    }
}
