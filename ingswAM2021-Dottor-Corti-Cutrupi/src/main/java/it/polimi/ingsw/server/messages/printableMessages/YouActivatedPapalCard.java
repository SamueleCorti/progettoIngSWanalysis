package it.polimi.ingsw.server.messages.printableMessages;

public class YouActivatedPapalCard implements PrintableMessage {
    private String string;
    private int index;

    public YouActivatedPapalCard(int index) {
        string = "You just activated the papal favor card number: "+index;
        this.index=index;
    }

    @Override
    public String getString() {
        return string;
    }

    public int getIndex() {
        return index;
    }
}
