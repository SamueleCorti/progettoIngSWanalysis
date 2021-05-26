package it.polimi.ingsw;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.client.cli.MessageHandler;
import it.polimi.ingsw.model.boardsAndPlayer.Dashboard;
import it.polimi.ingsw.model.developmentcard.Color;
import it.polimi.ingsw.model.developmentcard.DevelopmentCard;
import it.polimi.ingsw.model.developmentcard.DevelopmentCardZone;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.leadercard.LeaderCardForJson;
import it.polimi.ingsw.model.leadercard.leaderpowers.*;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.papalpath.CardCondition;
import it.polimi.ingsw.model.papalpath.PapalPath;
import it.polimi.ingsw.model.requirements.*;
import it.polimi.ingsw.model.resource.*;
import it.polimi.ingsw.server.messages.jsonMessages.DevelopmentCardMessage;
import it.polimi.ingsw.server.messages.jsonMessages.LeaderCardMessage;
import it.polimi.ingsw.server.messages.jsonMessages.ResourceToIntConverter;
import org.javatuples.Pair;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Locale;

public class MessagesTest {

    @Test
    public void testingDevelopmentCardMessageCreation(){
        CoinResource coin = new CoinResource();
        StoneResource stone = new StoneResource();
        ServantResource servant = new ServantResource();
        ResourcesRequirementsForAcquisition requirement1 = new ResourcesRequirementsForAcquisition(4, coin);
        ResourcesRequirementsForAcquisition requirement2 = new ResourcesRequirementsForAcquisition(2, stone);
        ResourcesRequirements requirement3 = new ResourcesRequirements(3, coin);
        ResourcesRequirements requirement4 = new ResourcesRequirements(5, stone);
        ArrayList<ResourcesRequirementsForAcquisition> requirements1 = new ArrayList<ResourcesRequirementsForAcquisition>();
        ArrayList<ResourcesRequirements> requirements2 = new ArrayList<ResourcesRequirements>();
        Pair <Integer, Color> stat1 = new Pair<Integer, Color>(3,Color.Blue);
        ArrayList<Resource> prod1 = new ArrayList<Resource>();
        Dashboard dashboard = new Dashboard(3);
        DevelopmentCardZone cardZone1 = new DevelopmentCardZone();
        ShieldResource shield = new ShieldResource();
        ResourcesRequirementsForAcquisition requirementTest1 = new ResourcesRequirementsForAcquisition(2,shield);
        ResourcesRequirements requirementTest2 = new ResourcesRequirements(1, coin);
        Pair<Integer, Color> statTest = new Pair<>(1,Color.Green);
        ArrayList<ResourcesRequirementsForAcquisition> arrayReq1 = new ArrayList<ResourcesRequirementsForAcquisition>();
        arrayReq1.add(requirementTest1);
        ArrayList<ResourcesRequirements> arrayReq2 = new ArrayList<ResourcesRequirements>();
        arrayReq2.add(requirementTest2);
        prod1.add(new FaithResource());
        DevelopmentCard card = new DevelopmentCard(arrayReq1,statTest,arrayReq2,prod1,1);

        DevelopmentCardMessage message = new DevelopmentCardMessage(card,2);
        System.out.println(message);


    }

    public void printDevCard(DevelopmentCardMessage message){
        System.out.println("Development card:");
        System.out.println("Card price: " + parseIntArrayToStringOfResources(message.getCardPrice()));
        System.out.println("Card Stats: " + message.getLevel() + " " + parseIntToColorString(message.getColor()));
        System.out.println("Production requirements: " + parseIntArrayToStringOfResources(message.getProdRequirements()));
        System.out.println("Production results: " + parseIntArrayToStringOfResources(message.getProdResults()));
        System.out.println("VictoryPoints: " + message.getVictoryPoints());
    }

    public String parseIntToSpecialPower(int i){
        if(i==0){
            return "Discount";
        }else if(i==1){
            return "ExtraDeposit";
        }else if(i==2){
            return "ExtraProd";
        }else if(i==3){
            return "WhiteToColor";
        }else {
            return "error";
        }
    }


    public String parseIntArrayToStringOfResources(int[] resources){
        String string = new String();
        if(resources[0]!=0) {
            string += resources[0];
            string += " coins, \t";
        }
        if(resources[1]!=0) {
            string += resources[1];
            string += " stones, \t";
        }
        if(resources[2]!=0) {
            string += resources[2];
            string += " servants, \t";
        }
        if(resources[3]!=0) {
            string += resources[3];
            string += " shields, \t";
        }

            if (resources[4] != 0) {
                string += resources[4];
                string += " faith, \t";
            }
        return string;
    }

