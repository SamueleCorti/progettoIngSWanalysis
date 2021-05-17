package it.polimi.ingsw.Communication.server.messages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.Model.boardsAndPlayer.GameBoard;

public class GameBoardMessage implements Message{
    private String jsonGameboard;

    public String getJsonGameboard() {
        return jsonGameboard;
    }

    public GameBoardMessage(GameBoard gameboard){
        System.out.println("we're trying to serialize the gameboard");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println("we've created the gson");
        String gameboardJson = gson.toJson(gameboard);
        System.out.println("we've created the json, it is"+gameboardJson);
        this.jsonGameboard = gameboardJson;
    }
}
