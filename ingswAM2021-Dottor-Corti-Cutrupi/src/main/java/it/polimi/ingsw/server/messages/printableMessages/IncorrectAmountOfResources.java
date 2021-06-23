package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import javafx.application.Platform;

public class IncorrectAmountOfResources implements PrintableMessage {
    private String string;

    public IncorrectAmountOfResources(int numOfStandardProdRequirements, int numOfStandardProdResults) {
        string = "You insert an incorrect amount of resources, you must select "+
                numOfStandardProdRequirements+
                " resources to use and "+numOfStandardProdResults+" resources to produce!";
    }

    public String getString() {
        return string;
    }

    @Override
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
