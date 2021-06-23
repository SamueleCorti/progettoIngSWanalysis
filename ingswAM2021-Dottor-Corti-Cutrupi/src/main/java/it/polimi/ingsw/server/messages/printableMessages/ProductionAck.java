package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.actions.secondaryActions.ViewDashboardAction;
import it.polimi.ingsw.client.shared.ClientSideSocket;
import javafx.application.Platform;

public class ProductionAck implements PrintableMessage {
    private String string="Production activated successfully";

    public String getString() {
        return string;
    }

    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(!isGui) System.out.println(string);
        else {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    socket.addOkAlert(string, "");
                    socket.changeStage("dashboard.fxml");
                    //socket.send(new ViewDashboardAction());
                }
            });
        }
    }
}
