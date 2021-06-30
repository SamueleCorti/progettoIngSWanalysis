package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import javafx.application.Platform;

/**
 * Self explanatory name
 */
public class LorenzoWonMessage implements Message {

    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(isGui) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    socket.changeStage("lostPage.fxml");
                    socket.addLorenzoAlert("Lorenzo won the game", "");
                }
            });
        }
        else{
            socket.LorenzoWon();
        }
    }
}