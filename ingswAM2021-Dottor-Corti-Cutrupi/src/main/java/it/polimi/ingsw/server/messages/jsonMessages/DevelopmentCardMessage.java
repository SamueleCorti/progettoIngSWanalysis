package it.polimi.ingsw.server.messages.jsonMessages;

import it.polimi.ingsw.client.gui.utility.DevelopmentCardForGUI;
import it.polimi.ingsw.model.developmentcard.Color;
import it.polimi.ingsw.model.resource.ResourceType;
import it.polimi.ingsw.server.messages.Message;
import it.polimi.ingsw.model.developmentcard.DevelopmentCard;
import it.polimi.ingsw.model.requirements.ResourcesRequirements;
import it.polimi.ingsw.model.requirements.ResourcesRequirementsForAcquisition;
import it.polimi.ingsw.model.resource.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DevelopmentCardMessage implements Message {

    private final int[] cardPrice;
    private final int level;
    private final int color;
    private final int[] prodRequirements;
    private final int[] prodResults;
    private final int victoryPoints;
    private int devCardZone;

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

    @Override
    public String toString() {
        return "DevelopmentCardMessage{" +
                "cardPrice=" + Arrays.toString(cardPrice) +
                ", level=" + level +
                ", color=" + color +
                ", prodRequirements=" + Arrays.toString(prodRequirements) +
                ", prodResults=" + Arrays.toString(prodResults) +
                ", victoryPoints=" + victoryPoints +
                ", devCardZone=" + devCardZone +
                '}';
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

    /* private String printDevCards(DevelopmentCard card) {
        if(card==null)  return "This deck is finished!\n";
        String string="\nHere is the new development card: \n";
        string+="Color: "+ card.getCardStats().getValue1()+"\tlevel: "+card.getCardStats().getValue0()+" \tvictory points: "+card.getVictoryPoints();
        string+="\nCard cost: \t";
        for(ResourcesRequirementsForAcquisition requirements: card.getCardPrice())
            string+=requirements.getResourcesRequired().getValue0() +" "+ requirements.getResourcesRequired().getValue1().getResourceType()+"s\t";
        string+="\nProduction cost: \n";
        for(ResourcesRequirements resourcesRequirements: card.getProdRequirements()){
            string+= resourcesRequirements.getResourcesRequired().getValue0()+" "+ resourcesRequirements.getResourcesRequired().getValue1().getResourceType()+"s\t";
        }
        string+="\n";
        string+="Resources produced: \n";
        for(Resource resource: card.getProdResults())
            string+= resource.getResourceType()+"\t";
        string+="\n";
        return string;
    }*/

}
