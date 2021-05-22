package it.polimi.ingsw.communication.server.messages.printableMessages;

import it.polimi.ingsw.communication.server.messages.Message;

public class YouDidntActivatePapalCard implements PrintableMessage {
    String string = "Unfortunately you weren't far enough in the papal to activate it too";

    public String getString() {
        return string;
    }
}
