package it.polimi.ingsw.server.messages.printableMessages;

public class ResourcesUsableForProd implements PrintableMessage {
    String string;

    public ResourcesUsableForProd(String s) {
        string = "Resources available for more productions: "+s;
    }

    public String getString() {
        return string;
    }
}
