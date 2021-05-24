package it.polimi.ingsw.server.messages.printableMessages;

public class LeaderCardIndexChanged implements PrintableMessage{
    String string = "Now card at index 0 is the card that previously was at index 1";

    public String getString() {
        return string;
    }


}
