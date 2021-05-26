package it.polimi.ingsw;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.model.boardsAndPlayer.Dashboard;
import it.polimi.ingsw.model.developmentcard.Color;
import it.polimi.ingsw.model.developmentcard.DevelopmentCard;
import it.polimi.ingsw.model.developmentcard.DevelopmentCardZone;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.leadercard.LeaderCardForJson;
import it.polimi.ingsw.model.leadercard.leaderpowers.*;
import it.polimi.ingsw.model.requirements.*;
import it.polimi.ingsw.model.resource.*;
import it.polimi.ingsw.server.messages.jsonMessages.DevelopmentCardMessage;
import it.polimi.ingsw.server.messages.jsonMessages.LeaderCardMessage;
import org.javatuples.Pair;
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
        System.out.println(message);
    }
}