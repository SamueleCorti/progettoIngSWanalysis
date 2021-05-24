package it.polimi.ingsw.server.messages.printableMessages;

public class ProductionAlreadyActivatedInThisTurn implements PrintableMessage {
    String string="You already activated the production in this turn, please try something else";

    public String getString() {
        return string;
    }
}
