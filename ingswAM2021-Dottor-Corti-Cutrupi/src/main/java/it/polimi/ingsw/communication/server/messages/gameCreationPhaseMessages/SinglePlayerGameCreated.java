package it.polimi.ingsw.communication.server.messages.gameCreationPhaseMessages;

import it.polimi.ingsw.communication.server.messages.printableMessages.PrintableMessage;

public class SinglePlayerGameCreated implements PrintableMessage {
    String string = "Single-player game created";

    public String getString() {
        return string;
    }

}
