package it.polimi.ingsw.Communication.server.messages.GameplayMessages;

import it.polimi.ingsw.Communication.server.messages.Message;
import it.polimi.ingsw.Model.Game;

public class LastTurnMessage implements Message {
    private final String message;

    public LastTurnMessage(Game game) {
        int playerOrder= game.getActivePlayer().getOrder();
        int gameSize= game.getPlayers().size();
        String s2=" ";
        String s1= game.getActivePlayer().getNickname() + " has just completed one of the conditions to end the game!";
        if (gameSize==playerOrder)  message= s1+" As he was the last player to play, the game has officially concluded. The results will now be announced!";
        else {
            for (int i = playerOrder; i < gameSize; i++) {
                s2 += game.getPlayers().get(i).getNickname()+", " ;
            }
            if(gameSize==playerOrder-1)     message= s1+ "Now"+ s2 +" will do his last turn. After that, the results will be announced";
            else message= s1+ "Now"+ s2 +" will do their last turn. After that, the results will be announced";
        }

    }

    public String getMessage() {
        return message;
    }
}
