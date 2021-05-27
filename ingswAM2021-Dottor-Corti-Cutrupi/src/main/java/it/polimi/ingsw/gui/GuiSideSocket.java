package it.polimi.ingsw.gui;


import it.polimi.ingsw.client.actions.*;
import it.polimi.ingsw.client.actions.initializationActions.BonusResourcesAction;
import it.polimi.ingsw.client.actions.initializationActions.DiscardLeaderCardsAction;
import it.polimi.ingsw.client.actions.matchManagementActions.CreateMatchAction;
import it.polimi.ingsw.client.actions.matchManagementActions.JoinMatchAction;
import it.polimi.ingsw.client.actions.matchManagementActions.RejoinMatchAction;
import it.polimi.ingsw.exception.NicknameAlreadyTakenException;
import it.polimi.ingsw.exception.NoGameFoundException;
import it.polimi.ingsw.server.messages.Message;
import it.polimi.ingsw.server.messages.gameCreationPhaseMessages.JoinMatchErrorMessage;
import it.polimi.ingsw.server.messages.gameCreationPhaseMessages.JoinMatchNameAlreadyTakenError;
import it.polimi.ingsw.server.messages.jsonMessages.LeaderCardMessage;
import it.polimi.ingsw.server.messages.notifications.DevelopmentNotification;
import it.polimi.ingsw.server.messages.notifications.MarketNotification;
import it.polimi.ingsw.server.messages.PlayerWonSinglePlayerMatch;
import it.polimi.ingsw.model.resource.ResourceType;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Locale;

public class GuiSideSocket {
    private GUI gui;

    /** IP of the server to connect */
    private final String serverAddress;

    /** Unique identifier for the game connected */
    private int gameID;

    private Socket socket;

    /** Port of the server to connect */
    private final int serverPort;

    /** Class created to wait for messages from the server at every moment */
    private SocketObjectListenerForGUI objectListener;

    /** Stream used to write actions to the server */
    private ObjectOutputStream outputStream;

    /** Stream used to read messages from the server */
    private ObjectInputStream inputStream;

    /** Stream used to read input from keyboard */
    private final BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

    /** Class used to create action based on the keyboard input */
    private final ActionParserForGUI actionParser;

    private int leaderCardsGiven;

    private boolean stillInitializing=true;

    private boolean firstTurnDone = false, isWaitingForOtherInitialization=false, choosingResources= false;
    private int numOfBlanks;
    private int leaderCardsKept;
    private int order;


    /** Constructor ConnectionSocket creates a new ConnectionSocket instance. */
    public GuiSideSocket(String serverAddress, int serverPort, GUI gui) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.actionParser = new ActionParserForGUI(this);
        this.gui = gui;
    }


    public boolean setup() throws NicknameAlreadyTakenException, NoGameFoundException {
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
            objectListener = new SocketObjectListenerForGUI(this ,inputStream);
            Thread thread1 = new Thread(objectListener);
            thread1.start();

            return true;
        } catch (IOException e) {
            System.err.println("Error during socket configuration! Application will now close.");
            System.exit(0);
            return false;
        }
    }

    /**
     * Method used to initialize the attributes of the player dashboard, based on his turn order. Method is used to choose
     * which leader cards to discard and eventually which extra resources to start with
     * @param order is the turn order of the player
     */
    public void initialize(int order,int leaderCardsKept,int leaderCardsGiven){
        stillInitializing=false;
        this.leaderCardsKept = leaderCardsKept;
        this.leaderCardsGiven = leaderCardsGiven;
        this.order = order;
        try {
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
}