    public String parseIntToColorString(int i){
        if(i==0){
            return "blue";
        }else if(i==1){
            return "green";
        }else if(i==2){
            return "yellow";
        }else if(i==3){
            return "purple";
        }else {
            return "error";
        }
    }

    public String parseIntToDevCardRequirement(int[] decks){
        String string = new String();
        if(decks[0]!=0) {
            string += decks[0];
            string += " Blue development cards level 1, \t";
        }
        if(decks[1]!=0) {
            string += decks[1];
            string += " Blue development cards level 2, \t";
        }
        if(decks[2]!=0) {
            string += decks[2];
            string += " Blue development cards level 3, \t";
        }
        if(decks[3]!=0) {
            string += decks[3];
            string += " Green development cards level 1, \t";
        }
        if(decks[4]!=0) {
            string += decks[4];
            string += " Green development cards level 2, \t";
        }
        if(decks[5]!=0) {
            string += decks[5];
            string += " Green development cards level 3, \t";
        }
        if(decks[6]!=0) {
            string += decks[6];
            string += " Yellow development cards level 1, \t";
        }
        if(decks[7]!=0) {
            string += decks[7];
            string += " Yellow development cards level 2, \t";
        }if(decks[8]!=0) {
            string += decks[8];
            string += " Yellow development cards level 3, \t";
        }
        if(decks[9]!=0) {
            string += decks[9];
            string += " Purple development cards level 1, \t";
        }
        if(decks[10]!=0) {
            string += decks[10];
            string += " Purple development cards level 2, \t";
        }
        if(decks[11]!=0) {
            string += decks[11];
            string += " Purple development cards level 3, \t";
        }
        return string;
    }

    @Test
    public void testingDevelopmentCardMessagePrint(){
        CoinResource coin = new CoinResource();
        StoneResource stone = new StoneResource();
        ServantResource servant = new ServantResource();
        ResourcesRequirementsForAcquisition requirement1 = new ResourcesRequirementsForAcquisition(4, coin);
        ResourcesRequirementsForAcquisition requirement2 = new ResourcesRequirementsForAcquisition(2, stone);
        ResourcesRequirements requirement3 = new ResourcesRequirements(3, coin);
        ResourcesRequirements requirement4 = new ResourcesRequirements(5, stone);
        ArrayList<ResourcesRequirementsForAcquisition> requirements1 = new ArrayList<ResourcesRequirementsForAcquisition>();
        ArrayList<ResourcesRequirements> requirements2 = new ArrayList<ResourcesRequirements>();
        Pair <Integer, Color> stat1 = new Pair<Integer, Color>(3,Color.Blue);
        ArrayList<Resource> prod1 = new ArrayList<Resource>();
        Dashboard dashboard = new Dashboard(3);
        DevelopmentCardZone cardZone1 = new DevelopmentCardZone();
        ShieldResource shield = new ShieldResource();
        ResourcesRequirementsForAcquisition requirementTest1 = new ResourcesRequirementsForAcquisition(2,shield);
        ResourcesRequirements requirementTest2 = new ResourcesRequirements(1, coin);
        Pair<Integer, Color> statTest = new Pair<>(1,Color.Green);
        ArrayList<ResourcesRequirementsForAcquisition> arrayReq1 = new ArrayList<ResourcesRequirementsForAcquisition>();
        arrayReq1.add(requirementTest1);
        ArrayList<ResourcesRequirements> arrayReq2 = new ArrayList<ResourcesRequirements>();
        arrayReq2.add(requirementTest2);
        prod1.add(new FaithResource());
        DevelopmentCard card = new DevelopmentCard(arrayReq1,statTest,arrayReq2,prod1,1);

        DevelopmentCardMessage message = new DevelopmentCardMessage(card,2);

        printDevCard(message);

    }

