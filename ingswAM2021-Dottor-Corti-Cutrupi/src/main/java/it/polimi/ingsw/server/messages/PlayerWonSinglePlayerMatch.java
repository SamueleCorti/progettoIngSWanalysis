package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import javafx.application.Platform;

/**
 * Self explanatory name
 */
public class PlayerWonSinglePlayerMatch implements Message {
    int victoryPoints;

    public PlayerWonSinglePlayerMatch(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }


    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(isGui){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    socket.changeStage("youWonPage.fxml");
                }
            });
        }
        else {
            socket.playerWonSinglePlayerMatch(this);
        }
    }
}
