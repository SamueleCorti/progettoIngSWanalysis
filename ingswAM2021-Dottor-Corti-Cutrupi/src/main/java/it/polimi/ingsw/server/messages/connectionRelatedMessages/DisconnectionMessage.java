package it.polimi.ingsw.server.messages.connectionRelatedMessages;

import it.polimi.ingsw.server.messages.Message;

/**
 * Used to inform the players that one of them has just lost connection with the server. Gives them the player's nickname and explains how things will proceed.
 */

public class DisconnectionMessage implements Message {
    private final String message;

    public DisconnectionMessage(String nickname) {
        message= nickname+ " has just disconnected. The game will proceed normally, and all "+nickname+"'s turns " +
                "will be skipped until he reconnects. ";
    }

    public String getMessage() {
        return message;
    }
}
