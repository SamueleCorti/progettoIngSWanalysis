package it.polimi.ingsw.communication.server.messages.printableMessages;

public class LeaderCardDiscardedAck implements PrintableMessage{
    String string;

    public String getString() {
        return string;
    }

    public LeaderCardDiscardedAck(int index) {
        this.string = "You have successfully removed the card at index "+index;
    }
}
