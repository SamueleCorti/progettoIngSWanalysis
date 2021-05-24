package it.polimi.ingsw.server.messages.gameplayMessages;

import it.polimi.ingsw.server.messages.Message;
import it.polimi.ingsw.model.Game;

/**
 * Sent when the player gets too many resources from the market.
 */

public class DiscardResourcesMessage implements Message {
    private final String message;

    public DiscardResourcesMessage(Game game) {
        message = "With the last action you exceeded your warehouse capacity; you now have to discard resources until the warehouse can contain what is left. \n" +
                "But be careful: for each discarded resource other players will advance of one square in the faith track!";
    }

    public String getMessage() {
        return message;
    }
}
