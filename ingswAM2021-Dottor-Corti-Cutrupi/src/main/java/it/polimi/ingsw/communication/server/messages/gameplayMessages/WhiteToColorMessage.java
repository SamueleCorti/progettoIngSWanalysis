package it.polimi.ingsw.communication.server.messages.gameplayMessages;

import it.polimi.ingsw.communication.server.messages.Message;
import it.polimi.ingsw.model.resource.ResourceType;

public class WhiteToColorMessage implements Message {
    private final int numOfBlnks;
    private final ResourceType type1;
    private final ResourceType type2;

    public WhiteToColorMessage(int numOfBlnks, ResourceType type1, ResourceType type2) {
        this.numOfBlnks = numOfBlnks;
        this.type1 = type1;
        this.type2 = type2;
    }

    public int getNumOfBlnks() {
        return numOfBlnks;
    }

    public ResourceType getType1() {
        return type1;
    }

    public ResourceType getType2() {
        return type2;
    }
}
