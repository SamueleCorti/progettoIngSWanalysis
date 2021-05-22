package it.polimi.ingsw.communication.server.messages.printableMessages;

import it.polimi.ingsw.communication.server.messages.Message;

public class ProductionAlreadyActivatedInThisTurn implements PrintableMessage {
    String string="You already activated the production in this turn, please try something else";

    public String getString() {
        return string;
    }
}
