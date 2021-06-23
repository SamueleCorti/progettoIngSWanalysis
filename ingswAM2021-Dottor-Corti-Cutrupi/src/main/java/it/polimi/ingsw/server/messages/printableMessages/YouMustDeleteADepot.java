package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import javafx.application.Platform;

public class YouMustDeleteADepot implements PrintableMessage {
    String string = "There's a fourth depot in the warehouse, you must delete one"+"To do so, you have to perform a delete " +
            "depot action " +"[e.g. deletedepot 4]";

    public String getString() {
        return string;
    }

    public void execute(ClientSideSocket socket, boolean isGui){
        if(isGui)
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                socket.changeStage("exceedingdepot.fxml");
            }
        });
        else System.out.println(string);
    }
}
