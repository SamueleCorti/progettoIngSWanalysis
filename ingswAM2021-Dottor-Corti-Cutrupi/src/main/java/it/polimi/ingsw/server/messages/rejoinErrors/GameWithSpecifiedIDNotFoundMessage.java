package it.polimi.ingsw.server.messages.rejoinErrors;

import it.polimi.ingsw.client.shared.ClientSideSocket;

public class GameWithSpecifiedIDNotFoundMessage implements RejoinErrorMessage {
    private final String string;
    private final int id;

    public GameWithSpecifiedIDNotFoundMessage(int id) {
        this.id = id;
        this.string = "Game with ID="+id+" not found; try another id";
    }

    public String getString() {
        return string;
    }

    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(isGui){
            socket.addErrorAlert("Game with ID="+id+" not found","Please try another id");
        }
        else {
            System.out.println(string);
        }
    }
}
