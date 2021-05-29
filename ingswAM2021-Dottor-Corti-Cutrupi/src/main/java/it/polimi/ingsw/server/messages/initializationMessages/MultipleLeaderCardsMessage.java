package it.polimi.ingsw.server.messages.initializationMessages;

import it.polimi.ingsw.server.messages.Message;
import it.polimi.ingsw.server.messages.jsonMessages.LeaderCardMessage;

import java.util.ArrayList;

public class MultipleLeaderCardsMessage implements Message {
    ArrayList<LeaderCardMessage> messages;

    public MultipleLeaderCardsMessage(ArrayList<LeaderCardMessage> messages) {
        this.messages = messages;
    }

    public ArrayList<LeaderCardMessage> getMessages() {
        return messages;
    }
}
