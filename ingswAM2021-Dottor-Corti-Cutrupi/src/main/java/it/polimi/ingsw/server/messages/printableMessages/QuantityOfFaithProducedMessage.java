package it.polimi.ingsw.server.messages.printableMessages;

public class QuantityOfFaithProducedMessage implements PrintableMessage {
    private final int quantity;

    public QuantityOfFaithProducedMessage(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String getString() {
        if(quantity>0)  return "\nYou also produced "+quantity+" faith";
        else return null;
    }
}