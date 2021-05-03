package it.polimi.ingsw.Communication.client;

import java.io.BufferedReader;
import java.io.ObjectInputStream;
import java.net.Socket;

public class SocketListener implements Runnable {

    private final Socket socket;
    private final ObjectInputStream inputStream;
    private final BufferedReader in;

    /**
     * Constructor SocketListener creates a new SocketListener instance.
     *
     * @param socket of type Socket - socket reference.
     * @param inputStream of type ObjectInputStream - the inputStream.
     */
    public SocketListener(Socket socket,ObjectInputStream inputStream,BufferedReader in) {
        this.socket = socket;
        this.inputStream = inputStream;
        this.in = in;
    }


    /** Method run loops and sends messages. */
    @Override
    public void run() {
    }
}
