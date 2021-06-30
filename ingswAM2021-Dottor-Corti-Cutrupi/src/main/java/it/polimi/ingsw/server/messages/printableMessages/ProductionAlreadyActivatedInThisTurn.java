package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import javafx.application.Platform;

/**
 * Self explanatory name
 */
public class ProductionAlreadyActivatedInThisTurn implements PrintableMessage {
    private String string="You already activated the selected production in this turn, please try something else";

    public String getString() {
        return string;
    }

    public void execute(ClientSideSocket socket, boolean isGui){
        if(isGui)
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    socket.addOkAlert(string,"");
                    socket.changeStage("dashboard.fxml");
                }
            });
        else System.out.println(string);
    }
}
