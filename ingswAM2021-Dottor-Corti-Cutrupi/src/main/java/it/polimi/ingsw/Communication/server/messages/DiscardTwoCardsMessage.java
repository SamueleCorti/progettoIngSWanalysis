package it.polimi.ingsw.Communication.server.messages;

/**
 * Message used if the number of leader card's parameter hasn't been modified. This message is used to properly set up the game.
 */
public class DiscardTwoCardsMessage implements Message{
    private String string;

    /**
     * Creates the standard message to send to notify the player on that to do to finish setting up the game.
     */
    public DiscardTwoCardsMessage() {
        string= "You've been handed 4 leader cards, but you can only keep 2. Please select the leader cards you wish to discard";
    }

    /**
     * Standard getter for {@link DiscardTwoCardsMessage}
     * @return A string containing the message
     */
    public String getString() {
        return string;
    }
}
