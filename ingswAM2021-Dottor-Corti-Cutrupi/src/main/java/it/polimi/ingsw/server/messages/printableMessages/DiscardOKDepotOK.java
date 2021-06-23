package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.actions.secondaryActions.ViewDashboardAction;
import it.polimi.ingsw.client.shared.ClientSideSocket;
import javafx.application.Platform;

public class DiscardOKDepotOK implements PrintableMessage {
    private String string = "You successfully deleted the resources you chose, and there are " +
            "no more problems with you depots, you can now go on";

    public String getString() {
        return string;
    }

    @Override
    public void execute(ClientSideSocket socket, boolean isGui){
        if(isGui)
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    socket.changeStage("dashboard.fxml");
                    socket.send(new ViewDashboardAction());
                }
            });
        else System.out.println(string);
    }
}
