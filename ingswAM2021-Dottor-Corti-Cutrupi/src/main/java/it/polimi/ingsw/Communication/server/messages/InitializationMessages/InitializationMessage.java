package it.polimi.ingsw.Communication.server.messages.InitializationMessages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.Communication.server.messages.Message;
import it.polimi.ingsw.Model.leadercard.LeaderCard;

import java.util.ArrayList;

/**
 * Message used if the number of leader card's parameter hasn't been modified. This message is used to properly set up the game.
 */
public class InitializationMessage implements Message {
    private final int order;
    private String leaderCardsPickedJson;

    /**
     * Creates the standard message to send to notify the player on that to do to finish setting up the game.
     */
    public InitializationMessage(int order, ArrayList<LeaderCard> listOfCards) {
        this.order=order;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String listOfCardsJson = gson.toJson(listOfCards);
        this.leaderCardsPickedJson = listOfCardsJson;
    }

    public String getLeaderCardsPickedJson() {
        return leaderCardsPickedJson;
    }

    /**
     * Standard getter for {@link InitializationMessage}
     * @return A string containing the message
     */

    public int getOrder() {
        return order;
    }
}
