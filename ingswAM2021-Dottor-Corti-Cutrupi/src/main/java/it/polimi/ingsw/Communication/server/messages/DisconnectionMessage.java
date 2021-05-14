package it.polimi.ingsw.Communication.server.messages;

import it.polimi.ingsw.Player;

/**
 * Used to inform the players that one of them has just lost connection with the server. Gives them the player's nickname and explains how things will proceed.
 */

public class DisconnectionMessage implements Message {
    String message;

    public DisconnectionMessage(Player disconnectedPlayer) {
        message= disconnectedPlayer.getNickname()+ " has just disconnected. The game will proceed normally, and all "+disconnectedPlayer.getNickname()+"'s turns" +
                "will be skipped until he reconnects. ";
    }

    public String getMessage() {
        return message;
    }
}
