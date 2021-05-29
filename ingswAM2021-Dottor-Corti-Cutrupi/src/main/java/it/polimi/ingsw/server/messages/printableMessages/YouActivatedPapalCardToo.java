package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.server.messages.Message;

public class YouActivatedPapalCardToo implements Message {
    private String string;
    private int index;

    public YouActivatedPapalCardToo(int index) {
        string="You have activated papal favor card number "+index+" as well!";
        this.index=index;
    }

    public String getString() {
        return string;
    }

    public int getIndex() {
        return index;
    }
}
