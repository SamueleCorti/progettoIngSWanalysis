package it.polimi.ingsw.communication.server.messages.printableMessages;

import it.polimi.ingsw.communication.server.messages.Message;

public class ViewLorenzoError implements PrintableMessage {
    String string = "We cant show Lorenzo, because this is not a single player game!";

    public String getString() {
        return string;
    }
}
