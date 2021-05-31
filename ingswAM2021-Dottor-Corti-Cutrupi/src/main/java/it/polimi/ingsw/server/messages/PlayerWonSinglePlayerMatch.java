package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import javafx.application.Platform;

public class PlayerWonSinglePlayerMatch implements Message {
    int victoryPoints;

    public PlayerWonSinglePlayerMatch(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public void execute(ClientSideSocket socket){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                socket.changeStage("winPage.fxml");
            }
        });
    }
}
