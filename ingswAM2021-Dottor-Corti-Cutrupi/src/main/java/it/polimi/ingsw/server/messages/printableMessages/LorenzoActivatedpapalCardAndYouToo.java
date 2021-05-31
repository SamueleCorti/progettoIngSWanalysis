package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import javafx.application.Platform;

public class LorenzoActivatedpapalCardAndYouToo implements PrintableMessage {
    private String string;
    private String string2;
    private int cardIndex;

    public LorenzoActivatedpapalCardAndYouToo(int cardIndex) {
        this.cardIndex=cardIndex;
        string="Lorenzo activated papal favor card number "+cardIndex;
        string2="And you were able to do it too";
    }

    public String getString() {
        return string+string2;
    }

    public void execute(ClientSideSocket socket){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                socket.addOkAlert(string,string2);
                socket.activatePapalCard(cardIndex);
            }
        });
    }
}
