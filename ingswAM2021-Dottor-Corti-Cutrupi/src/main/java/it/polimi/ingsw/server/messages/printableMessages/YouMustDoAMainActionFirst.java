package it.polimi.ingsw.server.messages.printableMessages;

public class YouMustDoAMainActionFirst implements PrintableMessage {
    String string = "You can't end your turn until you make a main action";

    public String getString() {
        return string;
    }
}
