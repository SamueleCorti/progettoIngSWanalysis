package it.polimi.ingsw.Communication.server.messages;

/**
 * Message used if the number of leader card's parameter hasn't been modified. This message is used to properly set up the game.
 */
public class InitializationMessage implements Message{
    private final int order;
    //private LeaderCard[] leaderCards= new LeaderCard[4];

    /**
     * Creates the standard message to send to notify the player on that to do to finish setting up the game.
     */
    public InitializationMessage(int order) {
        this.order=order;
        //this.leaderCards=leaderCards;
    }

    /**
     * Standard getter for {@link InitializationMessage}
     * @return A string containing the message
     */

    public int getOrder() {
        return order;
    }
}
