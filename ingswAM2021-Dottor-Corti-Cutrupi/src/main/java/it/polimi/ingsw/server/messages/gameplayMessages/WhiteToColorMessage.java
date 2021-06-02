package it.polimi.ingsw.server.messages.gameplayMessages;

import it.polimi.ingsw.client.gui.utility.LeaderCardForGUI;
import it.polimi.ingsw.client.shared.ClientSideSocket;
import it.polimi.ingsw.server.messages.Message;
import it.polimi.ingsw.server.messages.jsonMessages.LeaderCardMessage;
import javafx.application.Platform;

import java.util.ArrayList;

public class WhiteToColorMessage implements Message {
    private final int numOfBlanks;
    private ArrayList<LeaderCardMessage> cards=new ArrayList<>();

    public WhiteToColorMessage(int numOfBlnks, ArrayList<LeaderCardMessage> messages) {
        this.numOfBlanks = numOfBlnks;
        cards=messages;
    }

    public int getNumOfBlnks() {
        return numOfBlanks;
    }

    public void execute(ClientSideSocket socket) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    socket.initializeWhiteToColor(numOfBlanks, cards);
                    socket.changeStage("whiteToColor.fxml");
                }
            });
    }
}
