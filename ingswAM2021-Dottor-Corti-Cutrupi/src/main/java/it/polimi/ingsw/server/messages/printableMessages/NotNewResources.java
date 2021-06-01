package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.actions.secondaryActions.ViewDashboardAction;
import it.polimi.ingsw.client.shared.ClientSideSocket;
import javafx.application.Platform;

public class NotNewResources implements PrintableMessage{
    String string = "There was a problem, you tried to eliminate a depot with resources not just taken from market";

    public String getString() {
        return string;
    }

    public void execute(ClientSideSocket socket){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                socket.addErrorAlert("There was a problem", "You tried to eliminate a depot with resources not just taken from market");
            }
        });
    }
}
