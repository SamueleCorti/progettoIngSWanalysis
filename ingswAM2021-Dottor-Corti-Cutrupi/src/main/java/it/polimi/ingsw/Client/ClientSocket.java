package it.polimi.ingsw.Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ClientSocket {
    private final String serverAddress;
    private final int serverPort;
    SocketListener listener;
    private ObjectOutputStream outputStream;

    /** Constructor ConnectionSocket creates a new ConnectionSocket instance. */
    public ClientSocket(String serverAddress, int serverPort) {
        this.serverAddress=serverAddress;
        this.serverPort=serverPort;
    }

    /**
     * Method setup initializes a new socket connection and handles the nickname-choice response. It
     * loops until the server confirms the successful connection (with no nickname duplication and
     * with a correctly configured match lobby).
     *
     * @param nickname of type String - the username chosen by the user.
     * @return boolean true if connection is successful, false otherwise.
     */
    public boolean setup(String nickname) {
        try {
            System.out.println(
                    "Configuring socket connection..." );
            System.out.println(
                     "Opening a socket server communication on port "
                            + serverPort
                            + "...");
            Socket socket;
            try {
                socket = new Socket(serverAddress, serverPort);
            } catch (SocketException | UnknownHostException e) {
                return false;
            }
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            while (true) {
                if (readInput(nickname, input)) {
                    break;
                }
            }
            listener = new SocketListener(socket, input);
            Thread thread = new Thread(listener);
            thread.start();
            return true;
        } catch (IOException e) {
            System.err.println("Error during socket configuration! Application will now close.");
            System.exit(0);
            return false;
        }
    }

    /**
     * Method readInput handles the input reading in order to reduce the setup complexity.
     *
     * @param nickname of type String - the chosen nickname.
     * @param input of type ObjectInputStream - the input socket stream.
     * @return boolean true if nickname is available and set, false otherwise.
     */
    private boolean readInput(String nickname, ObjectInputStream input) {
        try {
            send(nickname);
            if (nicknameChecker(input.readObject())) {
                return true;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Method nicknameChecker handles the nickname validation phase after the server answer about the
     * availability of the desired username.
     *
     * @param input of type Object - the server ObjectStream answer.
     * @return boolean true if the nickname is available and set, false otherwise.
     */
    public boolean nicknameChecker(Object input) {
        return true;
    }

    /**
     * Method send sends a new message to the server, encapsulating the object in a SerializedMessage
     * type unpacked and read later by the server.
     *
     * @param message of type Message - the message to be sent to the server.
     */
    public void send(String message) {
        try {
            outputStream.reset();
            outputStream.writeObject(message);
            outputStream.flush();
        } catch (IOException e) {
            System.err.println("Error during send process.");
            System.err.println(e.getMessage());
        }
    }
}
