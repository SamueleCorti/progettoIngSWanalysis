package it.polimi.ingsw.client.shared;


import it.polimi.ingsw.server.messages.Message;

/**
 * the ActionHandler handles the messages coming from the Server
 */
public class MessageHandler implements Runnable {
    private final ClientSideSocket clientSideSocket;
    private final Message message;
    private final Boolean isGui;

    public MessageHandler(ClientSideSocket clientSideSocket, Message messageToHandle, Boolean isGui) {
        this.clientSideSocket = clientSideSocket;
        this.message = messageToHandle;
        this.isGui = isGui;
    }

    /**
     * Method used to handle the message based on the type of the message received
     */
    public void run() {
        message.execute(clientSideSocket, isGui);
    }
}