    public void printLeaderCard(LeaderCardMessage message){
        System.out.println("Leader Card number "+ message.getLeaderCardZone() + ":");
        if(message.isNeedsResources()==true){
            System.out.println("Resources required: " + parseIntArrayToStringOfResources(message.getResourcesRequired()));
        }else{
            System.out.println("Development cards required: " + parseIntToDevCardRequirement(message.getDevelopmentCardsRequired()));
        }
        System.out.println("Special power: " + parseIntToSpecialPower(message.getSpecialPower()));
        System.out.println("Special power resources: " + parseIntArrayToStringOfResources(message.getSpecialPowerResources()));
        System.out.println("Victory points: " + message.getVictoryPoints());
    }

    @Test
    public void testingLeaderCardMessageCreation() {
        ArrayList<LeaderCard> leaderCards = new ArrayList<LeaderCard>();
        int i;
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader("src/main/resources/LeaderCardsInstancing.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        JsonParser parser = new JsonParser();
        JsonArray cardsArray = parser.parse(reader).getAsJsonArray();

        for (JsonElement jsonElement : cardsArray) {
            Gson gson = new Gson();
            LeaderCardForJson cardRecreated = gson.fromJson(jsonElement.getAsJsonObject(), LeaderCardForJson.class);

            /**here we convert the card requirements
             *
             */
            i = 0;
            ArrayList<Requirements> requirements = new ArrayList<Requirements>();
            /**case were teh card has resources requirements
             *
             */
            if (cardRecreated.getTypeOfRequirement().equals("resources")) {
                for (Integer quantity : cardRecreated.getAmountOfForResourcesRequirement()) {
                    Resource resourceForPrice;
                    if (cardRecreated.getResourcesRequired().get(i).equals("coin")) {
                        resourceForPrice = new CoinResource();
                    } else if (cardRecreated.getResourcesRequired().get(i).equals("stone")) {
                        resourceForPrice = new StoneResource();
                    } else if (cardRecreated.getResourcesRequired().get(i).equals("shield")) {
                        resourceForPrice = new ShieldResource();
                    } else {
                        resourceForPrice = new ServantResource();
                    }
                    ResourcesRequirementsForLeaderCards requirement = new ResourcesRequirementsForLeaderCards(quantity, resourceForPrice);
                    requirements.add(requirement);
                    i++;
                }
            }
            /**case where the card has development requirements
             *
             */
            else {
                i = 0;
                for (Integer quantity : cardRecreated.getAmountOfForDevelopmentRequirement()) {
                    DevelopmentRequirements requirement;
                    if (cardRecreated.getColorsRequired().get(i).equals("blue")) {
                        requirement = new DevelopmentRequirements(quantity, cardRecreated.getLevelsRequired().get(i), Color.Blue);
                    } else if (cardRecreated.getColorsRequired().get(i).equals("yellow")) {
                        requirement = new DevelopmentRequirements(quantity, cardRecreated.getLevelsRequired().get(i), Color.Yellow);
                    } else if (cardRecreated.getColorsRequired().get(i).equals("green")) {
                        requirement = new DevelopmentRequirements(quantity, cardRecreated.getLevelsRequired().get(i), Color.Green);
                    } else {
                        requirement = new DevelopmentRequirements(quantity, cardRecreated.getLevelsRequired().get(i), Color.Purple);
                    }
                    requirements.add(requirement);
                    i++;
                }
            }

            /**here we convert the Leader Power
             *
             */
            LeaderPower leaderPower;
            /**first we get the correct resource of the power
             *
             */
            ArrayList<Resource> resourcesForLeaderPower = new ArrayList<Resource>();

            for (String string : cardRecreated.getSpecialPowerResources()) {
                if (string.toLowerCase(Locale.ROOT).equals("coin")) {
                    resourcesForLeaderPower.add(new CoinResource());
                } else if (string.toLowerCase(Locale.ROOT).equals("stone")) {
                    resourcesForLeaderPower.add(new StoneResource());
                } else if (string.toLowerCase(Locale.ROOT).equals("shield")) {
                    resourcesForLeaderPower.add(new ShieldResource());
                } else {
                    resourcesForLeaderPower.add(new ServantResource());
                }
            }


            /**and then the leader power type
             *
             */
            if (cardRecreated.getSpecialPower().equals("discount")) {
                leaderPower = new Discount(resourcesForLeaderPower);
            } else if (cardRecreated.getSpecialPower().equals("extradeposit")) {
                leaderPower = new ExtraDeposit(resourcesForLeaderPower);
            } else if (cardRecreated.getSpecialPower().equals("extraprod")) {
                leaderPower = new ExtraProd(resourcesForLeaderPower);
            } else {
                leaderPower = new WhiteToColor(resourcesForLeaderPower);
            }
            LeaderCard cardToAdd = new LeaderCard(requirements, cardRecreated.getVictoryPoints(), leaderPower);
            leaderCards.add(cardToAdd);
        }
        LeaderCardMessage message = new LeaderCardMessage(leaderCards.get(0), 2);
        printLeaderCard(message);
        LeaderCardMessage message2 = new LeaderCardMessage(leaderCards.get(10), 2);
        printLeaderCard(message2);

    }
    ResourceToIntConverter resourceToIntConverter= new ResourceToIntConverter();


    @Test
    public void shouldAnswerWithTrue() {
        int floatMarble;
        int representation[][] = new int[3][4];
        Market market = new Market();
        market.printMarket();
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 4; column++) {
                if (market.reresourceTypeInMarket(row, column).equals(ResourceType.Coin)) {
                    representation[row][column] = 0;
                } else if (market.reresourceTypeInMarket(row, column).equals(ResourceType.Stone)) {
                    representation[row][column] = 1;
                } else if (market.reresourceTypeInMarket(row, column).equals(ResourceType.Servant)) {
                    representation[row][column] = 2;
                } else if (market.reresourceTypeInMarket(row, column).equals(ResourceType.Shield)) {
                    representation[row][column] = 3;
                } else if (market.reresourceTypeInMarket(row, column).equals(ResourceType.Faith)) {
                    representation[row][column] = 4;
                } else if (market.reresourceTypeInMarket(row, column).equals(ResourceType.Blank)) {
                    representation[row][column] = 5;
                }
                else representation[row][column] = 25;
            }
        }
        floatMarble= resourceToIntConverter.converter(market.getFloatingMarble());

