package it.polimi.ingsw.server.messages.connectionRelatedMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import it.polimi.ingsw.server.messages.Message;

public class PingMessage implements Message {
    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
    }
}
