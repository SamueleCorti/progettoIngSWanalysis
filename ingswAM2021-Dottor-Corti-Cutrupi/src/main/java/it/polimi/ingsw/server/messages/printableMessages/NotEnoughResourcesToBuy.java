package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import javafx.application.Platform;

public class NotEnoughResourcesToBuy implements PrintableMessage {
    private String string="You dont have enough resources to buy the card";

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
                    socket.addErrorAlert(string,"Try later when you will have the correct amount of resources");
                }
            });
        }
    }
}
