package it.polimi.ingsw.server.messages.gameplayMessages;

import it.polimi.ingsw.server.messages.Message;
import it.polimi.ingsw.server.messages.jsonMessages.DevelopmentCardMessage;

import java.util.ArrayList;

public class ViewGameboardMessage implements Message {
    private DevelopmentCardMessage[] messages= new DevelopmentCardMessage[12];

    public ViewGameboardMessage(DevelopmentCardMessage[] messages) {
        this.messages = messages;
    }

    public DevelopmentCardMessage[] getMessages() {
        return messages;
    }
}
