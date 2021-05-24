package it.polimi.ingsw.server.messages.printableMessages;

public class NotEnoughResourcesToProduce implements PrintableMessage {
    String string = "There's no leader card at the index you selected";

    @Override
    public String getString() {
        return string;
    }
}
