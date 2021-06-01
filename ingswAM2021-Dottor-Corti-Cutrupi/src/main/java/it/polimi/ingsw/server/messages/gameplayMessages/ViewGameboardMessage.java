package it.polimi.ingsw.server.messages.gameplayMessages;

import it.polimi.ingsw.model.boardsAndPlayer.Player;
import it.polimi.ingsw.server.messages.Message;
import it.polimi.ingsw.server.messages.jsonMessages.DevelopmentCardMessage;

import java.util.ArrayList;

public class ViewGameboardMessage implements Message {
    private DevelopmentCardMessage[] messages= new DevelopmentCardMessage[12];
    private int[] resources= new int[4];

    public ViewGameboardMessage(DevelopmentCardMessage[] messages, int[] resources) {
        this.messages = messages;
        this.resources=resources;
    }

    public ViewGameboardMessage(DevelopmentCardMessage[] messages){
        this.messages=messages;
    }

    public DevelopmentCardMessage[] getMessages() {
        return messages;
    }

    public int[] getResources() {
        return resources;
    }
}
