package it.polimi.ingsw.server.messages.printableMessages;

public class YouActivatedProductionsInThisTurn implements PrintableMessage {
    String string = "This turn you're activating your " +
            "productions. You can either pass your turn or keep on activating them";

    public String getString() {
        return string;
    }
}
