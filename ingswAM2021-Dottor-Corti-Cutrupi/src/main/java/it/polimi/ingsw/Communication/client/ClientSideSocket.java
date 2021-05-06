package it.polimi.ingsw.Communication.client;


import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * ConnectionSocket class handles the connection between the client and the server.
 *
 * @author Luca Pirovano
 */
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

    /** Constructor ConnectionSocket creates a new ConnectionSocket instance. */
    public ClientSideSocket(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
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
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

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
            return true;
        } catch (IOException e) {
            System.err.println("Error during socket configuration! Application will now close.");
            System.exit(0);
            return false;
        }
    }

    private void createOrJoinMatchChoice(PrintWriter out){
        try {
            String line;
            do {
                line = stdIn.readLine();
                out.println(line);
            }while (line!="Create" || line!="Join");
            if(line=="Create"){
                createMatch();
            }
            else if(line=="Join"){

            }

        } catch (IOException e) {
            System.err.println("Error during the choice between Create and Join! Application will now close. ");
            System.exit(0);
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
            }while (line!=null || line == "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Method send sends a new message to the server, encapsulating the object in a SerializedMessage
     * type unpacked and read later by the server.
     *
     */
    /*public void send(Message message) {
        SerializedMessage output = new SerializedMessage(message);
        try {
            outputStream.reset();
            outputStream.writeObject(output);
            outputStream.flush();
        } catch (IOException e) {
            System.err.println("Error during send process.");
            System.err.println(e.getMessage());
        }
    }*/


    public void sout(String line){
        System.out.println(line);
    }
}
