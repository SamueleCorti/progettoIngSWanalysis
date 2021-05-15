package it.polimi.ingsw.Communication.client;


import it.polimi.ingsw.Communication.client.actions.*;
import it.polimi.ingsw.Communication.client.actions.RejoinMatchAction;
import it.polimi.ingsw.Player;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ClientSideSocket {
    private final String serverAddress;
    private int gameID;
    private final int serverPort;
    SocketObjectListener objectListener;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private PrintWriter out;
    private BufferedReader in;
    private BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
    private ActionParser actionParser;
    private Player player;
    private boolean firstTurnDone= false;


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

            // string messages
            /*stringListener = new SocketStringListener(socket, in, this);
            Thread thread2 = new Thread(stringListener);
            thread2.start();*/

            createOrJoinMatchChoice();
            while(!firstTurnDone){
            }
            loopRequest();
            return true;
        } catch (IOException e) {
            System.err.println("Error during socket configuration! Application will now close.");
            System.exit(0);
            return false;
        }
    }

    public void initialize(int order){
        System.out.println("Order: "+order);
        try {
            System.out.println("Select the index of two leader card to discard   [e.g. setupdiscard 0 2]");
            Action action= new Action() {};
            while(!(action instanceof DiscardTwoLeaderCardsAction)){
                action= actionParser.parseInput(stdIn.readLine());
            }
            send(action);
            if(order>3){
                System.out.println("ORDER>3");
                do {
                    System.out.println("Select two resources to start with [e.g. headstart Coin Shield]");
                    action= actionParser.parseInput(stdIn.readLine());
                }while (!(action instanceof BonusResourcesAction));
                send(action);
            }
            else if(order>1){
                System.out.println("ORDER>1");
                do {
                    System.out.println("Select one resource to start with [e.g. headstart Stone]");
                    action= actionParser.parseInput(stdIn.readLine());
                }while (!(action instanceof BonusResourcesAction));
                send(action);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        firstTurnDone= true;
    }

    private void loopRequest() {
        System.out.println("SIAMO IN LOOP REQUEST");
        while (true){
            try {
                String keyboardInput = stdIn.readLine();
                Action actionToSend = this.actionParser.parseInput(keyboardInput);
                if (actionToSend instanceof DiscardTwoLeaderCardsAction) System.out.println("Discard message is in loop");
                if(!actionToSend.equals(null)&& !((actionToSend instanceof BonusResourcesAction) || actionToSend instanceof DiscardTwoLeaderCardsAction)) {
                    send(actionToSend);
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

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

    private void joinMatch() {
        try {
            System.out.println("Insert Nickname");
            String nickname = stdIn.readLine();
            outputStream.writeObject(new JoinMatchAction(nickname));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    private void createMatch(){
        int gameSize=0;
        String nickname="";
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

    public void sout(String line){
        System.out.println(line);
    }
}
