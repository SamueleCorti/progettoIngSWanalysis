package it.polimi.ingsw.server.messages.gameplayMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import it.polimi.ingsw.server.messages.Message;
import javafx.application.Platform;

public class WhiteToColorMessage implements Message {
    private final int numOfBlnks;

    public WhiteToColorMessage(int numOfBlnks) {
        this.numOfBlnks = numOfBlnks;
    }

    public int getNumOfBlnks() {
        return numOfBlnks;
    }

    public void execute(ClientSideSocket socket){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                socket.changeStage("whiteToColor.fxml");
            }
        });
    }
}
