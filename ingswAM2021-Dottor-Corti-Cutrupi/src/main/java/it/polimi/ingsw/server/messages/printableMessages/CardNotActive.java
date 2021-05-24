package it.polimi.ingsw.server.messages.printableMessages;

public class CardNotActive implements PrintableMessage {
    String string = "The card you selected is not active";

    public String getString() {
        return string;
    }
}
