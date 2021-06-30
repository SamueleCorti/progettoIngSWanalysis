package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.shared.ClientSideSocket;

import java.io.Serializable;

/**
 * Interface used by all messages
 */
public interface Message extends Serializable {
    void execute(ClientSideSocket socket, boolean isGui);
}
