package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import javafx.application.Platform;

/**
 * Self explanatory name
 */
public class LorenzoActivatedPapalCardAndYouDidnt implements PrintableMessage {
    private final String string;
    private final String string2;
    private int cardIndex;

    public LorenzoActivatedPapalCardAndYouDidnt(int cardIndex) {
        this.cardIndex=cardIndex;
        string = "Lorenzo activated papal favor card number "+cardIndex;
        string2 = "Unfortunately you weren't far enough in the papal to activate it too";
    }

    public String getString() {
        return string+string2;
    }

    @Override
    public void execute(ClientSideSocket socket, boolean isGui){
        if(isGui)
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    socket.addErrorAlert(string,string2);
                    socket.discardPapalCard(cardIndex);
                }
            });
        else System.out.println(string+"\n"+string2);
    }
}
