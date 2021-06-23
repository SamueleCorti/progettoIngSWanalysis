package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import javafx.application.Platform;

public class NotEnoughRequirementsToActivate implements PrintableMessage {
    String string = "You dont have the requirements to activate this leader card";

    public String getString() {
        return string;
    }


    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(!isGui) System.out.println(string);
        else{
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    socket.addErrorAlert("Error!","You dont have the requirements to activate this leader card");
                }
            });
        }
    }
}
