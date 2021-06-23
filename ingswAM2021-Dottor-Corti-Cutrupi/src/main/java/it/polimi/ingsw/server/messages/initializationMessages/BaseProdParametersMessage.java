package it.polimi.ingsw.server.messages.initializationMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import it.polimi.ingsw.server.messages.Message;

public class BaseProdParametersMessage implements Message {
    private int used;
    private int produced;

    public BaseProdParametersMessage(int used, int produced) {
        this.used = used;
        this.produced = produced;
    }

    public int getUsed() {
        return used;
    }

    public int getProduced() {
        return produced;
    }

    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(isGui){
            socket.setBaseProd(this);
        }
    }
}
