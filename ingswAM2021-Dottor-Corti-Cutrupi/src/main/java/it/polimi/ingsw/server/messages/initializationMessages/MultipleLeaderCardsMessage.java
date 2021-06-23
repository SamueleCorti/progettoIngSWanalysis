package it.polimi.ingsw.server.messages.initializationMessages;

import it.polimi.ingsw.adapters.Parser;
import it.polimi.ingsw.client.shared.ClientSideSocket;
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

    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(isGui){
            socket.addCardToLeaderTables(this);
        }
        else {
            for(LeaderCardMessage leaderCardMessage:messages){
                printLeaderCard(leaderCardMessage);
            }
        }
    }

    public void printLeaderCard(LeaderCardMessage message){
        Parser parser = new Parser();
        System.out.println("\n");
        System.out.println("Leader Card number "+ (message.getLeaderCardZone()+1) + ":");
        if(message.isNeedsResources()==true){
            System.out.println("Resources required: " + parser.parseIntArrayToStringOfResources(message.getResourcesRequired()));
        }else{
            System.out.println("Development cards required: " + parser.parseIntToDevCardRequirement(message.getDevelopmentCardsRequired()));
        }
        System.out.println("Special power: " + parser.parseIntToSpecialPower(message.getSpecialPower()));
        System.out.println("Special power resources: " + parser.parseIntArrayToStringOfResources(message.getSpecialPowerResources()));
        System.out.println("Victory points: " + message.getVictoryPoints());
        if(message.isActive())  System.out.println("This card is currently active\n");
        if(!message.isActive())  System.out.println("This card is currently not active\n");
    }
}
