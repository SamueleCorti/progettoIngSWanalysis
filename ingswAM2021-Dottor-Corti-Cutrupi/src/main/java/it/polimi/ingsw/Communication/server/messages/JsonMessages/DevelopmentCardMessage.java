package it.polimi.ingsw.Communication.server.messages.JsonMessages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.Communication.server.messages.Message;
import it.polimi.ingsw.Model.developmentcard.DevelopmentCard;

public class DevelopmentCardMessage implements Message {
    private String leaderCardJson;
    public DevelopmentCardMessage(DevelopmentCard developmentCard){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String leaderCardJson = gson.toJson(developmentCard);
        this.leaderCardJson = leaderCardJson;
    }

    public String getLeaderCardJson() {
        return leaderCardJson;
    }
}
