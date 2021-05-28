package it.polimi.ingsw.client.shared;


import it.polimi.ingsw.client.actions.*;
import it.polimi.ingsw.client.actions.initializationActions.BonusResourcesAction;
import it.polimi.ingsw.client.actions.initializationActions.DiscardLeaderCardsAction;
import it.polimi.ingsw.client.actions.matchManagementActions.CreateMatchAction;
import it.polimi.ingsw.client.actions.matchManagementActions.JoinMatchAction;
import it.polimi.ingsw.client.actions.matchManagementActions.RejoinMatchAction;
import it.polimi.ingsw.client.cli.ActionParserForCLI;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.utility.LeaderCardForGUI;
import it.polimi.ingsw.model.resource.*;
import it.polimi.ingsw.server.messages.Message;
import it.polimi.ingsw.server.messages.initializationMessages.CardsToDiscardMessage;
import it.polimi.ingsw.server.messages.jsonMessages.LeaderCardMessage;
import it.polimi.ingsw.server.messages.notifications.DevelopmentNotification;
import it.polimi.ingsw.server.messages.notifications.MarketNotification;
import it.polimi.ingsw.server.messages.PlayerWonSinglePlayerMatch;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Locale;

public class ClientSideSocket {
    private GUI gui;

    /** IP of the server to connect */
    private final String serverAddress;

    /** Unique identifier for the game connected */
    private int gameID;

    private Socket socket;

    /** Port of the server to connect */
    private final int serverPort;

    /** Class created to wait for messages from the server at every moment */
    private SocketListener objectListener;

    /** Stream used to write actions to the server */
    private ObjectOutputStream outputStream;

    /** Stream used to read messages from the server */
    private ObjectInputStream inputStream;

    /** Stream used to read input from keyboard */
    private final BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

    /** Class used to create action based on the keyboard input */
    private final ActionParserForCLI actionParser;

    private boolean guiCase,firstTurnDone = false, isWaitingForOtherInitialization=false, choosingResources= false;
    private int numOfBlanks,order;
    private int leaderCardsKept,leaderCardsGiven;
    private boolean stillInitializing=true;


