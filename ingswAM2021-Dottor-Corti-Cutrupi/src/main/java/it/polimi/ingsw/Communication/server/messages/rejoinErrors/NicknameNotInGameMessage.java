package it.polimi.ingsw.Communication.server.messages.rejoinErrors;

import it.polimi.ingsw.Communication.server.messages.Message;

public class NicknameNotInGameMessage extends RejoinErrorMessage {
    private final String string = "The nickname you insert is not one of those in game or the player using that nickname is still playing."+
            "Please try later or join another game";

    public String getString() {
        return string;
    }
}
