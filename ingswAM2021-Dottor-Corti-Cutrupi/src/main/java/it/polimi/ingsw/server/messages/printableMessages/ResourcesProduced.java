package it.polimi.ingsw.server.messages.printableMessages;

public class ResourcesProduced implements PrintableMessage {
    String string;

    public ResourcesProduced(String s) {
        string = "Resources produced: "+s;
    }

    public String getString() {
        return string;
    }
}
