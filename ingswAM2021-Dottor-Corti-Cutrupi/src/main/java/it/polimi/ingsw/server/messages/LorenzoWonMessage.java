package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import javafx.application.Platform;

public class LorenzoWonMessage implements Message {

    public void execute(ClientSideSocket socket,Boolean isGui){
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