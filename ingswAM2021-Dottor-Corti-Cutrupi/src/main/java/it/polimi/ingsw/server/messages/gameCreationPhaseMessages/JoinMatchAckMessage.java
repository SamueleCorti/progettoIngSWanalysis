package it.polimi.ingsw.server.messages.gameCreationPhaseMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import it.polimi.ingsw.server.messages.Message;
import javafx.application.Platform;

/**
 * Self explanatory name
 */
public class JoinMatchAckMessage implements Message {
    private final int gameID;
    private final int size;

    public JoinMatchAckMessage(int gameID, int sizeOfLobby) {
        this.gameID = gameID;
        this.size = sizeOfLobby;
    }

    public int getGameID() {
        return gameID;
    }

    public int getSize() {
        return size;
    }

    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        socket.setSizeOfLobby(size);
        if(isGui){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    socket.addOkAlert("You joined match n."+gameID,"Have a good game");
                }
            });
        }
        else {
            System.out.println("You joined match n."+gameID);
        }
    }
}
