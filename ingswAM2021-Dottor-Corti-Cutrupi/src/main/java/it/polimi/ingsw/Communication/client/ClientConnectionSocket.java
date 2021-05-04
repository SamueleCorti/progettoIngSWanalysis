package it.polimi.ingsw.Communication.client;


import it.polimi.ingsw.Communication.client.SocketListener;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ConnectionSocket class handles the connection between the client and the server.
 *
 * @author Luca Pirovano
 */
public class ClientConnectionSocket {
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final String serverAddress;
    private final int serverPort;
    SocketListener listener;
    private ObjectOutputStream outputStream;

    /** Constructor ConnectionSocket creates a new ConnectionSocket instance. */
    public ClientConnectionSocket(String serverAddress, int serverPort) {
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
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            listener = new SocketListener(socket, input);
            Thread thread = new Thread(listener);
            thread.start();
            return true;
        } catch (IOException e) {
            System.err.println("Error during socket configuration! Application will now close.");
            logger.log(Level.SEVERE, e.getMessage(), e);
            System.exit(0);
            return false;
        }
    }


    /**
     * Method send sends a new message to the server, encapsulating the object in a SerializedMessage
     * type unpacked and read later by the server.
     *
     * @param message of type Message - the message to be sent to the server.
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


}
