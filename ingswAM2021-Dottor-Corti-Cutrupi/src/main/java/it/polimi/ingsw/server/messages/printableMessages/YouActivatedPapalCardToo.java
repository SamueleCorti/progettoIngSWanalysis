package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.server.messages.Message;

public class YouActivatedPapalCardToo implements Message {
    String string;

    public YouActivatedPapalCardToo(int index) {
        string="You have activated papal favor card number "+index+" as well!";
    }

    public String getString() {
        return string;
    }
}