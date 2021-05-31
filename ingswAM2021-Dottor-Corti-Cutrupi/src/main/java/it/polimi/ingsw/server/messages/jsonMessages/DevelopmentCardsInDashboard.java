package it.polimi.ingsw.server.messages.jsonMessages;

import it.polimi.ingsw.server.messages.Message;

import java.util.ArrayList;

public class DevelopmentCardsInDashboard implements Message {
    private ArrayList<DevelopmentCardMessage> messages;

    public DevelopmentCardsInDashboard(ArrayList<DevelopmentCardMessage> messages) {
        this.messages = messages;
    }

    public ArrayList<DevelopmentCardMessage> getMessages() {
        return messages;
    }
}
