package it.polimi.ingsw;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.developmentcard.Color;
import it.polimi.ingsw.developmentcard.DevelopmentCard;
import it.polimi.ingsw.developmentcard.DevelopmentCardZone;
import it.polimi.ingsw.requirements.ResourcesRequirements;
import it.polimi.ingsw.requirements.ResourcesRequirementsForAcquisition;
import it.polimi.ingsw.resource.CoinResource;
import it.polimi.ingsw.resource.Resource;
import it.polimi.ingsw.resource.ServantResource;
import it.polimi.ingsw.resource.StoneResource;
import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {

    public class Car {
        public String brand = null;
        public int    doors = 0;
    }

    @Test
    public void firstTest(){
        Car car = new Car();
        car.brand = "Rover";
        car.doors = 5;

        Gson gson = new Gson();
        String json = gson.toJson(car);
        System.out.println(json);
    }

    @Test
    public void secondTest(){
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
        assertEquals(true,dashboard.getDevelopmentCardZones().get(0).getOnTopCard().checkPrice(dashboard));
        assertEquals(false,dashboard.getDevelopmentCardZones().get(0).getOnTopCard().checkRequirements(dashboard));
        System.out.println("card:");
        Gson cardGson = new GsonBuilder().setPrettyPrinting().create();
        String cardjson = cardGson.toJson(card1);
        System.out.println(cardjson);
        System.out.println("coin:");
        Gson coinGson = new GsonBuilder().setPrettyPrinting().create();
        String coinJson = cardGson.toJson(coin1);
        System.out.println(coinJson);
    }

    @Test
    public void thirdTest(){
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
        assertEquals(true,dashboard.getDevelopmentCardZones().get(0).getOnTopCard().checkPrice(dashboard));
        assertEquals(false,dashboard.getDevelopmentCardZones().get(0).getOnTopCard().checkRequirements(dashboard));


        //here we serialize the class coin and we print it
        System.out.println("coin:");
        Gson coinGson = new GsonBuilder().setPrettyPrinting().create();
        String coinJson = coinGson.toJson(coin1);
        System.out.println(coinJson);

        //here we deserialize it into a CoinResource (called resourceRecreated)
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(DevelopmentCard.class, new InterfaceAdapter());
        Gson gson = builder.create();
        CoinResource resourceRecreated = gson.fromJson(coinJson, CoinResource.class);
        assertEquals("coin",resourceRecreated.getResourceType());

        //here we serialize it again once more to check if it has been deserialized correctly
        System.out.println("coin recreated:");
        Gson coinRecreatedGson = new GsonBuilder().setPrettyPrinting().create();
        String coinRecreatedJson = coinRecreatedGson.toJson(resourceRecreated);
        System.out.println(coinRecreatedJson);


    }


}
