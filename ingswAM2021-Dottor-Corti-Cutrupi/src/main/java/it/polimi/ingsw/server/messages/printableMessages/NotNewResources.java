package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import javafx.application.Platform;

/**
 * Self explanatory name
 */
public class NotNewResources implements PrintableMessage{
    private String string = "There was a problem, you tried to eliminate resources not just taken from market";

    public String getString() {
        return string;
    }

    @Override
    public void execute(ClientSideSocket socket, boolean isGui){
        if(isGui)
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                socket.addErrorAlert("There was a problem", "You tried to eliminate resources not just taken from market");
            }
        });
        else System.out.println(string);
    }
}
