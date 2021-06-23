package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import it.polimi.ingsw.server.messages.Message;
import javafx.application.Platform;

public class ShowingDashboardMessage implements Message {

    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(isGui){
            System.out.println("we set to true the showing other player dash");
            socket.setTrueShowingOtherPlayerDashboard();
            socket.resetAnotherPlayerDashboard();
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    socket.changeStage("anotherPlayerDashboard.fxml");
                }
            });
        }
        else System.out.println("The dashboard you requested is:");
    }
}
