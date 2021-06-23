package it.polimi.ingsw.server.messages.connectionRelatedMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import it.polimi.ingsw.server.messages.Message;
import it.polimi.ingsw.model.boardsAndPlayer.Player;
import javafx.application.Platform;

/**
 * Used when a player reconnects to inform everyone, giving them the nickname of who reconnected.
 */

public class ReconnectionMessage implements Message {
    private final String message;
    private final String nickname;

    public ReconnectionMessage(String nickname) {
        this.nickname = nickname;
        message= nickname+ " has just reconnected! He'll now get to play from where he left; wish him good luck!";
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
                    socket.addErrorAlert(nickname + " has just reconnected","He'll now get to play from where he left; wish him good luck!");
                }
            });
        }
        else{
            System.out.println(message);
        }
    }
}
