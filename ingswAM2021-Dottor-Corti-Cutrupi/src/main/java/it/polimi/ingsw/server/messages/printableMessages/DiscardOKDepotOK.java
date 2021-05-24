package it.polimi.ingsw.server.messages.printableMessages;

public class DiscardOKDepotOK implements PrintableMessage {
    String string = "You successfully deleted the resources you chose, and there are " +
            "no more problems with you depots, you can now go on";

    public String getString() {
        return string;
    }
}
