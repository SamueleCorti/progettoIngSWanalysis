package it.polimi.ingsw.server.messages.gameplayMessages;

import it.polimi.ingsw.server.messages.Message;
import it.polimi.ingsw.server.messages.jsonMessages.DevelopmentCardMessage;

import java.util.ArrayList;

public class ViewGameboardMessage implements Message {
    private ArrayList<DevelopmentCardMessage> messages;

    public ViewGameboardMessage(ArrayList<DevelopmentCardMessage> messages) {
        this.messages = messages;
    }

    public ArrayList<DevelopmentCardMessage> getMessages() {
        return messages;
    }
}
