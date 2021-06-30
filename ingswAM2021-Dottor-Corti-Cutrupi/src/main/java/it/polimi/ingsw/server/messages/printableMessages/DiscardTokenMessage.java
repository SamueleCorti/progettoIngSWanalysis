package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.actions.secondaryActions.ViewGameboardAction;
import it.polimi.ingsw.client.shared.ClientSideSocket;
import javafx.application.Platform;

/**
 * Self explanatory name
 */
public class DiscardTokenMessage implements PrintableMessage {
    private String string;
    private String string2;

    public DiscardTokenMessage(String tokenUsed) {
        string = "Lorenzo drew a discard token: " + tokenUsed + "\n";
        string2 = tokenUsed;
    }

    @Override
    public String getString() {
        return string;
    }

    @Override
    public void execute(ClientSideSocket socket,boolean isGui){
        if (isGui)
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    socket.addLorenzoAlert("He drew a Discard Token",string2);
                    socket.send(new ViewGameboardAction());
                }
            });
        else System.out.println(string);
    }
}
