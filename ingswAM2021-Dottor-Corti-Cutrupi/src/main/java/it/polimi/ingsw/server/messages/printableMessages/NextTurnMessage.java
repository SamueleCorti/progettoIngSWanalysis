package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import javafx.application.Platform;

public class NextTurnMessage implements PrintableMessage {
    private String string;

    public NextTurnMessage(String nickname) {
        string = "It's "+nickname+"'s turn";
    }

    public String getString() {
        return string;
    }


    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(!isGui) System.out.println(string);
        else{
            socket.resetBaseProd();

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    socket.addOkAlert("Turn changed", (string));
                }
            });
        }
    }
}
