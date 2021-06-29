package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import javafx.application.Platform;

/**
 * Self explanatory name
 */
public class YouActivatedPapalCard implements PrintableMessage {
    private String string;
    private int index;

    public YouActivatedPapalCard(int index) {
        string = "You just activated the papal favor card number: "+index;
        this.index=index;
    }

    @Override
    public String getString() {
        return string;
    }

    public int getIndex() {
        return index;
    }


    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(!isGui) System.out.println(string);
        else{
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    socket.activatePapalCard(index);
                }
            });
        }
    }
}
