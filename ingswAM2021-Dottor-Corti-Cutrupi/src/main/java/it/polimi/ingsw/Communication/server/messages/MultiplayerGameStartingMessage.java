package it.polimi.ingsw.Communication.server.messages;

import it.polimi.ingsw.Game;
import it.polimi.ingsw.Player;

/**
 * Message sent when a game containing two or more players is starting
 */
public class MultiplayerGameStartingMessage implements Message {
    private String message;

    public MultiplayerGameStartingMessage(Game game) {
        String names="";
        for(Player player: game.getPlayers()){
            names+= player.getNickname()+", ";
        }
        message= "The game is now starting. " +names+ " get ready to play!";
    }

    public String getMessage() {
        return message;
    }
}