        Resource[][] fakeMarket = new Resource[3][4];
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 4; column++) {
                switch (representation[row][column]) {
                    case 0:
                        fakeMarket[row][column] = new CoinResource();
                        break;
                    case 1:
                        fakeMarket[row][column] = new StoneResource();
                        break;
                    case 2:
                        fakeMarket[row][column] = new ServantResource();
                        break;
                    case 3:
                        fakeMarket[row][column] = new ShieldResource();
                        break;
                    case 4:
                        fakeMarket[row][column] = new FaithResource();
                        break;
                    case 5:
                        fakeMarket[row][column] = new BlankResource();
                        break;
                }
            }
        }
        Resource floatingMarble= resourceToIntConverter.intToResource(floatMarble);

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 4; column++) {
                System.out.print(fakeMarket[row][column].getResourceType() + "\t");
            }
            System.out.println();
        }
        System.out.println("\t\t\t\t\t\t\t\t" + floatingMarble.getResourceType());
        System.out.println();
    }

    @Test
    public void papalMessageTest(){
        int[] tiles= new int[25];
        int[] victoryPoints= new int[25];
        int playerFaithPos=0;
        PapalPath papalPath=new PapalPath(1);

        for(int i=0;i<25;i++){
            tiles[i]=papalPath.getPapalTiles().get(i).getNumOfReportSection();
            if(papalPath.isPopeSpace(i)){
                if(papalPath.getCards(papalPath.getPapalTiles().get(i).getNumOfReportSection()-1).getCondition()== CardCondition.Active)   tiles[1]+=30;
                else if(papalPath.getCards(papalPath.getPapalTiles().get(i).getNumOfReportSection()-1).getCondition()== CardCondition.Discarded)   tiles[1]+=10;
                else if(papalPath.getCards(papalPath.getPapalTiles().get(i).getNumOfReportSection()-1).getCondition()== CardCondition.Inactive)   tiles[1]+=20;
            }
            victoryPoints[i]=papalPath.getPapalTiles().get(i).getVictoryPoints();
        }
        playerFaithPos=papalPath.getFaithPosition();

        String string= new String();
        for(int i=0;i<=24;i++){
            if(playerFaithPos!=i){
                if(papalPath.getPapalTiles().get(i).isPopeSpace()) string+="X|";
                else if(papalPath.getPapalTiles().get(i).getNumOfReportSection()!=0) string+="x|";
                else string+=" |";
            }
            else if(papalPath.getPapalTiles().get(i).isPopeSpace()) string+="O|";
            else if(papalPath.getPapalTiles().get(i).getNumOfReportSection()!=0) string+="O|";
            else string+="o|";
        }
        System.out.println(string);
    }

}
