package it.polimi.ingsw.Communication.client;

import it.polimi.ingsw.Communication.server.messages.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;


public class SocketObjectListener implements Runnable {

    private final ClientSideSocket socket;
    private final ObjectInputStream inputStream;
    private final MessageHandler messageHandler;

    /**
     * Constructor SocketListener creates a new SocketListener instance.
     *
     * @param socket      of type Socket - socket reference.
     * @param inputStream of type ObjectInputStream - the inputStream.
     */
    public SocketObjectListener(ClientSideSocket socket, ObjectInputStream inputStream) {
        this.socket = socket;
        this.inputStream = inputStream;
        messageHandler = new MessageHandler(socket);
    }


    /**
     * Method run loops and sends messages.
     */
    @Override
    public void run() {


        try {
        while (true) {
            try {
                Message receivedMessage =(Message) inputStream.readObject();
                this.messageHandler.handle(receivedMessage);
            }catch (StreamCorruptedException e){

            }
        }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}