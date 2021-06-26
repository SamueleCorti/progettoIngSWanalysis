package it.polimi.ingsw.server.messages.gameplayMessages;

import it.polimi.ingsw.adapters.Parser;
import it.polimi.ingsw.client.shared.ClientSideSocket;
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

    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(isGui){
            socket.refreshGameboard(this);
        }
        else {
            for(DevelopmentCardMessage developmentCardMessage: messages){
                printDevCard(developmentCardMessage);
            }
        }
    }

    public void printDevCard(DevelopmentCardMessage message){
        Parser parser = new Parser();
        System.out.println("Development card: COLOR "+message.getColor()+ " LEVEL "+message.getLevel());
        System.out.println("Card price: " + parser.parseIntArrayToStringOfResources(message.getCardPrice()));
        System.out.println("Card Stats: " + message.getLevel() + " " + parser.parseIntToColorString(message.getColor()) + ",");
        System.out.println("Production requirements: " + parser.parseIntArrayToStringOfResources(message.getProdRequirements()));
        System.out.println("Production results: " + parser.parseIntArrayToStringOfResources(message.getProdResults()));
        System.out.println("VictoryPoints: " + message.getVictoryPoints());
        System.out.println("\n");
    }
}
