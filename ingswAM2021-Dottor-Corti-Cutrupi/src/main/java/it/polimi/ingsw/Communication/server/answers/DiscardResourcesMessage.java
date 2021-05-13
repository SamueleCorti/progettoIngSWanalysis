package it.polimi.ingsw.Communication.server.answers;

import it.polimi.ingsw.Game;

/**
 * Sent when the player gets too many resources from the market.
 */

public class DiscardResourcesMessage implements Message{
    private String message;

    public DiscardResourcesMessage(Game game) {
        message = "With the last action you exceeded your warehouse capacity; you now have to discard resources until the warehouse can contain what is left. \n" +
                "But be careful: for each discarded resource other players will advance of one square in the faith track!";
    }

    public String getMessage() {
        return message;
    }
}