    /** Constructor ConnectionSocket creates a new ConnectionSocket instance. */
    public ClientSideSocket(String serverAddress, int serverPort,boolean isGui, GUI gui) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.actionParser = new ActionParserForCLI(this);
        this.guiCase = isGui;
        this.gui = gui;
    }

    public boolean isGuiCase() {
        return guiCase;
    }

    /**
     * Method setup initializes a new socket connection and handles the nickname-choice response. It
     * loops until the server confirms the successful connection (with no nickname duplication and
     * with a correctly configured match lobby).
     *
     * @return boolean true if connection is successful, false otherwise.
     */
    public boolean setup(){
        try {
            System.out.println("Configuring socket connection...");
            System.out.println("Opening a socket server communication on port "+ serverPort+ "...");
            try {
                socket = new Socket(serverAddress, serverPort);
            } catch (SocketException | UnknownHostException e) {
                return false;
            }

            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());

            //creating listener to read server messages
            objectListener = new SocketListener(this ,inputStream);
            Thread thread1 = new Thread(objectListener);
            thread1.start();

            if(!guiCase) {
                createOrJoinMatchChoice();
                while (!firstTurnDone) {
                }
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error during socket configuration! Application will now close.");
            System.exit(0);
            return false;
        }
    }

    public void initializeForGUI(int order,int leaderCardsKept,int leaderCardsGiven){
        this.leaderCardsKept = leaderCardsKept;
        this.leaderCardsGiven = leaderCardsGiven;
        this.order = order;
    }


    /**
     * Method used to initialize the attributes of the player dashboard, based on his turn order. Method is used to choose
     * which leader cards to discard and eventually which extra resources to start with
     * @param order is the turn order of the player
     */
    public void initialize(int order,int leaderCardsKept,int leaderCardsGiven){
        this.leaderCardsKept = leaderCardsKept;
        this.leaderCardsGiven = leaderCardsGiven;
        this.order = order;
        try {
            System.out.println("\nYou have to discard " + (leaderCardsGiven-leaderCardsKept) + " cards");
            System.out.println("Select the indexes of the leader cards to discard [e.g. discard 1 3]");
            Action action= new Action() {};
            boolean bool = false;
            boolean error = false;
            while(bool==false) {
                error = false;
                action = actionParser.parseInput(stdIn.readLine());
                if (action instanceof DiscardLeaderCardsAction) {
                    DiscardLeaderCardsAction tempaction;
                    tempaction = (DiscardLeaderCardsAction) action;
                    if(tempaction.getIndexes().size()==leaderCardsGiven-leaderCardsKept){
                        bool = true;
                    }else{
                        System.out.println("Incorrect number of indexes, try again");
                    }
                    for (Integer num: tempaction.getIndexes()) {
                        if (num < 0 || num > leaderCardsGiven) {
                            System.out.println("The indexes must be between 1 and " + leaderCardsGiven);
                            error = true;
                            break;
                        }
                    }
                    if(error==true){
                        bool=false;
                    }
                }
            }
            send(action);
            if(order>3){
                ResourceType resourceType1, resourceType2;
                do {
                    System.out.println("Select the 1st of your two bonus resources to start with");
                    resourceType1 = actionParser.parseResource(stdIn.readLine());
                    if(resourceType1==null) System.out.println("Insert a valid resource");
                }while (resourceType1==null);
                do {
                    System.out.println("Select the 2nd of your two bonus resources to start with");
                    resourceType2 = actionParser.parseResource(stdIn.readLine());
                    if(resourceType2==null) System.out.println("Insert a valid resource");
                }while (resourceType2==null);
                action= new BonusResourcesAction(resourceType1, resourceType2);
                send(action);
            }
            else if(order>1){
                ResourceType resourceType;
                do {
                    System.out.println("Select your bonus resource to start with");
                    resourceType = actionParser.parseResource(stdIn.readLine());
                    if(resourceType==null) System.out.println("Insert a valid resource");
                }while (resourceType==null);
                action= new BonusResourcesAction(resourceType);
                send(action);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Your initialization is complete, wait for the other players to finish theirs and the game " +
                "will start");
        loopIgnoreInputs();
        //loopRequest();
    }

    private void loopIgnoreInputs() {
        isWaitingForOtherInitialization=true;
        while (isWaitingForOtherInitialization){
            try {
                String keyboardInput = stdIn.readLine();
                if(isWaitingForOtherInitialization ) {
                    System.out.println("You can't do any move at the moment, wait for the other players to end their initialization " +
                            "phase");
                }
                else{
                    Action actionToSend = this.actionParser.parseInput(keyboardInput);
                    if(actionToSend!=null&& !((actionToSend instanceof BonusResourcesAction) || actionToSend instanceof DiscardLeaderCardsAction)) {
                        send(actionToSend);
                    }else{
                        System.out.println("the message inserted was not recognized; try again");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method used to transform every keyboard input into an action
     */
    public void loopRequest() {
        isWaitingForOtherInitialization=false;
        System.out.println("we're now reading from keyboard! type 'help' for the list of avaible commands");
        boolean isActive=true;
        while (isActive){
            try {
                String keyboardInput = stdIn.readLine();

                Action actionToSend = this.actionParser.parseInput(keyboardInput);
                if(actionToSend!=null&& !((actionToSend instanceof BonusResourcesAction) || actionToSend instanceof DiscardLeaderCardsAction)) {
                    send(actionToSend);
                }else{
                    System.out.println("the message inserted was not recognized; try again");
                }

            } catch (IOException e){
                isActive=false;
                close();
            }
        }
    }

    public void whiteToColorChoices(int numOfBlanks){
        this.numOfBlanks=numOfBlanks;
        System.out.println("You have two white to color leader cards active. You now have to choose "+numOfBlanks+ " indexes of cards to activate.");
        System.out.println("To proceed insert wtcchoice followed by the list of resource you want to transform the blanks to [e.g. wtcchoice coin stone]");
    }

    /**
     * Method called after the creation of the socket. It ask the player if he want to create, join or rejoin a match,
     * and calls other methods based on the player's choice
     */
    public void createOrJoinMatchChoice(){
        try {
            String line;
            do {
                System.out.println("Do you want to create a new match or join/rejoin an already created one?");
                line = stdIn.readLine().toLowerCase(Locale.ROOT);
                if(!line.equals("create") && !line.equals("join") && !line.equals("rejoin")) System.out.println("Please select either join, rejoin, or create");
            }while (!line.equals("create") && !line.equals("join") && !line.equals("rejoin"));

            //dividing the possible choices in their respective method
            switch (line){
                case "create":
                    createMatch();
                    break;
                case "join":
                    joinMatch();
                    break;
                case "rejoin":
                    rejoinMatch();
                    break;
            }
        } catch (IOException e) {
            System.err.println("Error during the choice between Create and Join! Application will now close. ");
            System.exit(0);
        }
    }


    /**
     * Method called when the player wants to join a random match. It asks for a nickname to use in the game
     */
    private void joinMatch() {
        try {
            String nickname;
            do {
                System.out.println("Select a nickname: ");
                nickname = stdIn.readLine();
                if(nickname==null || nickname.equals(""))   System.out.println("Your nickname is invalid");
            }while (nickname==null || nickname.equals(""));
            outputStream.writeObject(new JoinMatchAction(nickname));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Method called when a player wants too rejoin a match. It asks for the gameID of the game he wants to rejoin and the
     * nickname he was using in that game
     */
    private void rejoinMatch() {
        System.out.println("What's the ID of the game you want to rejoin?");
        try {
            int idToRejoin = Integer.parseInt(stdIn.readLine());
            System.out.println("What was your nickname in that game?");
            String name = stdIn.readLine();
            outputStream.writeObject(new RejoinMatchAction(idToRejoin,name));
        } catch (NumberFormatException e) {
            System.out.println("You must insert a number!!!");
            rejoinMatch();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * Method called when the player wants to create a new match. It asks to insert the nickname he wants to use in the game
     * and how many players he wants in his game
     */
    private void createMatch(){
        int gameSize;
        String nickname;
        try {
            do {
                System.out.println("How many players do you want this game to have? [1-4] ");
                gameSize = Integer.parseInt(stdIn.readLine());
                if(gameSize<1 || gameSize>4) System.out.println("Please select a number between 1 and 4");
            }while (gameSize<1 || gameSize>4);
            do {
                System.out.println("Select a nickname: ");
                nickname = stdIn.readLine();
                if(nickname==null || nickname.equals(""))   System.out.println("Your nickname is invalid");
            }while (nickname==null || nickname.equals(""));

        //TODO: Json file
        ArrayList<String> jsonSetting= new ArrayList<>();
        CreateMatchAction createMatchAction= new CreateMatchAction(gameSize, nickname, "JSON");
            outputStream.writeObject(createMatchAction);
        } catch (NumberFormatException e) {
            System.out.println("You must insert a number!!!");
            createMatch();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method send sends a new message to the server
     *
     */
    public void send(Action action) {
        try {
            outputStream.reset();
            outputStream.writeObject(action);
            outputStream.flush();
        } catch (IOException e) {
            System.err.println("Error during send process.");
            System.err.println(e.getMessage());
        }
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public void manageNotification(Message message) {
        String string="";
        if (message instanceof MarketNotification){
            MarketNotification notification= (MarketNotification) message;
            string="This turn "+notification.getNickname()+ " has decided to take resources from market, in particular he chose";
            if (notification.isRow()) string+=" row ";
            if (!notification.isRow()) string+=" column ";
            string+= "number "+ notification.getIndex()+ "\nHere is the new market: \n";
        }
        else if (message instanceof DevelopmentNotification){
            DevelopmentNotification notification= (DevelopmentNotification) message;
        }
        System.out.println(string);
    }

    public int getNumOfBlanks() {
        return numOfBlanks;
    }

    public int getLeaderCardsKept() {return leaderCardsKept;}

    public void LorenzoWon() {
        System.out.println("Lorenzo Il Magnifico wins! The game has ended");
        close();
    }

    public void close(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void playerWonSinglePlayerMatch(PlayerWonSinglePlayerMatch message) {
        System.out.println("You won the match with "+ message.getVictoryPoints() +" points! The game has ended");
        close();
    }

    public void changeStage(String newStage){
        gui.changeStage(newStage);
    }

    public int cardsToDiscard(){
        return leaderCardsGiven-leaderCardsKept;
    }

    public void addAlert(String header, String context){
        gui.addAlert(header,context);
    }

    public boolean isStillInitializing() {
        return stillInitializing;
    }

    public void addCardToTable(LeaderCardForGUI card) {
        gui.addCardToTable(card);
    }

    public int getOrder() {
        return order;
    }

    public void addCardToTable(CardsToDiscardMessage message) {

        for(LeaderCardMessage leaderCardMessage: message.getMessages()){
            LeaderCardForGUI card = new LeaderCardForGUI(leaderCardMessage);
            addCardToTable(card);
        }
    }
}
