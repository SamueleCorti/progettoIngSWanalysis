package it.polimi.ingsw.server.messages.jsonMessages;

import it.polimi.ingsw.adapters.Parser;
import it.polimi.ingsw.client.shared.ClientSideSocket;
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

    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(isGui){
            for(DevelopmentCardMessage developmentCardMessage: messages){
                if(socket.checkShowingOtherPlayerDashboard()){
                    socket.addCardToAnotherPlayerDevCardZone(this);
                }else{
                    socket.addCardToYourDevCardZone(this);
                }
            }
        }
        else {
            for(DevelopmentCardMessage message: messages)
                printDevCard(message);
        }
    }

    public void printDevCard(DevelopmentCardMessage message){
        Parser parser = new Parser();
        System.out.println("Development card:");
        System.out.println("Card price: " + parser.parseIntArrayToStringOfResources(message.getCardPrice()));
        System.out.println("Card Stats: " + message.getLevel() + " " + parser.parseIntToColorString(message.getColor()) + ",");
        System.out.println("Production requirements: " + parser.parseIntArrayToStringOfResources(message.getProdRequirements()));
        System.out.println("Production results: " + parser.parseIntArrayToStringOfResources(message.getProdResults()));
        System.out.println("VictoryPoints: " + message.getVictoryPoints());
        System.out.println("\n");
    }
}
