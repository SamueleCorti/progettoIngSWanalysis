package it.polimi.ingsw.server.messages.gameCreationPhaseMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import it.polimi.ingsw.server.messages.Message;
import javafx.application.Platform;

/**
 * Self explanatory name
 */
public class AddedToGameMessage implements Message {
    private String message=", you have been successfully added to the match";

    public AddedToGameMessage(String nickname, boolean isHost) {
        message= nickname+message;
        if(isHost) message+= " and set as host!";
    }

    public String getMessage() {
        return message;
    }

    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(isGui) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    socket.changeStage("lobby.fxml");
                    socket.addOkAlert(message,message);
                }
            });
        }
        else{
            System.out.println(message);
        }
    }
}
