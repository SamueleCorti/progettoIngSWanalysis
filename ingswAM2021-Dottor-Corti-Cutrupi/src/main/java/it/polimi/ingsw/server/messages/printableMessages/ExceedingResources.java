package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import javafx.application.Platform;

/**
 * Self explanatory name
 */
public class ExceedingResources implements PrintableMessage{
    private String string = "There's an exceeding amount of resources in one depot of the warehouse,\n" +"To do so, you have to perform " +
            "a discard resource action [e.g. discardresources coin stone]";

    public String getString() {
        return string;
    }

    @Override
    public void execute(ClientSideSocket socket, boolean isGui){
        if(isGui)
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    socket.changeStage("exceedingresources.fxml");
                }
            });
        else System.out.println(string);
    }
}
