package it.polimi.ingsw.server.messages.initializationMessages;

import it.polimi.ingsw.server.messages.Message;
import it.polimi.ingsw.server.messages.jsonMessages.LeaderCardMessage;

import java.util.ArrayList;

public class CardsToDiscardMessage implements Message {
    ArrayList<LeaderCardMessage> messages;

    public CardsToDiscardMessage(ArrayList<LeaderCardMessage> messages) {
        this.messages = messages;
    }

    public ArrayList<LeaderCardMessage> getMessages() {
        return messages;
    }
}
