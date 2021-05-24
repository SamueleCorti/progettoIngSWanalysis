package it.polimi.ingsw.server.messages.printableMessages;

public class PlayerActivatePapalCard implements PrintableMessage {
    String string;

    public PlayerActivatePapalCard(String nickname, int index) {
        string= nickname + " has just activated the papal card number " + index;
    }

    public String getString() {
        return string;
    }
}
