package it.polimi.ingsw.Communication.server.messages.JsonMessages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.Communication.server.messages.GenericMessage;
import it.polimi.ingsw.Communication.server.messages.Message;
import it.polimi.ingsw.Model.boardsAndPlayer.GameBoard;
import it.polimi.ingsw.Model.boardsAndPlayer.Player;
import it.polimi.ingsw.Model.developmentcard.DevelopmentCard;
import it.polimi.ingsw.Model.developmentcard.DevelopmentCardDeck;
import it.polimi.ingsw.Model.requirements.ResourcesRequirements;
import it.polimi.ingsw.Model.resource.Resource;

import java.util.ArrayList;

public class GameBoardMessage implements Message {
    private String message="Here's the gameboard:\n\n";

    public String getJsonGameboard() {
        return message;
    }

    public GameBoardMessage(GameBoard gameboard){
        for(int row=0; row<3;row++){
            for(int column=0; column<4;column++){
                //message+=gameboard.getDevelopmentCardDeck(row,column).getLast
            }
        }
    }

    private String printDevCards(DevelopmentCard card) {
        String string = new String();
        string+="Color: "+ card.getCardStats().getValue1()+"\tlevel: "+card.getCardStats().getValue0()+" \tvictory points: "+card.getVictoryPoints();
        string+="Production cost: \n";
        for(ResourcesRequirements resourcesRequirements: card.getProdRequirements()){
            string+= resourcesRequirements.getResourcesRequired().getValue0()+" "+ resourcesRequirements.getResourcesRequired().getValue1()+"s\t";
        }
        string+="\n";
        string+="Resources produced: \n";
        for(Resource resource: card.getProdResults())
            string+= resource;
        return string;
    }
}
