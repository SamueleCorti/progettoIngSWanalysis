package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import javafx.application.Platform;

public class WrongAmountOfResources implements PrintableMessage {
    private String string;
    private String string2;

    public WrongAmountOfResources(int i) {
        this.string = "Wrong number of resources wanted inserted";
        this.string2 = "that leader card needs "+i+"resources wanted";
    }

    public String getString() {
        return string + string2;
    }

    public void execute(ClientSideSocket socket, boolean isGui){
        if(isGui)
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                socket.addErrorAlert(string,string2);
            }
        });
        else System.out.println(string);
    }
}
