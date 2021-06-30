package it.polimi.ingsw.server.messages.gameCreationPhaseMessages;

import it.polimi.ingsw.client.actions.matchManagementActions.NotInLobbyAnymore;
import it.polimi.ingsw.client.shared.ClientSideSocket;
import it.polimi.ingsw.server.messages.Message;
import javafx.application.Platform;

/**
 * Self explanatory name
 */
public class GameStartingMessage implements Message {
    private final String message;

    public GameStartingMessage() {
        message = "The game has started\n";
    }

    public String getMessage() {
        return message;
    }

    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(isGui){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    socket.changeStage("discardleadercards.fxml");
                }
            });
            socket.send(new NotInLobbyAnymore());
        }
        else {
            System.out.println(message);
            socket.send(new NotInLobbyAnymore());
        }
    }
}
