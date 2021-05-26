package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.server.messages.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;


public class SocketObjectListenerForCLI implements Runnable {

    private final ClientSideSocket socket;
    private final ObjectInputStream inputStream;
    //private MessageHandler messageHandler;

    /**
     * Constructor SocketListener creates a new SocketListener instance.
     *
     * @param socket      of type Socket - socket reference.
     * @param inputStream of type ObjectInputStream - the inputStream.
     */
    public SocketObjectListenerForCLI(ClientSideSocket socket, ObjectInputStream inputStream) {
        this.socket = socket;
        this.inputStream = inputStream;
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
                    MessageHandlerForCLI handler = new MessageHandlerForCLI(this.socket,receivedMessage);
                    Thread thread1 = new Thread(handler);
                    thread1.start();
                }catch (StreamCorruptedException e){

                }
            }
        } catch (IOException e) {
            System.out.println("Ended connection with server");
            socket.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}