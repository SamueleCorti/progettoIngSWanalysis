package it.polimi.ingsw.server.messages.printableMessages;

public class YouActivatedPapalCard implements PrintableMessage {
    String string;

    public YouActivatedPapalCard(int index) {
        string = "You just activated the papal favor card number: "+index;
    }

    @Override
    public String getString() {
        return string;
    }
}
