package it.polimi.ingsw.Communication.client;


import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

public class SocketStringListener implements Runnable {

    private final Socket socket;
    private final BufferedReader inputStream;
    private final ClientSideSocket clientSideSocket;

    /**
     * Constructor SocketListener creates a new SocketListener instance.
     *
     * @param socket        of type Socket - socket reference.
     * @param inputStream   of type ObjectInputStream - the inputStream.
     */
    public SocketStringListener(Socket socket, BufferedReader inputStream, ClientSideSocket clientSideSocket) {
        this.socket = socket;
        this.inputStream = inputStream;
        this.clientSideSocket = clientSideSocket;
    }


    /**
     * Method run loops and sends messages.
     */
    @Override
    public void run() {
            try {
                while (true) {
                    String line = inputStream.readLine();
                    if(line instanceof String)clientSideSocket.sout(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

    }
}