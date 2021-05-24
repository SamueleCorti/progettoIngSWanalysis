package it.polimi.ingsw.server.messages.printableMessages;

public class NotEnoughResourcesToProduce implements PrintableMessage {
    String string = "You don't have enough resources to activate this production";

    @Override
    public String getString() {
        return string;
    }
}
