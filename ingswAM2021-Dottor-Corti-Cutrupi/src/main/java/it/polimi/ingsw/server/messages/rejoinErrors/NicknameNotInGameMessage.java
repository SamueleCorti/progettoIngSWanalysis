package it.polimi.ingsw.server.messages.rejoinErrors;

import it.polimi.ingsw.client.shared.ClientSideSocket;

/**
 * Self explanatory name
 */
public class NicknameNotInGameMessage implements RejoinErrorMessage {
    private final String string = "The nickname you insert is not one of those in game or the player using that nickname is still playing."+
            "Please try later or join another game";

    public String getString() {
        return string;
    }

    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(isGui){
            socket.addErrorAlert("Error",string);
        }
        else {
            System.out.println(string);
        }
    }
}
