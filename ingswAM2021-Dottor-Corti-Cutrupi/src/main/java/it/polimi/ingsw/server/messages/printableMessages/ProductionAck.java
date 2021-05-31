package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import javafx.application.Platform;

public class ProductionAck implements PrintableMessage {
    String string="Production activated successfully";

    public String getString() {
        return string;
    }

    public void execute(ClientSideSocket socket){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                socket.addOkAlert(string,"");
            }
        });
    }
}
