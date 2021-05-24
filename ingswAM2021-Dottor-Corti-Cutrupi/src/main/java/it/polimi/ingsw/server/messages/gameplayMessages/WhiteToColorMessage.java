package it.polimi.ingsw.server.messages.gameplayMessages;

import it.polimi.ingsw.server.messages.Message;

public class WhiteToColorMessage implements Message {
    private final int numOfBlnks;

    public WhiteToColorMessage(int numOfBlnks) {
        this.numOfBlnks = numOfBlnks;
    }

    public int getNumOfBlnks() {
        return numOfBlnks;
    }
}
