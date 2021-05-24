package it.polimi.ingsw.server.messages.printableMessages;

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
