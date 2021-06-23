package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import javafx.application.Platform;

public class LastRoundOfMatch implements PrintableMessage {
    private String string="Someone has fulfilled the conditions to end the game; when the last rotation of turns " +
            "will finish the winner will be declared!";

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
                    socket.addErrorAlert("Someone has fulfilled the conditions to end the game",
                            "The last round of turns will finish then we'll see who is the winner!");
                }
            });
        }
    }
}
