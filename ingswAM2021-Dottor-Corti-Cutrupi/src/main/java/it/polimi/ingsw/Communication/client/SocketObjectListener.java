package it.polimi.ingsw.Communication.client;

import java.io.ObjectInputStream;
import java.net.Socket;


public class SocketObjectListener implements Runnable {

    private final Socket socket;
    private final ObjectInputStream inputStream;

    /**
     * Constructor SocketListener creates a new SocketListener instance.
     *
     * @param socket        of type Socket - socket reference.
     * @param inputStream   of type ObjectInputStream - the inputStream.
     */
    public SocketObjectListener(Socket socket, ObjectInputStream inputStream) {
        this.socket = socket;
        this.inputStream = inputStream;
    }

    /**
     * Method process processes the serialized answer received from the server, passing it to the
     * answer handler.
     *
     * @param serverMessage of type SerializedAnswer - the serialized answer.
     */
    /*public void process(SerializedAnswer serverMessage) {
        modelView.setServerAnswer(serverMessage.getServerAnswer());
        actionHandler.answerHandler();
    }*/

    /**
     * Method run loops and sends messages.
     */
    @Override
    public void run() {
    }
}