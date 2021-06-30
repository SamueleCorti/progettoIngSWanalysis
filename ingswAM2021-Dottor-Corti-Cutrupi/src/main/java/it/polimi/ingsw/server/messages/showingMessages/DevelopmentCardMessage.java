package it.polimi.ingsw.server.messages.showingMessages;

import it.polimi.ingsw.adapters.Parser;
import it.polimi.ingsw.client.shared.ClientSideSocket;
import it.polimi.ingsw.model.developmentcard.Color;
import it.polimi.ingsw.model.resource.ResourceType;
import it.polimi.ingsw.server.messages.Message;
import it.polimi.ingsw.model.developmentcard.DevelopmentCard;
import it.polimi.ingsw.model.requirements.ResourcesRequirements;
import it.polimi.ingsw.model.requirements.ResourcesRequirementsForAcquisition;
import it.polimi.ingsw.model.resource.Resource;

import java.util.Arrays;
import java.util.List;

/**
 * Self explanatory name
 */
public class DevelopmentCardMessage implements Message {

    private final int[] cardPrice;
    private final int level;
    private final int color;
    private final int[] prodRequirements;
    private final int[] prodResults;
    private final int victoryPoints;
    private int devCardZone;
    private boolean wasCardModified;

    public DevelopmentCardMessage(DevelopmentCard developmentCard, int devCardZone){
        this.devCardZone = devCardZone;
        int[] tempPrice = new int[5];
        List<ResourcesRequirementsForAcquisition> price = developmentCard.getCardPrice();
        for(ResourcesRequirementsForAcquisition priceRequirement : price){
            tempPrice[parseResourceToInt(priceRequirement.getResourcesRequired().getValue1())]+=priceRequirement.getResourcesRequired().getValue0();
        }
        this.cardPrice=tempPrice;
        this.level = developmentCard.getCardStats().getValue0();
        this.color = parseColorToInt(developmentCard.getCardStats().getValue1());
        List<ResourcesRequirements> prodRequirements = developmentCard.getProdRequirements();
        int[] tempProdReq = new int [5];
        for(ResourcesRequirements prodRequirement : prodRequirements){
            tempProdReq[parseResourceToInt(prodRequirement.getResourcesRequired().getValue1())]+=prodRequirement.getResourcesRequired().getValue0();
        }
        this.prodRequirements = tempProdReq;
        int[] tempProdResult = new int[5];
        List <Resource> prodResult = developmentCard.getProdResults();
        for(Resource resource : prodResult){
            tempProdResult[parseResourceToInt(resource)]+=1;
        }
        this.prodResults = tempProdResult;
        this.victoryPoints = developmentCard.getVictoryPoints();
        this.wasCardModified = developmentCard.isWasCardModified();
    }

    public int parseResourceToInt(Resource resource){
        if(resource.getResourceType().equals(ResourceType.Coin)){
            return 0;
        }else if(resource.getResourceType().equals(ResourceType.Stone)){
            return 1;
        }else if(resource.getResourceType().equals(ResourceType.Servant)){
            return 2;
        }else if(resource.getResourceType().equals(ResourceType.Shield)){
            return 3;
        }else if(resource.getResourceType().equals(ResourceType.Faith)){
            return 4;
        }
        else{
            System.out.println("There was an error in parsing the resource!");
            return 150;
        }
    }

    public int parseColorToInt(Color color){
        if(color.equals(Color.Blue)){
            return 0;
        }else if(color.equals(Color.Green)){
            return 1;
        }else if(color.equals(Color.Yellow)){
            return 2;
        }else if(color.equals(Color.Purple)){
            return 3;
        }

        else{
            System.out.println("There was an error in parsing the color!");
            return 150;
        }
    }

    public int[] getCardPrice() {
        return cardPrice;
    }

    public int getLevel() {
        return level;
    }

    public int getColor() {
        return color;
    }

    public int[] getProdRequirements() {
        return prodRequirements;
    }

    public int[] getProdResults() {
        return prodResults;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public int getDevCardZone() {
        return devCardZone;
    }

    public boolean isWasCardModified() {
        return wasCardModified;
    }


    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(isGui){
            socket.refreshGameboard(this);
        }
        else {
            printDevCard(this);
        }
    }

    public void printDevCard(DevelopmentCardMessage message){
        SerializationConverter parser = new SerializationConverter();
        System.out.println("Development card:");
        System.out.println("Card price: " + parser.parseIntArrayToStringOfResourcesPretty(message.getCardPrice()));
        System.out.println("Production requirements: " + parser.parseIntArrayToStringOfResourcesPretty(message.getProdRequirements()));
        System.out.println("Production results: " + parser.parseIntArrayToStringOfResourcesPretty(message.getProdResults()));
        System.out.println("VictoryPoints: " + message.getVictoryPoints());
        System.out.println("\n");
    }
}