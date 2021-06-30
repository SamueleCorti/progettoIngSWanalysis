package it.polimi.ingsw.server.messages.showingMessages;

import it.polimi.ingsw.adapters.Parser;
import it.polimi.ingsw.client.shared.ClientSideSocket;
import it.polimi.ingsw.model.developmentcard.Color;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.leadercard.leaderpowers.PowerType;
import it.polimi.ingsw.model.papalpath.CardCondition;
import it.polimi.ingsw.model.requirements.DevelopmentRequirements;
import it.polimi.ingsw.model.requirements.Requirements;
import it.polimi.ingsw.model.requirements.ResourcesRequirementsForLeaderCards;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceType;
import it.polimi.ingsw.server.messages.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Self explanatory name
 */
public class LeaderCardMessage implements Message{
    boolean needsResources;
    int[] resourcesRequired;
    int[] developmentCardsRequired;
    int specialPower;
    int[] specialPowerResources;
    int victoryPoints;
    int leaderCardZone;
    boolean isActive;
    boolean wasCardModified;

    public LeaderCardMessage(LeaderCard leaderCard,int leaderCardZone){
        if(leaderCard.getCardRequirements().get(0) instanceof ResourcesRequirementsForLeaderCards){
            this.needsResources = true;

            ArrayList<ResourcesRequirementsForLeaderCards> resourcesRequirements = new ArrayList<ResourcesRequirementsForLeaderCards>();
            for(Requirements requirement: leaderCard.getCardRequirements()){
                resourcesRequirements.add((ResourcesRequirementsForLeaderCards) requirement);
            }

            this.leaderCardZone = leaderCardZone;

            int[] tempReq = new int [5];
            for(ResourcesRequirementsForLeaderCards resourcesRequirement : resourcesRequirements){
                tempReq[parseResourceToInt(resourcesRequirement.getResourcesRequired().getValue1())]+=resourcesRequirement.getResourcesRequired().getValue0();
            }
            this.resourcesRequired = tempReq;
        }else{
            this.needsResources = false;
            ArrayList<DevelopmentRequirements> devRequirements = new ArrayList<DevelopmentRequirements>();
            for(Requirements requirement: leaderCard.getCardRequirements()){
                devRequirements.add((DevelopmentRequirements) requirement);
            }
            int[] tempReq = new int [12];
            for(DevelopmentRequirements devRequirement : devRequirements){
                tempReq[parseColorAndLevelToInt(devRequirement.getColorRequired(),devRequirement.getLevelRequired())]+=devRequirement.getAmountOfDevelopmentRequired();
            }
            this.developmentCardsRequired = tempReq;
        }

        this.wasCardModified = leaderCard.isWasCardModified();
        this.victoryPoints=leaderCard.getVictoryPoints();

        if(leaderCard.getLeaderPower().returnPowerType().equals(PowerType.Discount)){
            this.specialPower=0;
        }else if(leaderCard.getLeaderPower().returnPowerType().equals(PowerType.ExtraDeposit)){
            this.specialPower=1;
        }else if(leaderCard.getLeaderPower().returnPowerType().equals(PowerType.ExtraProd)){
            this.specialPower=2;
        }else if(leaderCard.getLeaderPower().returnPowerType().equals(PowerType.WhiteToColor)){
            this.specialPower=3;
        }

        int[] specialResourceTemp = new int[5];
        List <Resource> resourceList = leaderCard.getLeaderPower().returnRelatedResourcesCopy();
        for(Resource resource : resourceList){
            specialResourceTemp[parseResourceToInt(resource)]+=1;
        }
        this.specialPowerResources=specialResourceTemp;

        this.leaderCardZone = leaderCardZone;
        isActive= leaderCard.getCondition()== CardCondition.Active;
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

    public int parseColorAndLevelToInt(Color color, int level){
        int num;
        if(color.equals(Color.Blue)){
            num=0;
        }else if(color.equals(Color.Green)){
            num=3;
        }else if(color.equals(Color.Yellow)){
            num=6;
        }else if(color.equals(Color.Purple)){
            num=9;
        }
        else{
            System.out.println("There was an error in parsing the color!");
            return 150;
        }
        return num+level-1;
    }

    @Override
    public String toString() {
        return "LeaderCardMessage{" +
                "needsResources=" + needsResources +
                ", resourcesRequired=" + Arrays.toString(resourcesRequired) +
                ", developmentCardsRequired=" + Arrays.toString(developmentCardsRequired) +
                ", specialPower=" + specialPower +
                ", specialPowerResources=" + Arrays.toString(specialPowerResources) +
                ", victoryPoints=" + victoryPoints +
                ", leaderCardZone=" + leaderCardZone +
                '}';
    }

    public boolean isNeedsResources() {
        return needsResources;
    }

    public int[] getResourcesRequired() {
        return resourcesRequired;
    }

    public int[] getDevelopmentCardsRequired() {
        return developmentCardsRequired;
    }

    public int getSpecialPower() {
        return specialPower;
    }

    public int[] getSpecialPowerResources() {
        return specialPowerResources;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public int getLeaderCardZone() {
        return leaderCardZone;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isWasCardModified() {
        return wasCardModified;
    }

    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(!isGui){
            printLeaderCard(this);
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
