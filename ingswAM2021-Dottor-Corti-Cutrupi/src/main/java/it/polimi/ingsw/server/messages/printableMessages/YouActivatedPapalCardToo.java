package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import it.polimi.ingsw.server.messages.Message;
import javafx.application.Platform;

public class YouActivatedPapalCardToo implements Message {
    private String string;
    private int index;

    public YouActivatedPapalCardToo(int index) {
        string="You have activated papal favor card number "+index+" as well!";
        this.index=index;
    }

    public String getString() {
        return string;
    }

    public int getIndex() {
        return index;
    }


    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(!isGui) System.out.println(string);
        else {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    socket.activatePapalCard(index);
                }
            });
        }
    }
}
