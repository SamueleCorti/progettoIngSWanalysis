package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.actions.secondaryActions.ViewDashboardAction;
import it.polimi.ingsw.client.shared.ClientSideSocket;
import javafx.application.Platform;

public class DiscardOKDepotOK implements PrintableMessage {
    String string = "You successfully deleted the resources you chose, and there are " +
            "no more problems with you depots, you can now go on";

    public String getString() {
        return string;
    }

    public void execute(ClientSideSocket socket){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                socket.changeStage("dashboard.fxml");
                socket.send(new ViewDashboardAction());
            }
        });
    }
}
