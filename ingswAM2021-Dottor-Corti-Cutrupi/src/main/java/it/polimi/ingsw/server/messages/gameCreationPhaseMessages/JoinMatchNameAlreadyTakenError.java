package it.polimi.ingsw.server.messages.gameCreationPhaseMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import it.polimi.ingsw.server.messages.Message;
import javafx.application.Platform;

public class JoinMatchNameAlreadyTakenError implements Message {

    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(isGui){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    socket.addErrorAlert("Nickname already used","The nickname you selected is already used " +
                            "in the game we tried to connect you to. Please try with another nickname.");
                }
            });
        }
        else {
            System.out.println("The nickname you selected is already used in the game we tried to connect you to. Please" +
                    " try with another nickname");
            socket.createOrJoinMatchChoice();
        }
    }
}
