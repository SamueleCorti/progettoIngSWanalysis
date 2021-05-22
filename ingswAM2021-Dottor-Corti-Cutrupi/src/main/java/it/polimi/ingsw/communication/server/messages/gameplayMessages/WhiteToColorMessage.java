package it.polimi.ingsw.communication.server.messages.gameplayMessages;

import it.polimi.ingsw.communication.server.messages.Message;
import it.polimi.ingsw.model.resource.ResourceType;

public class WhiteToColorMessage implements Message {
    private final int numOfBlnks;

    public WhiteToColorMessage(int numOfBlnks) {
        this.numOfBlnks = numOfBlnks;
    }

    public int getNumOfBlnks() {
        return numOfBlnks;
    }
}
