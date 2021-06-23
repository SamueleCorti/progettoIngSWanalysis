package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import javafx.application.Platform;

public class ActivatedLeaderCardAck implements PrintableMessage {
    private String string = "Leader card activated correctly!";
    private int index;

    public ActivatedLeaderCardAck(int index) {
        this.index = index;
    }

    public String getString() {
        return string;
    }

    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(!isGui){
            System.out.println(string);
        }
        else {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    socket.activateCardGivenItsIndex(index);
                    socket.activateIfDepot(new ActivatedLeaderCardAck(index));
                }
            });
        }
    }

    public int getIndex() {
        return index;
    }
}
