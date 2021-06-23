package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import javafx.application.Platform;

public class SlotsLeft implements PrintableMessage {
    private String string;
    private int num;

    public SlotsLeft(int num) {
        this.num = num;
        this.string = num +" slots left";
    }

    public String getString() {
        return string;
    }

    public int getNum() {
        return num;
    }

    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(isGui) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    socket.addErrorAlert("A player connected to the game!",string);
                }
            });
        }
        else System.out.println("There are "+string);
    }
}
