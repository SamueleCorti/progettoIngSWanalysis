package it.polimi.ingsw.Client;

import java.io.ObjectInputStream;
import java.net.Socket;

public class SocketListener implements Runnable {

    private final Socket socket;
    private final ObjectInputStream inputStream;

    /**
     * Constructor SocketListener creates a new SocketListener instance.
     *
     * @param socket of type Socket - socket reference.
     * @param inputStream of type ObjectInputStream - the inputStream.
     */
    public SocketListener(
            Socket socket,
            ObjectInputStream inputStream) {
        this.socket = socket;
        this.inputStream = inputStream;
    }


    /** Method run loops and sends messages. */
    @Override
    public void run() {
    }
}
