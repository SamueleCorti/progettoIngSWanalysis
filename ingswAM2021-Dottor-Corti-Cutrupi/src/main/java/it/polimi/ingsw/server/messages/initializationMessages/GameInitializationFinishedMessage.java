package it.polimi.ingsw.server.messages.initializationMessages;

import it.polimi.ingsw.client.actions.initializationActions.NotInInitializationAnymoreAction;
import it.polimi.ingsw.client.actions.secondaryActions.ViewDashboardAction;
import it.polimi.ingsw.client.actions.secondaryActions.ViewGameboardAction;
import it.polimi.ingsw.client.shared.ClientSideSocket;
import it.polimi.ingsw.server.messages.Message;
import javafx.application.Platform;

import java.util.concurrent.TimeUnit;

/**
 * Self explanatory name
 */
public class GameInitializationFinishedMessage implements Message {
    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(isGui){
            System.out.println("All the players have initialized their boards, game is now ready to effectively begin");
            socket.send(new NotInInitializationAnymoreAction());
            socket.setGameStarted();
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.MILLISECONDS.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    socket.changeStage("dashboard.fxml");
                }
            });
            socket.send(new ViewDashboardAction());
            socket.send(new ViewGameboardAction());
            socket.loopRequest();
        }
        else {
            System.out.println("All the players have initialized their boards, game is now ready to effectively begin");
            socket.send(new NotInInitializationAnymoreAction());
            socket.loopRequest();
        }
    }
}
