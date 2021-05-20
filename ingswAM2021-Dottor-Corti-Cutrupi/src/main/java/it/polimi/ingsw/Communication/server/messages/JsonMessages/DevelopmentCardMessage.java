package it.polimi.ingsw.Communication.server.messages.JsonMessages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.Communication.server.messages.Message;
import it.polimi.ingsw.Model.developmentcard.DevelopmentCard;

public class DevelopmentCardMessage implements Message {
    private String leaderCardJson;
    public DevelopmentCardMessage(DevelopmentCard developmentCard){
        if(developmentCard != null) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String leaderCardJson = gson.toJson(developmentCard);
            this.leaderCardJson = leaderCardJson;
        }
        else leaderCardJson = "null (there are no more cards in the deck of the specified color and level)";
    }

    public String getLeaderCardJson() {
        return leaderCardJson;
    }
}
