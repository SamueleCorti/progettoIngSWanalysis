package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import javafx.application.Platform;

public class YouDidntActivatePapalCard implements PrintableMessage {
    private String string = "Unfortunately you weren't far enough in the papal to activate it too";
    private int index;

    public String getString() {
        return string;
    }

    public int getIndex() {
        return index;
    }

    public YouDidntActivatePapalCard(int index) {
        this.index = index;
    }


    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(!isGui) System.out.println(string);
        else {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    socket.discardPapalCard(index);
                }
            });
        }
    }
}
