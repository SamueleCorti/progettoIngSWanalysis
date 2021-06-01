package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import javafx.application.Platform;

public class IncorrectAmountOfResources implements PrintableMessage {
    String string;

    public IncorrectAmountOfResources(int numOfStandardProdRequirements, int numOfStandardProdResults) {
        String string = "You insert an incorrect amount of resources, you must select "+
                numOfStandardProdRequirements+
                " resources to use and "+numOfStandardProdResults+" resources to produce!";
    }

    public String getString() {
        return string;
    }

    public void execute(ClientSideSocket socket){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                socket.addOkAlert(string,"");
                socket.changeStage("dashboard.fxml");
            }
        });
    }
}
