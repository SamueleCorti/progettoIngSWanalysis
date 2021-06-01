package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import javafx.application.Platform;

public class ProductionAlreadyActivatedInThisTurn implements PrintableMessage {
    String string="You already activated the selected production in this turn, please try something else";

    public String getString() {
        return string;
    }

    public void execute(ClientSideSocket socket){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                socket.addOkAlert(string,"");
                socket.changeStage("dashboard.fxml");
            }
        });
    }
}
