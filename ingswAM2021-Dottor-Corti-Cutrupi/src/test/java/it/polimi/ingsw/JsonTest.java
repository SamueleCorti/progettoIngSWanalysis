package it.polimi.ingsw;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.Model.boardsAndPlayer.Dashboard;
import it.polimi.ingsw.Model.adapters.InterfaceAdapter;
import it.polimi.ingsw.Model.developmentcard.Color;
import it.polimi.ingsw.Model.developmentcard.DevelopmentCard;
import it.polimi.ingsw.Model.developmentcard.DevelopmentCardForJson;
import it.polimi.ingsw.Model.developmentcard.DevelopmentCardZone;
import it.polimi.ingsw.Model.leadercard.LeaderCard;
import it.polimi.ingsw.Model.leadercard.LeaderCardDeck;
import it.polimi.ingsw.Model.leadercard.LeaderCardForJson;
import it.polimi.ingsw.Model.papalpath.PapalPath;
import it.polimi.ingsw.Model.papalpath.PapalPathModifier;
import it.polimi.ingsw.Model.requirements.ResourcesRequirements;
import it.polimi.ingsw.Model.requirements.ResourcesRequirementsForAcquisition;
import it.polimi.ingsw.Model.resource.CoinResource;
import it.polimi.ingsw.Model.resource.Resource;
import it.polimi.ingsw.Model.resource.ServantResource;
import it.polimi.ingsw.Model.resource.StoneResource;
import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {

    public class Car {
        public String brand = null;
        public int    doors = 0;
    }

    /**in this test we check if Gson is installed properly and working
     *
     */
    @Test
    public void firstTest(){
        Car car = new Car();
        car.brand = "Rover";
        car.doors = 5;

        Gson gson = new Gson();
        String json = gson.toJson(car);
        System.out.println(json);
    }

    /**in this test we try to serialize a CoinResource and a DevelopmentCard
     *
     */
    @Test
    public void secondTest() throws FileNotFoundException {
        CoinResource coin1= new CoinResource();
        StoneResource stone1= new StoneResource();
        ServantResource servant1=new ServantResource();
        ResourcesRequirementsForAcquisition requirement1 = new ResourcesRequirementsForAcquisition(4,coin1);
        ResourcesRequirementsForAcquisition requirement2 = new ResourcesRequirementsForAcquisition(2,stone1);
        ResourcesRequirements requirement3 = new ResourcesRequirements(3,coin1);
        ResourcesRequirements requirement4 = new ResourcesRequirements(5,stone1);
        ArrayList<ResourcesRequirementsForAcquisition> requirements1 = new ArrayList<ResourcesRequirementsForAcquisition>();
        ArrayList<ResourcesRequirements> requirements2 = new ArrayList<ResourcesRequirements>();
        Pair<Integer, Color> stat1 = new Pair<Integer, Color>(3,Color.Blue);
        ArrayList<Resource> prod1 = new ArrayList<Resource>();
        Dashboard dashboard = new Dashboard(3);
        DevelopmentCardZone cardZone1 = new DevelopmentCardZone();
        requirements1.add(requirement1);
        requirements1.add(requirement2);
        requirements2.add(requirement3);
        requirements2.add(requirement4);
        prod1.add(servant1);
        prod1.add(servant1);
        prod1.add(servant1);
        DevelopmentCard card1 = new DevelopmentCard(requirements1,stat1,requirements2,prod1,5);
        dashboard.getWarehouse().addResource(coin1);
        dashboard.getWarehouse().addResource(coin1);
        dashboard.getWarehouse().addResource(stone1);
        dashboard.getWarehouse().addResource(stone1);
        dashboard.getStrongbox().addResource(coin1);
        dashboard.getStrongbox().addResource(coin1);
        dashboard.getDevelopmentCardZones().add(cardZone1);
        dashboard.getDevelopmentCardZones().get(0).addNewCard(card1);
        assertEquals(true,dashboard.getDevelopmentCardZones().get(0).getLastCard().checkPrice(dashboard));
        assertEquals(false,dashboard.getDevelopmentCardZones().get(0).getLastCard().checkRequirements(dashboard));
        System.out.println("card:");
        Gson cardGson = new GsonBuilder().setPrettyPrinting().create();
        String cardjson = cardGson.toJson(card1);
        System.out.println(cardjson);
        System.out.println("coin:");
        Gson coinGson = new GsonBuilder().setPrettyPrinting().create();
        String coinJson = cardGson.toJson(coin1);
        System.out.println(coinJson);
    }

    /**in this test we try to deserialize the ResourceCoin and DevelopmentCard classes
     *
     */
    @Test
    public void thirdTest() throws FileNotFoundException {
        CoinResource coin1=new CoinResource();
        StoneResource stone1=new StoneResource();
        ServantResource servant1=new ServantResource();
        ResourcesRequirementsForAcquisition requirement1 = new ResourcesRequirementsForAcquisition(4,coin1);
        ResourcesRequirementsForAcquisition requirement2 = new ResourcesRequirementsForAcquisition(2,stone1);
        ResourcesRequirements requirement3 = new ResourcesRequirements(3,coin1);
        ResourcesRequirements requirement4 = new ResourcesRequirements(5,stone1);
        ArrayList<ResourcesRequirementsForAcquisition> requirements1 = new ArrayList<ResourcesRequirementsForAcquisition>();
        ArrayList<ResourcesRequirements> requirements2 = new ArrayList<ResourcesRequirements>();
        Pair<Integer, Color> stat1 = new Pair<Integer, Color>(3,Color.Blue);
        ArrayList<Resource> prod1 = new ArrayList<Resource>();
        Dashboard dashboard = new Dashboard(3);
        DevelopmentCardZone cardZone1 = new DevelopmentCardZone();
        requirements1.add(requirement1);
        requirements1.add(requirement2);
        requirements2.add(requirement3);
        requirements2.add(requirement4);
        prod1.add(servant1);
        prod1.add(servant1);
        prod1.add(servant1);
        DevelopmentCard card1 = new DevelopmentCard(requirements1,stat1,requirements2,prod1,5);
        dashboard.getWarehouse().addResource(coin1);
        dashboard.getWarehouse().addResource(coin1);
        dashboard.getWarehouse().addResource(stone1);
        dashboard.getWarehouse().addResource(stone1);
        dashboard.getStrongbox().addResource(coin1);
        dashboard.getStrongbox().addResource(coin1);
        dashboard.getDevelopmentCardZones().add(cardZone1);
        dashboard.getDevelopmentCardZones().get(0).addNewCard(card1);
        assertEquals(true,dashboard.getDevelopmentCardZones().get(0).getLastCard().checkPrice(dashboard));
        assertEquals(false,dashboard.getDevelopmentCardZones().get(0).getLastCard().checkRequirements(dashboard));


        /**here we serialize the class coin and we print it
         *
         */
        System.out.println("coin:");
        Gson coinGson = new GsonBuilder().setPrettyPrinting().create();
        String coinJson = coinGson.toJson(coin1);
        System.out.println(coinJson);

        /**here we deserialize it into a CoinResource (called resourceRecreated)
         *
         */
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(DevelopmentCard.class, new InterfaceAdapter());
        Gson gson = builder.create();
        CoinResource resourceRecreated = gson.fromJson(coinJson, CoinResource.class);
        assertEquals("coin",resourceRecreated.getResourceType());

        /**here we serialize it again once more to check if it has been deserialized correctly
         *
         */
        System.out.println("coin recreated:");
        Gson coinRecreatedGson = new GsonBuilder().setPrettyPrinting().create();
        String coinRecreatedJson = coinRecreatedGson.toJson(resourceRecreated);
        System.out.println(coinRecreatedJson);
    }

    /**in this test, we take the different parts from a card from a list of cards and we print them with json
     *
     * @throws IOException
     */
    @Test
    public void fourthTest() throws IOException {
        CoinResource coin1=new CoinResource();
        StoneResource stone1=new StoneResource();
        ServantResource servant1=new ServantResource();
        ResourcesRequirementsForAcquisition requirement1 = new ResourcesRequirementsForAcquisition(4,coin1);
        ResourcesRequirementsForAcquisition requirement2 = new ResourcesRequirementsForAcquisition(2,stone1);
        ResourcesRequirements requirement3 = new ResourcesRequirements(3,coin1);
        ResourcesRequirements requirement4 = new ResourcesRequirements(5,stone1);
        ArrayList<ResourcesRequirementsForAcquisition> requirements1 = new ArrayList<ResourcesRequirementsForAcquisition>();
        ArrayList<ResourcesRequirements> requirements2 = new ArrayList<ResourcesRequirements>();
        Pair<Integer, Color> stat1 = new Pair<Integer, Color>(3,Color.Blue);
        ArrayList<Resource> prod1 = new ArrayList<Resource>();
        Dashboard dashboard = new Dashboard(3);
        DevelopmentCardZone cardZone1 = new DevelopmentCardZone();
        requirements1.add(requirement1);
        requirements1.add(requirement2);
        requirements2.add(requirement3);
        requirements2.add(requirement4);
        prod1.add(servant1);
        prod1.add(servant1);
        prod1.add(servant1);
        DevelopmentCard card1 = new DevelopmentCard(requirements1,stat1,requirements2,prod1,5);
        dashboard.getWarehouse().addResource(coin1);
        dashboard.getWarehouse().addResource(coin1);
        dashboard.getWarehouse().addResource(stone1);
        dashboard.getWarehouse().addResource(stone1);
        dashboard.getStrongbox().addResource(coin1);
        dashboard.getStrongbox().addResource(coin1);
        dashboard.getDevelopmentCardZones().add(cardZone1);
        dashboard.getDevelopmentCardZones().get(0).addNewCard(card1);
        dashboard.getDevelopmentCardZones().get(0).addNewCard(card1);
        dashboard.getDevelopmentCardZones().get(0).addNewCard(card1);


        assertEquals(true,dashboard.getDevelopmentCardZones().get(0).getLastCard().checkPrice(dashboard));
        assertEquals(false,dashboard.getDevelopmentCardZones().get(0).getLastCard().checkRequirements(dashboard));


        /**here we serialize a list of cards and write it
         *
         */
        System.out.println("list of cards:");
        Gson listOfCardsGson = new GsonBuilder().setPrettyPrinting().create();
        String listJson = listOfCardsGson.toJson(dashboard.getDevelopmentCardZones().get(0).getCards());
        System.out.println(listJson);

        /**here we take a single card from the list of cards we created
         *
         */
        System.out.println("single card:");
        JsonParser parser = new JsonParser();
        JsonArray cardArray = parser.parse(listJson).getAsJsonArray();
        Gson cardGson = new GsonBuilder().setPrettyPrinting().create();
        String singleCardJson = cardGson.toJson(cardArray.get(0).getAsJsonObject());
        System.out.println(singleCardJson);

        /**we now try to take only the CardPrice
         *
         */
        System.out.println("price of the card:");
        JsonParser parser1 = new JsonParser();
        JsonObject priceJsonObj = parser1.parse(singleCardJson).getAsJsonObject();
        Gson priceGson = new GsonBuilder().setPrettyPrinting().create();
        String priceJson = cardGson.toJson(priceJsonObj.get("cardPrice"));
        System.out.println(priceJson);

        /**now we take the cardRequirements:
         *
         */
        System.out.println("requirements of the card:");
        JsonParser parser2 = new JsonParser();
        JsonObject requirementsJsonObj = parser2.parse(singleCardJson).getAsJsonObject();
        Gson requirementsGson = new GsonBuilder().setPrettyPrinting().create();
        String requirementsJson = cardGson.toJson(requirementsJsonObj.get("prodRequirements"));
        System.out.println(requirementsJson);

        /**here we get the cardStats
         *
         */
        System.out.println("stats of the card:");
        JsonParser parser3 = new JsonParser();
        JsonObject statsJsonObj = parser3.parse(singleCardJson).getAsJsonObject();
        Gson statsGson = new GsonBuilder().setPrettyPrinting().create();
        String statsJson = cardGson.toJson(requirementsJsonObj.get("cardStats"));
        System.out.println(statsJson);

        /**we get the first element of the array of requirements of the price
         *
         */
        System.out.println("first requirement of the price:");
        JsonParser parser4 = new JsonParser();
        JsonArray requirementsArray = parser4.parse(priceJson).getAsJsonArray();
        Gson requirementGson = new GsonBuilder().setPrettyPrinting().create();
        String requirementJson = cardGson.toJson(requirementsArray.get(0).getAsJsonObject());
        System.out.println(requirementJson);

    }


    /**we now create a card in json that has all the info that we need
     *
     * @throws IOException
     */
    @Test
    public void fifthTest() throws IOException {

        List <Integer> amounts = new ArrayList<Integer>();
        amounts.add(3);
        amounts.add(4);
        List <String> resourcesType = new ArrayList<String>();
        resourcesType.add("stone");
        resourcesType.add("coin");
        DevelopmentCardForJson firstCard = new DevelopmentCardForJson(amounts,resourcesType,3,"blue",amounts,resourcesType,amounts,resourcesType,5);

        System.out.println("card created:");
        Gson cardGson = new GsonBuilder().setPrettyPrinting().create();
        String cardJson = cardGson.toJson(firstCard);
        System.out.println(cardJson);

        ArrayList <DevelopmentCardForJson> listOfCards = new ArrayList<DevelopmentCardForJson>();
        listOfCards.add(firstCard);
        listOfCards.add(firstCard);
        listOfCards.add(firstCard);

        System.out.println("list of cards:");
        Gson listOfCardsGson = new GsonBuilder().setPrettyPrinting().create();
        String listJson = listOfCardsGson.toJson(listOfCards);
        System.out.println(listJson);


    }

    /**we try to deserialize the card created in the previous test
     *
     * @throws IOException
     */
    @Test
    public void sixthTest() throws IOException {
        JsonReader reader = new JsonReader(new FileReader("C:\\Users\\Sam\\Desktop\\provajson.json"));
        JsonParser parser = new JsonParser();
        JsonArray cardsArray = parser.parse(reader).getAsJsonArray();
        for(JsonElement jsonElement : cardsArray){


            Gson gson = new Gson();
            DevelopmentCardForJson cardRecreated = gson.fromJson(jsonElement.getAsJsonObject(), DevelopmentCardForJson.class);

            System.out.println(cardRecreated.getVictoryPoints());

        }


    }

    /**we now serialize a LeaderCardForJson
     *
     * @throws IOException
     */
    @Test
    public void seventhTest() throws IOException {
        List <Integer> amounts = new ArrayList<Integer>();
        amounts.add(3);
        amounts.add(4);
        List <String> resourcesType = new ArrayList<String>();
        resourcesType.add("stone");
        resourcesType.add("coin");
        List <String> colorRequired = new ArrayList<String>();
        colorRequired.add("blue");
        colorRequired.add("yellow");


        LeaderCardForJson leaderCardForJson = new LeaderCardForJson("development",amounts,amounts,colorRequired,amounts,resourcesType,5, "discount","coin");

        System.out.println("card created:");
        Gson cardGson = new GsonBuilder().setPrettyPrinting().create();
        String cardJson = cardGson.toJson(leaderCardForJson);
        System.out.println(cardJson);

        ArrayList <LeaderCardForJson> listOfCards = new ArrayList<LeaderCardForJson>();
        listOfCards.add(leaderCardForJson);
        listOfCards.add(leaderCardForJson);
        listOfCards.add(leaderCardForJson);

        System.out.println("list of cards:");
        Gson listOfCardsGson = new GsonBuilder().setPrettyPrinting().create();
        String listJson = listOfCardsGson.toJson(listOfCards);
        System.out.println(listJson);
    }
    @Test
    public void eightTest() throws IOException {
        JsonReader reader = new JsonReader(new FileReader("C:\\Users\\Sam\\Desktop\\leadercards.json"));
        JsonParser parser = new JsonParser();
        JsonArray cardsArray = parser.parse(reader).getAsJsonArray();
        for (JsonElement jsonElement : cardsArray) {

            Gson gson = new Gson();
            LeaderCardForJson cardRecreated = gson.fromJson(jsonElement.getAsJsonObject(), LeaderCardForJson.class);

            System.out.println(cardRecreated);

        }
    }

    /**in this test we check that the leader card instantiation works properly
     *
     * @throws IOException
     */
    @Test
    public void ninthTest() throws IOException {
        LeaderCardDeck deck = new LeaderCardDeck();
        deck.deckInitializer();
        for (LeaderCard cardToPrint: deck.getDeck()){
            System.out.println(cardToPrint);
        }
    }
    @Test
    public void tenthTest() throws IOException {
        PapalPathModifier funz1 = new PapalPathModifier();
        funz1.importFavorCards();
        funz1.printFavorValues();
    }
    @Test
    public void eleventhTest() throws IOException {
        PapalPath papalPath = new PapalPath(1);
        System.out.println("the papal path is");
        System.out.println(papalPath);
    }

    @Test
    public void twelfthTest() throws IOException {
        PapalPathModifier funz1 = new PapalPathModifier();
        funz1.importFavorCards();
        funz1.setFavorCardsVictoryPoints(1,3);
        funz1.printFavorValues();
        funz1.writeFavorCardsInJson();
        PapalPath papalPath = new PapalPath(1);
        System.out.println("the papal path is");
        System.out.println(papalPath);
    }
}
