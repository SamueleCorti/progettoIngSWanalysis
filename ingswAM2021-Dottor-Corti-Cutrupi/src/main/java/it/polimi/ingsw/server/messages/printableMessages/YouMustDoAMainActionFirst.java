package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import javafx.application.Platform;

public class YouMustDoAMainActionFirst implements PrintableMessage {
    String string = "You can't end your turn until you make a main action";

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
                    socket.addErrorAlert("Unable to end turn", (string));
                }
            });
        }
    }
}
