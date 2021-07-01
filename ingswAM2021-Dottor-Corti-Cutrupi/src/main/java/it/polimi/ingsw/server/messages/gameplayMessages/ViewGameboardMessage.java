package it.polimi.ingsw.server.messages.gameplayMessages;

import it.polimi.ingsw.adapters.Parser;
import it.polimi.ingsw.client.shared.ClientSideSocket;
import it.polimi.ingsw.server.messages.Message;
import it.polimi.ingsw.server.messages.showingMessages.DevelopmentCardMessage;
import it.polimi.ingsw.server.messages.showingMessages.SerializationConverter;

/**
 * Self explanatory name
 */
public class ViewGameboardMessage implements Message {
    private DevelopmentCardMessage[] messages= new DevelopmentCardMessage[12];
    private int[] resources= new int[4];

    public ViewGameboardMessage(DevelopmentCardMessage[] messages, int[] resources) {
        this.messages = messages;
        this.resources=resources;
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
        SerializationConverter parser = new SerializationConverter();
        System.out.println("Development card: COLOR "+parser.parseIntToColorString(message.getColor())+ " LEVEL "+message.getLevel());
        System.out.println("Card price: " + parser.parseIntArrayToStringOfResourcesPretty(message.getCardPrice()));
        System.out.println("Production requirements: " + parser.parseIntArrayToStringOfResourcesPretty(message.getProdRequirements()));
        System.out.println("Production results: " + parser.parseIntArrayToStringOfResourcesPretty(message.getProdResults()));
        System.out.println("VictoryPoints: " + message.getVictoryPoints());
        System.out.println("\n");
    }
}