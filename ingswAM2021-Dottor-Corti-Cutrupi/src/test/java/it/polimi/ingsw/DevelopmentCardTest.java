package it.polimi.ingsw;

import it.polimi.ingsw.exception.NotEnoughResourcesToActivateProductionException;
import it.polimi.ingsw.exception.PapalCardActivatedException;
import it.polimi.ingsw.model.boardsAndPlayer.Dashboard;
import it.polimi.ingsw.model.developmentcard.Color;
import it.polimi.ingsw.model.developmentcard.DevelopmentCard;
import it.polimi.ingsw.model.developmentcard.DevelopmentCardZone;
import it.polimi.ingsw.model.requirements.ResourcesRequirementsForAcquisition;
import it.polimi.ingsw.model.requirements.ResourcesRequirements;
import it.polimi.ingsw.model.resource.*;
import it.polimi.ingsw.exception.warehouseErrors.WarehouseDepotsRegularityError;
import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class     DevelopmentCardTest {

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

    public DevelopmentCardTest() throws FileNotFoundException {
    }

    /**
     * in this test we check if a card gets created correctly, and its production of a faith resource works properly
     * @throws WarehouseDepotsRegularityError
     * @throws NotEnoughResourcesToActivateProductionException
     */
    @Test
    public void testingBasicFaithProduction() throws WarehouseDepotsRegularityError, NotEnoughResourcesToActivateProductionException, PapalCardActivatedException {
        ShieldResource shield = new ShieldResource();
        ResourcesRequirementsForAcquisition requirementTest1 = new ResourcesRequirementsForAcquisition(2,shield);
        ResourcesRequirements requirementTest2 = new ResourcesRequirements(1, coin);
        Pair<Integer,Color> statTest = new Pair<>(1,Color.Green);
        ArrayList<ResourcesRequirementsForAcquisition> arrayReq1 = new ArrayList<ResourcesRequirementsForAcquisition>();
        arrayReq1.add(requirementTest1);
        ArrayList<ResourcesRequirements> arrayReq2 = new ArrayList<ResourcesRequirements>();
        arrayReq2.add(requirementTest2);
        prod1.add(new FaithResource());
        DevelopmentCard card = new DevelopmentCard(arrayReq1,statTest,arrayReq2,prod1,1,false);
        assertEquals(3,dashboard.getWarehouse().sizeOfWarehouse());
        dashboard.getWarehouse().addResource(coin);
        assertEquals(3,dashboard.getWarehouse().sizeOfWarehouse());
        assertEquals(1,dashboard.getWarehouse().amountOfResource(coin));
        assertEquals(1,dashboard.getWarehouse().returnLengthOfDepot(3));
        assertEquals(1,dashboard.getPapalPath().getFaithPosition());
        card.produce(dashboard);
        assertEquals(3,dashboard.getWarehouse().sizeOfWarehouse());
        assertEquals(0,dashboard.getWarehouse().amountOfResource(coin));
        assertEquals(0,dashboard.getWarehouse().returnLengthOfDepot(3));
        assertEquals(2,dashboard.getPapalPath().getFaithPosition());
    }

    /**
     * in this method we check if the check requirements method of the requirements interface works properly
     */
    @Test
    public void testingCheckRequirements() {
        requirements1.add(requirement1);
        requirements1.add(requirement2);
        requirements2.add(requirement3);
        requirements2.add(requirement4);
        prod1.add(servant);
        prod1.add(servant);
        prod1.add(servant);
        DevelopmentCard card1 = new DevelopmentCard(requirements1,stat1,requirements2,prod1,5,false);
        dashboard.getWarehouse().addResource(coin);
        dashboard.getWarehouse().addResource(coin);
        dashboard.getWarehouse().addResource(stone);
        dashboard.getWarehouse().addResource(stone);
        dashboard.getStrongbox().addResource(coin);
        dashboard.getStrongbox().addResource(coin);
        dashboard.getDevelopmentCardZones().add(cardZone1);
        dashboard.getDevelopmentCardZones().get(0).addNewCard(card1);
        assertEquals(true,dashboard.getDevelopmentCardZones().get(0).getLastCard().checkPrice(dashboard));
        assertEquals(false,dashboard.getDevelopmentCardZones().get(0).getLastCard().checkRequirements(dashboard));
    }

    /**
     * in this test we check if the produce and buy methods work properly
     * @throws WarehouseDepotsRegularityError
     * @throws NotEnoughResourcesToActivateProductionException
     */
    @Test
    public void testingProduceAndBuy() throws WarehouseDepotsRegularityError, NotEnoughResourcesToActivateProductionException, PapalCardActivatedException {
        requirements1.add(requirement1);
        requirements1.add(requirement2);
        requirements2.add(requirement3);
        requirements2.add(requirement4);
        prod1.add(coin);
        prod1.add(coin);
        prod1.add(coin);
        DevelopmentCard card1 = new DevelopmentCard(requirements1,stat1,requirements2,prod1,5,false);
        dashboard.getWarehouse().addResource(coin);
        dashboard.getWarehouse().addResource(coin);
        dashboard.getWarehouse().addResource(coin);
        dashboard.getWarehouse().addResource(stone);
        dashboard.getWarehouse().addResource(stone);
        dashboard.getStrongbox().addResource(coin);
        dashboard.getStrongbox().addResource(coin);
        dashboard.getDevelopmentCardZones().add(cardZone1);
        dashboard.getDevelopmentCardZones().get(0).addNewCard(card1);
        dashboard.getDevelopmentCardZones().get(0).getLastCard().buyCard(dashboard);
        assertEquals(1,dashboard.availableResourcesForProduction(coin));
        /**at this point i should have 1 coin and 0 stones in the strongbox, and the warehouse should be empty
         *
         */
        dashboard.getWarehouse().addResource(coin);dashboard.getWarehouse().addResource(coin);dashboard.getWarehouse().addResource(coin);
        dashboard.getStrongbox().addResource(stone);dashboard.getStrongbox().addResource(stone);dashboard.getStrongbox().addResource(stone);
        dashboard.getStrongbox().addResource(stone);dashboard.getStrongbox().addResource(stone);
        dashboard.getDevelopmentCardZones().get(0).getLastCard().produce(dashboard);
        /** i check now that even if there are enough resources (coins) in the strongbox, i cant use them because
        *i just produced them
         * */
        assertEquals(false,dashboard.getDevelopmentCardZones().get(0).getLastCard().checkRequirements(dashboard));
    }



}
