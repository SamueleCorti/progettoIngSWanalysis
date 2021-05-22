package it.polimi.ingsw.communication.server.messages.printableMessages;

import it.polimi.ingsw.communication.server.messages.Message;

public class LorenzoActivatedpapalCardAndYouToo implements PrintableMessage {
    String string;

    public LorenzoActivatedpapalCardAndYouToo(int cardIndex) {
        string="Lorenzo activated papal favor card number "+cardIndex+", and you were " +
                "able to do it too";
    }

    public String getString() {
        return string;
    }
}
