package it.polimi.ingsw.server.messages.printableMessages;

public class PrintAString implements PrintableMessage {
    String string;

    public PrintAString(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
