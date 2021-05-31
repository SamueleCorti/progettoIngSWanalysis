package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.actions.secondaryActions.ViewDashboardAction;
import it.polimi.ingsw.client.shared.ClientSideSocket;
import javafx.application.Platform;

public class BlackCrossTokenMessage implements PrintableMessage {
    private String string;
    private String string2;

    public BlackCrossTokenMessage(int faithPosition) {
        string = "Lorenzo drew a BlackCrossToken: now he is at position "+faithPosition+" and his token deck has been shuffled";
        string2 = "Now he is at position "+faithPosition+" and his token deck has been shuffled";
    }

    @Override
    public String getString() {
        return string;
    }

    public void execute(ClientSideSocket socket){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                socket.addLorenzoAlert("He drew a Black Cross Token",string2);
                socket.send(new ViewDashboardAction());
            }
        });
    }
}
