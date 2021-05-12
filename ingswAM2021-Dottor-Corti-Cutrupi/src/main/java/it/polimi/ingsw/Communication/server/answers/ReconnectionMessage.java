package it.polimi.ingsw.Communication.server.answers;

import it.polimi.ingsw.Game;
import it.polimi.ingsw.Player;

public class ReconnectionMessage implements Message{
    Player reconnectedPlayer;
    Game game;
    String message;

    public ReconnectionMessage(Player reconnectedPlayer, Game game) {
        this.reconnectedPlayer = reconnectedPlayer;
        this.game = game;
        message= reconnectedPlayer.getNickname()+ " has just reconnected! He'll now get to play from where he left; wish him good luck!";
    }
}
