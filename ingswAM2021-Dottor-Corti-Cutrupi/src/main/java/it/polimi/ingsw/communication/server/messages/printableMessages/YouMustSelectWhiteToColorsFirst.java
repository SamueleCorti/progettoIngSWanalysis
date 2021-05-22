package it.polimi.ingsw.communication.server.messages.printableMessages;

import it.polimi.ingsw.communication.server.messages.Message;

public class YouMustSelectWhiteToColorsFirst implements PrintableMessage {
    String string = "You're not allowed to do that, as right now you have to " +
            "select the resources to get from blanks. You can see your leader cards active from above";

    public String getString() {
        return string;
    }
}
