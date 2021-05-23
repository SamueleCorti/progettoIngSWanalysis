package it.polimi.ingsw.communication.server.messages.printableMessages;

import it.polimi.ingsw.communication.server.messages.Message;
import it.polimi.ingsw.model.lorenzoIlMagnifico.Token;

public class DiscardTokenMessage implements PrintableMessage {
    String string;

    public DiscardTokenMessage(String tokenUsed) {
        string = "Lorenzo drew a discard token: " + tokenUsed + ";\nThe new card on top of that deck is:";
    }

    @Override
    public String getString() {
        return string;
    }
}
