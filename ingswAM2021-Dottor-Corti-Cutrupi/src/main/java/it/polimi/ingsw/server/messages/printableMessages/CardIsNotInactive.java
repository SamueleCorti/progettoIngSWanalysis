package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import javafx.application.Platform;

public class CardIsNotInactive implements PrintableMessage{
    String string = "The selected card is not in your hand anymore (you activated it): you can't discard it!";

    public String getString() {
        return string;
    }

    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(!isGui) System.out.println(string);
        else
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    socket.addErrorAlert("You can't discard the selected card!","You already activated this card " +
                            "so you can't discard it!");
                }
            });
    }
}