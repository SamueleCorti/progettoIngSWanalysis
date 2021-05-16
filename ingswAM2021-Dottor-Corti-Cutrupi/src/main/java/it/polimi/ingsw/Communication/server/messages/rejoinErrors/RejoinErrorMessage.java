package it.polimi.ingsw.Communication.server.messages.rejoinErrors;

import it.polimi.ingsw.Communication.server.messages.Message;

public class RejoinErrorMessage implements Message {
    private final String string;

    public RejoinErrorMessage() {
        string= "Rejoin operation failed. Please try again after checking again your nickname and gameID";
    }

    public String getString() {
        return string;
    }
}
