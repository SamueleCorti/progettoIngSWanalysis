package it.polimi.ingsw.server.messages.printableMessages;

public class YouDidntActivatePapalCard implements PrintableMessage {
    private String string = "Unfortunately you weren't far enough in the papal to activate it too";
    private int index;

    public YouDidntActivatePapalCard(int index) {
        this.index = index;
    }

    public String getString() {
        return string;
    }

    public int getIndex() {
        return index;
    }
}
