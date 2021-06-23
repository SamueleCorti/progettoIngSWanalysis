package it.polimi.ingsw.server.messages.connectionRelatedMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import it.polimi.ingsw.server.messages.Message;
import javafx.application.Platform;

/**
 * Used to inform the players that one of them has just lost connection with the server. Gives them the player's nickname and explains how things will proceed.
 */

public class DisconnectionMessage implements Message {
    private final String message;
    private final String nickname;

    public DisconnectionMessage(String nickname) {
        this.nickname = nickname;
        message= nickname+ " has just disconnected. The game will proceed normally, and all "+nickname+"'s turns " +
                "will be skipped until he reconnects. ";
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
                    socket.addErrorAlert(nickname + " has just disconnected","The game will proceed normally, and all "+nickname+"'s turns " +
                            "will be skipped until he reconnects. ");
                }
            });
        }
        else{
            System.out.println(message);
        }
    }
}
