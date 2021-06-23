package it.polimi.ingsw.server.messages.connectionRelatedMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import it.polimi.ingsw.server.messages.Message;
import javafx.application.Platform;

public class RejoinAckMessage implements Message {
    private final int gamePhase;
    private final int size;

    public RejoinAckMessage(int gamePhase, int size) {
        this.gamePhase = gamePhase;
        this.size = size;
    }

    public int getGamePhase() {
        return gamePhase;
    }

    public int getSize() {
        return size;
    }

    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(isGui) {
            switch (gamePhase){
                case 0:
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            socket.changeStage("lobby.fxml");
                            socket.addOkAlert("Rejoined successfully!","You are still in lobby" +
                                    " so you simply have to wait for the room to full");
                        }
                    });
                    break;
                case 1:
                    socket.addOkAlert("You were in initialization phase","You have to finish it");
            }
        }
        else{
            System.out.println("You have been correctly reconnected to the game");
            switch (gamePhase){
                case 0:
                    System.out.println("You are still in lobby so you simply have to wait for the room to full");
                    break;
                case 1:
                    System.out.println("You were in initialization phase: you have to finish it");
            }
        }
    }
}
