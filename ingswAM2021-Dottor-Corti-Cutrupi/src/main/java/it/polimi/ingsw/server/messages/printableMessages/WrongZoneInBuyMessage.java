package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import javafx.application.Platform;

public class WrongZoneInBuyMessage implements PrintableMessage {
    String string = "You cant put a card of that level in that developmentCardZone";

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
                    socket.addErrorAlert("You can't do that!",
                            "You cant put a card of that level in that developmentCardZone");
                }
            });
        }
    }

}
