package it.polimi.ingsw.server.messages.printableMessages;

public class DiscardTokenMessage implements PrintableMessage {
    String string;

    public DiscardTokenMessage(String tokenUsed) {
        string = "Lorenzo drew a discard token: " + tokenUsed + "\n";
    }

    @Override
    public String getString() {
        return string;
    }
}
