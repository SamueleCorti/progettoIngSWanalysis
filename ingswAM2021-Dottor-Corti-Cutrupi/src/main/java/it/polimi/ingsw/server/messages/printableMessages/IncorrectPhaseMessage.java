package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import javafx.application.Platform;

/**
 * Self explanatory name
 */
public class IncorrectPhaseMessage implements PrintableMessage {
    private String string = "There is a time and a place for everything, but not now, Ash";


    public String getString() {
        return string;
    }

    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(isGui) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    socket.addErrorAlert("Incorrect phase!", "There is a time and a place for everything, " +
                            "but not now, Ash!");
                }
            });
        }
        else System.out.println(string);
    }
}
