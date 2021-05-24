package it.polimi.ingsw.server.messages.printableMessages;

public class YouDidntActivatePapalCard implements PrintableMessage {
    String string = "Unfortunately you weren't far enough in the papal to activate it too";

    public String getString() {
        return string;
    }
}
