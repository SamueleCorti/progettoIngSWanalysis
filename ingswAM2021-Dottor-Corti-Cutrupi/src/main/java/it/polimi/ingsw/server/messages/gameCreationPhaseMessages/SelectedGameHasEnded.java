package it.polimi.ingsw.server.messages.gameCreationPhaseMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import it.polimi.ingsw.server.messages.Message;
import javafx.application.Platform;

public class SelectedGameHasEnded implements Message {
    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(isGui){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    socket.addErrorAlert("Error: the selected game has ended","if all the players in a game disconnect, the game ends");
                }
            });
        }
        else {
            System.out.println("The selected game has ended (if all the players in a game disconnect, the game ends)");
        }
    }
}
