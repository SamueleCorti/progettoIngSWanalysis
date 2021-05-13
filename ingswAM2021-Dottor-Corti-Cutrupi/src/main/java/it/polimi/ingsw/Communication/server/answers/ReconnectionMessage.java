package it.polimi.ingsw.Communication.server.answers;

import it.polimi.ingsw.Game;
import it.polimi.ingsw.Player;

/**
 * Used when a player reconnects to inform everyone, giving them the nickname of who reconnected.
 */

public class ReconnectionMessage implements Message{
    String message;

    public ReconnectionMessage(Player reconnectedPlayer) {
        message= reconnectedPlayer.getNickname()+ " has just reconnected! He'll now get to play from where he left; wish him good luck!";
    }
}
