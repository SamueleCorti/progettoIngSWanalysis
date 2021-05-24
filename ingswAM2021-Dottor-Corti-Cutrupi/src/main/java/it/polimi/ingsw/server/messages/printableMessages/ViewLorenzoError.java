package it.polimi.ingsw.server.messages.printableMessages;

public class ViewLorenzoError implements PrintableMessage {
    String string = "We cant show Lorenzo, because this is not a single player game!";

    public String getString() {
        return string;
    }
}
