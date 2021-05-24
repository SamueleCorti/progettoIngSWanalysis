package it.polimi.ingsw.server.messages.printableMessages;

public class BlackCrossTokenMessage implements PrintableMessage {
    String string;

    public BlackCrossTokenMessage(int faithPosition) {
        string = "Lorenzo drew a BlackCrossToken: now he is at position "+faithPosition+" and his token deck has been shuffled";
    }

    @Override
    public String getString() {
        return string;
    }
}