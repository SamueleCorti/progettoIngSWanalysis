package it.polimi.ingsw.Communication.server.messages.JsonMessages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.Communication.server.messages.Message;
import it.polimi.ingsw.Model.boardsAndPlayer.GameBoard;
import it.polimi.ingsw.Model.developmentcard.DevelopmentCard;
import it.polimi.ingsw.Model.developmentcard.DevelopmentCardDeck;

import java.util.ArrayList;

public class GameBoardMessage implements Message {
    private String jsonGameboard;

    public String getJsonGameboard() {
        return jsonGameboard;
    }

    public GameBoardMessage(GameBoard gameboard){
        System.out.println("we're trying to serialize the gameboard");
        ArrayList<DevelopmentCard> cardsOnTopOfDecks= new ArrayList<DevelopmentCard>();
        for(int i=0;i<3;i++) {
            for (DevelopmentCardDeck deck : gameboard.getDevelopmentCardDecks()[i]) {
                cardsOnTopOfDecks.add(deck.getFirstCard());
            }
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String gameboardJson = gson.toJson(cardsOnTopOfDecks);
        this.jsonGameboard = "Those are the cards on top of each deck in the GameBoard: \n" + gameboardJson;
    }
}
