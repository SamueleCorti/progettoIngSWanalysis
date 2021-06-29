package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import javafx.application.Platform;

/**
 * Self explanatory name
 */
public class NotEnoughResourcesToProduce implements PrintableMessage {
    private String string = "You don't have enough resources to activate this production";

    @Override
    public String getString() {
        return string;
    }

    @Override
    public void execute(ClientSideSocket socket, boolean isGui){
        if(isGui)
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                socket.addErrorAlert(string,"Try later");
            }
        });
        else System.out.println(string);
    }
}
