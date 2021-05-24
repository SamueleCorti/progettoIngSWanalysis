package it.polimi.ingsw.server.messages.printableMessages;

public class NotEnoughResourcesToBuy implements PrintableMessage {
    String string="You dont have enough resources to buy the card";

    public String getString() {
        return string;
    }
}
