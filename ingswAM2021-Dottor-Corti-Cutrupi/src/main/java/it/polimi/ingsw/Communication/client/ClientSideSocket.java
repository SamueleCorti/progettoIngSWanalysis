package it.polimi.ingsw.Communication.client;


import it.polimi.ingsw.Communication.client.actions.Action;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Locale;

public class ClientSideSocket {
    private final String serverAddress;
    private final int serverPort;
    SocketObjectListener objectListener;
    SocketStringListener stringListener;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private PrintWriter out;
    private BufferedReader in;
    private BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
    private ActionParser actionParser;


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
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            System.out.println(in.readLine());

            //creating listeners for string and object messages from server
            //object messages
            objectListener = new SocketObjectListener(socket, inputStream);
            Thread thread1 = new Thread(objectListener);
            thread1.start();

            // string messages
            stringListener = new SocketStringListener(socket, in, this);
            Thread thread2 = new Thread(stringListener);
            thread2.start();

            createOrJoinMatchChoice(out);
            loopRequest();
            return true;
        } catch (IOException e) {
            System.err.println("Error during socket configuration! Application will now close.");
            System.exit(0);
            return false;
        }
    }

    private void loopRequest() {
        while (true){
            try {
                //out.println(stdIn.readLine()); PREVIOUS COMMAND
                System.out.println("we are now reading from keyboard!");
                String keyboardInput = stdIn.readLine();
                System.out.println("you have typed " + keyboardInput);

                Action actionToSend = this.actionParser.parseInput(keyboardInput);
                if(!actionToSend.equals(null)) {
                    System.out.println("we are now trying to send the message");
                    send(actionToSend);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void createOrJoinMatchChoice(PrintWriter out){
        try {
            String line;
            do {
                line = stdIn.readLine().toLowerCase(Locale.ROOT);
                out.println(line);
            }while (!line.equals("create") && !line.equals("join") && !line.equals("rejoin"));

            //dividing the possible choices in their respective method
            switch (line){
                case "create":
                    createMatch();
                    break;
                case "join":

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

    private void rejoinMatch() {
        //client receives a message saying: "What's the ID of the game you want to rejoin?"
        //need to test when client inserts a string and not an int
        try {
            String id = stdIn.readLine();
            out.println(id);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void createMatch(){
        String line;
        try {
            do {
                line = stdIn.readLine();
                out.println(line);
            }while (Integer.parseInt(line)<1 || Integer.parseInt(line)>4);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            do {
                line = stdIn.readLine();
                out.println(line);
            }while (line==null || line.equals(""));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method send sends a new message to the server
     *
     */
    public void send(Action action) {
        try {
            System.out.println("before we reset");
            outputStream.reset();
            System.out.println("before we do the write obj");
            outputStream.writeObject(action);
            System.out.println("before we flush");
            outputStream.flush();
        } catch (IOException e) {
            System.err.println("Error during send process.");
            System.err.println(e.getMessage());
        }
    }

    public void sout(String line){
        System.out.println(line);
    }
}
