package it.polimi.ingsw;

import it.polimi.ingsw.developmentcard.Color;
import it.polimi.ingsw.developmentcard.DevelopmentCard;
import it.polimi.ingsw.developmentcard.DevelopmentCardZone;
import it.polimi.ingsw.papalpath.CardCondition;
import it.polimi.ingsw.requirements.DevelopmentRequirements;
import it.polimi.ingsw.requirements.Requirements;
import it.polimi.ingsw.requirements.ResourcesRequirements;
import it.polimi.ingsw.resource.CoinResource;
import it.polimi.ingsw.resource.Resource;
import it.polimi.ingsw.resource.ServantResource;
import it.polimi.ingsw.resource.StoneResource;
import it.polimi.ingsw.storing.RegularityError;
import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DevelopmentCardTest {

    CoinResource coin1=new CoinResource();
    StoneResource stone1=new StoneResource();
    ServantResource servant1=new ServantResource();
    ResourcesRequirements requirement1 = new ResourcesRequirements(4,coin1);
    ResourcesRequirements requirement2 = new ResourcesRequirements(2,stone1);
    ResourcesRequirements requirement3 = new ResourcesRequirements(3,coin1);
    ResourcesRequirements requirement4 = new ResourcesRequirements(5,stone1);
    ArrayList<ResourcesRequirements> requirements1 = new ArrayList<ResourcesRequirements>();
    ArrayList<ResourcesRequirements> requirements2 = new ArrayList<ResourcesRequirements>();
    Pair <Integer, Color> stat1 = new Pair<Integer, Color>(3,Color.Blue);
    ArrayList<Resource> prod1 = new ArrayList<Resource>();
    Dashboard dashboard = new Dashboard(3);
    DevelopmentCardZone cardZone1 = new DevelopmentCardZone();
    @Test
    public void testingCheckRequirements() {
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
    }
    @Test
    public void testingProduceAndBuy() throws RegularityError {
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
        dashboard.getWarehouse().addResource(coin1);
        dashboard.getWarehouse().addResource(stone1);
        dashboard.getWarehouse().addResource(stone1);
        dashboard.getStrongbox().addResource(coin1);
        dashboard.getStrongbox().addResource(coin1);
        dashboard.getDevelopmentCardZones().add(cardZone1);
        dashboard.getDevelopmentCardZones().get(0).addNewCard(card1);
        dashboard.getDevelopmentCardZones().get(0).getOnTopCard().buyCard(dashboard);
        assertEquals(1,dashboard.availableResourcesForProduction(coin1));
    }
}
