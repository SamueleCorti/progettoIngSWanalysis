package it.polimi.ingsw.gui;

import it.polimi.ingsw.server.messages.Message;
import javafx.application.Platform;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;


public class SocketObjectListenerForGUI implements Runnable {

    private final GuiSideSocket socket;
    private final ObjectInputStream inputStream;
    //private MessageHandler messageHandler;

    /**
     * Constructor SocketListener creates a new SocketListener instance.
     *
     * @param socket      of type Socket - socket reference.
     * @param inputStream of type ObjectInputStream - the inputStream.
     */
    public SocketObjectListenerForGUI(GuiSideSocket socket, ObjectInputStream inputStream) {
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
                    MessageHandlerForGUI handler = new MessageHandlerForGUI(this.socket,receivedMessage);
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