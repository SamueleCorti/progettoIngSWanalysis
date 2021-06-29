package it.polimi.ingsw.server.messages.gameplayMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import it.polimi.ingsw.server.messages.Message;
import it.polimi.ingsw.server.messages.showingMessages.LeaderCardMessage;
import javafx.application.Platform;

import java.util.ArrayList;

/**
 * Self explanatory name
 */
public class WhiteToColorMessage implements Message {
    private final int numOfBlanks;
    private ArrayList<LeaderCardMessage> cards=new ArrayList<>();

    public WhiteToColorMessage(int numOfBlanks, ArrayList<LeaderCardMessage> messages) {
        this.numOfBlanks = numOfBlanks;
        cards=messages;
    }

    public int getNumOfBlnks() {
        return numOfBlanks;
    }

    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(isGui) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    socket.initializeWhiteToColor(numOfBlanks, cards);
                    socket.changeStage("whiteToColor.fxml");
                }
            });
        }
        else {
            socket.whiteToColorChoices(numOfBlanks);
        }
    }
}
