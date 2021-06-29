package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import javafx.application.Platform;

/**
 * Self explanatory name
 */
public class MainActionAlreadyDoneMessage implements PrintableMessage {
    private String string = "You already did one of the main actions.";
    private String string2 = "Try with something else or end your turn";

    public String getString() {
        return string + string2;
    }

    @Override
    public void execute(ClientSideSocket socket, boolean isGui){
        if(isGui)
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                socket.addErrorAlert(string,string2);
            }
        });
        else System.out.println(string);
    }
}
