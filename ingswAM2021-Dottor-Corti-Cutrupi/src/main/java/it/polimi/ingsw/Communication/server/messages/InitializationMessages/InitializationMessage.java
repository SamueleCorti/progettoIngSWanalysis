package it.polimi.ingsw.Communication.server.messages.InitializationMessages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.Communication.server.messages.Message;
import it.polimi.ingsw.Model.leadercard.LeaderCard;
import it.polimi.ingsw.Model.requirements.Requirements;

import java.util.ArrayList;

/**
 * Message used if the number of leader card's parameter hasn't been modified. This message is used to properly set up the game.
 */
public class InitializationMessage implements Message {
    private final int order;
    private String leaderCardsPickedJson;
    private int leaderCardsKept;
    private int leaderCardsGiven;
    /**
     * Creates the standard message to send to notify the player on that to do to finish setting up the game.
     */
    public InitializationMessage(int order, ArrayList<LeaderCard> listOfCards,int leaderCardsKept,int leaderCardsGiven) {
        this.order=order;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String listOfCardsJson = gson.toJson(listOfCards);
        this.leaderCardsPickedJson = printLeaderCards(listOfCards);
        this.leaderCardsKept = leaderCardsKept;
        this.leaderCardsGiven = leaderCardsGiven;
    }

    public int getLeaderCardsKept() {
        return leaderCardsKept;
    }

    public int getLeaderCardsGiven() {
        return leaderCardsGiven;
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

    public String printLeaderCards(ArrayList<LeaderCard> cards){
        String string="\nHere are you development cards: \n";
        for(int i=0;i<cards.size();i++){
            string+="Leader card numer "+i+":\n";
            string+="Type of power : "+cards.get(i).getLeaderPower().returnPowerType()+"\n";
            string+="Activation requirements: ";
            for(Requirements requirements: cards.get(i).getCardRequirements()){
                string+=requirements;
            }
            string+="\nThis card is currently "+ cards.get(i).getCondition()+"\n\n";
        }
        return string;
    }
}
