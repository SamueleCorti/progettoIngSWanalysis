package it.polimi.ingsw.Communication.client;


import it.polimi.ingsw.Communication.client.actions.*;
import it.polimi.ingsw.Communication.client.actions.RejoinMatchAction;
import it.polimi.ingsw.Communication.client.actions.mainActions.WhiteToColorAction;
import it.polimi.ingsw.Communication.server.messages.Message;
import it.polimi.ingsw.Communication.server.messages.Notificatios.DevelopmentNotification;
import it.polimi.ingsw.Communication.server.messages.Notificatios.MarketNotification;
import it.polimi.ingsw.Communication.server.messages.Notificatios.Notification;
import it.polimi.ingsw.Model.resource.ResourceType;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ClientSideSocket {
    /** IP of the server to connect */
    private final String serverAddress;

    /** Unique identifier for the game connected */
    private int gameID;

    /** Port of the server to connect */
    private final int serverPort;

    /** Class created to wait for messages from the server at every moment */
    private SocketObjectListener objectListener;

    /** Stream used to write actions to the server */
    private ObjectOutputStream outputStream;

    /** Stream used to read messages from the server */
    private ObjectInputStream inputStream;

    /** Stream used to read input from keyboard */
    private BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

    /** Class used to create action based on the keyboard input */
    private ActionParser actionParser;

    private boolean firstTurnDone = false, choosingResources= false;


    /** Constructor ConnectionSocket creates a new ConnectionSocket instance. */
    public ClientSideSocket(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.actionParser = new ActionParser();
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
            Socket socket;
            try {
                socket = new Socket(serverAddress, serverPort);
            } catch (SocketException | UnknownHostException e) {
                return false;
            }

            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
            //creating listeners for string and object messages from server
            //object messages
            objectListener = new SocketObjectListener(this ,inputStream);
            Thread thread1 = new Thread(objectListener);
            thread1.start();

            createOrJoinMatchChoice();
           System.out.println("we will now wait until the first turn done is gonna be true");
            while(!firstTurnDone){
            }
           /* System.out.println("the first turn has been done");
            loopRequest();*/
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
    public void initialize(int order,String leaderCardsPicked){
        System.out.println("The list of leader cards you have to choose from is:");
        System.out.println(leaderCardsPicked);
        System.out.println("Your order in this match is: "+order);
        try {
            System.out.println("Select the index of two leader card to discard   [e.g. setupdiscard 0 2]");
            Action action= new Action() {};
            while(!(action instanceof DiscardTwoLeaderCardsAction)){
                action= actionParser.parseInput(stdIn.readLine());
            }
            if (action!=null) {
                send(action);
            }
            if(order>3){
                ResourceType resourceType1, resourceType2;
                do {
                    System.out.println("Select the 1st of your two bonus resources to start with");
                    resourceType1 = actionParser.parseResource(stdIn.readLine());
                }while (resourceType1==null);
                do {
                    System.out.println("Select the 2nd of your two bonus resources to start with");
                    resourceType2 = actionParser.parseResource(stdIn.readLine());
                }while (resourceType2==null);
                action= new BonusResourcesAction(resourceType1, resourceType2);
                send(action);
            }
            else if(order>1){
                ResourceType resourceType=null;
                do {
                    System.out.println("Select your bonus resource to start with");
                    resourceType = actionParser.parseResource(stdIn.readLine());
                }while (resourceType==null);
                action= new BonusResourcesAction(resourceType);
                send(action);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("we will now do the loop request");
        loopRequest();
    }

    /**
     * Method used to transform every keyboard input into an action
     */
    private void loopRequest() {
        System.out.println("we're now reading from keyboard! type 'help' for the list of avaible commands");
        boolean isActive=true;
        while (isActive){
            if(!choosingResources){
                System.out.println("In loop request");
                try {
                    String keyboardInput = stdIn.readLine();
                    if(!keyboardInput.equals("wtcchoice")){
                        Action actionToSend = this.actionParser.parseInput(keyboardInput);
                        if(actionToSend!=null&& !((actionToSend instanceof BonusResourcesAction) || actionToSend instanceof DiscardTwoLeaderCardsAction)) {
                            System.out.println("We are sending the message");
                            send(actionToSend);
                            System.out.println("We sent the message");
                        }else{
                            System.out.println("the message inserted was not recognized; try again");
                        }
                    }
                } catch (IOException e){
                    isActive=false;
                }
            }
            else{
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void whiteToColorChoices(int numOfBlanks, ResourceType type1, ResourceType type2){
        choosingResources= true;
        ArrayList<ResourceType> resources= new ArrayList<>();
        System.out.println("You have two white to color leader cards active. You now have to choose "+numOfBlanks+ " resources between "+ type1+" and "+type2);
        System.out.println("To proceed insert [wtcchoice]]");
        for(int i=0;i<numOfBlanks;i++){
            System.out.println("insert resouce n. "+i+ " :");
            String line="";
            do{
                try {
                    line= stdIn.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(actionParser.parseResource(line)!=type1 && actionParser.parseResource(line)!=type2)
                    System.out.println("You selected an invalid resource.You have to choose between "+ type1+" and "+type2);
            }while (actionParser.parseResource(line)!=type1 && actionParser.parseResource(line)!=type2);
            resources.add(actionParser.parseResource(line));
        }
        String line= "You selected ";
        for(int i=0;i<numOfBlanks;i++) line+=resources.get(i)+" ";
        line+= "to substitute blanks";
        System.out.println(line);
        send(new WhiteToColorAction(resources));
        choosingResources= false;
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
            System.out.println("we've sent the action to the server");
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
            string="This turn "+notification.getNickname()+ " has decided to take the";
            if (notification.isRow()) string+=" row ";
            if (!notification.isRow()) string+=" column ";
            string+= " number"+ notification.getIndex();
        }
        else if (message instanceof DevelopmentNotification){
            DevelopmentNotification notification= (DevelopmentNotification) message;
            string="This turn "+notification.getNickname()+ " has decided to buy the "+ notification.getColor()+ " level "+notification.getLevel()+" development card";
            string+= " number"+ notification.getIndex();
        }
        System.out.println(string);
    }
}
