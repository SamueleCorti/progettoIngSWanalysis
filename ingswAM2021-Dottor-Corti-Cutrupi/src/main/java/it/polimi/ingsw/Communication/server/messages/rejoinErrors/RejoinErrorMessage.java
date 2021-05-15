package it.polimi.ingsw.Communication.server.messages.rejoinErrors;

import it.polimi.ingsw.Communication.server.messages.Message;

public class RejoinErrorMessage implements Message {
    String string;

    public String getString() {
        return string;
    }
}
