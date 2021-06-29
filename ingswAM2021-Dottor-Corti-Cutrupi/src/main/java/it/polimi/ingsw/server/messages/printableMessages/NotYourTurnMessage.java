package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.actions.secondaryActions.ViewDashboardAction;
import it.polimi.ingsw.client.shared.ClientSideSocket;
import javafx.application.Platform;

/**
 * Self explanatory name
 */
public class NotYourTurnMessage implements PrintableMessage{
    private String string = "It's not your turn, you must wait until it is before asking" +
            " for an action";

    public String getString() {
        return string;
    }

    @Override
    public void execute(ClientSideSocket socket, boolean isGui){
        if(isGui)
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                socket.addErrorAlert("It's not your turn","You must wait until is it before asking for an action");
                socket.send(new ViewDashboardAction());
            }
        });
        else System.out.println(string);
    }
}
