package it.polimi.ingsw.server.messages.printableMessages;

public class YouWillMoveForward implements PrintableMessage {
    String string;

    public YouWillMoveForward(String nickname) {
        string = "As "+ nickname+ " discarded a resource, you'll now advance of one" +
                "tile in the papal path";
    }

    public String getString() {
        return string;
    }
}
