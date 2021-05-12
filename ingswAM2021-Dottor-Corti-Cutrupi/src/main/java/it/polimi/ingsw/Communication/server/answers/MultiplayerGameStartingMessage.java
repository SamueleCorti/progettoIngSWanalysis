package it.polimi.ingsw.Communication.server.answers;

import it.polimi.ingsw.Communication.server.answers.Message;
import it.polimi.ingsw.Game;
import it.polimi.ingsw.Player;

public class MultiplayerGameStartingMessage implements Message {
    private Game game;
    private String message;

    public MultiplayerGameStartingMessage(Game game) {
        this.game = game;
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
