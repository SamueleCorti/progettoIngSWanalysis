package it.polimi.ingsw.server.messages.printableMessages;

public class NotAProductionCard implements PrintableMessage {
    String string = "The card you selected is not a production card, please try again";

    public String getString() {
        return string;
    }
}
