package it.polimi.ingsw.communication.server.messages.printableMessages;

import it.polimi.ingsw.communication.server.messages.Message;

public class YouActivatedPapalCard implements PrintableMessage {
    String string;

    public YouActivatedPapalCard(int index) {
        string = "You just activated the papal favor card number: "+index;
    }
}
