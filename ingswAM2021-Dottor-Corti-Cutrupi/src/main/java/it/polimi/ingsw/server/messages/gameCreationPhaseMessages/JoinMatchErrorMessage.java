package it.polimi.ingsw.server.messages.gameCreationPhaseMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import it.polimi.ingsw.server.messages.Message;
import javafx.application.Platform;

public class JoinMatchErrorMessage implements Message {
    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(isGui){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    socket.addErrorAlert("No game found","0 games found, please try later.");
                }
            });
        }
        else {
            System.out.println("No game found, please try later");
            socket.createOrJoinMatchChoice();
        }
    }
}
