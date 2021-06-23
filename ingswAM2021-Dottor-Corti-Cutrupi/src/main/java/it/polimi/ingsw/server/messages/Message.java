package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.shared.ClientSideSocket;

import java.io.Serializable;

public interface Message extends Serializable {
    public void execute(ClientSideSocket socket, boolean isGui);
}
