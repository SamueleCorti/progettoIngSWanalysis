package it.polimi.ingsw;

import it.polimi.ingsw.exception.PapalCardActivatedException;
import it.polimi.ingsw.model.boardsAndPlayer.Dashboard;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.leadercard.leaderpowers.ExtraDeposit;
import it.polimi.ingsw.model.leadercard.leaderpowers.PowerType;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.papalpath.CardCondition;
import it.polimi.ingsw.model.requirements.Requirements;
import it.polimi.ingsw.model.requirements.ResourcesRequirementsForAcquisition;
import it.polimi.ingsw.model.resource.*;
import it.polimi.ingsw.exception.warehouseErrors.WarehouseDepotsRegularityError;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExtraDepositTest {

    /**
     * Testing that the creation of an extra depot due to the activation of a card works
     */
    @Test
    public void testingCreation(){
        StoneResource stone = new StoneResource();
        ResourcesRequirementsForAcquisition requirement1 = new ResourcesRequirementsForAcquisition(5,stone);
        ArrayList<Requirements> requirements= new ArrayList<Requirements>();
        requirements.add(requirement1);
        ServantResource servant = new ServantResource();
        ArrayList<Resource> resources= new ArrayList<Resource>();
        resources.add(servant);
        ExtraDeposit extraDeposit = new ExtraDeposit(resources);
        LeaderCard leaderCard = new LeaderCard(requirements,3,extraDeposit,false);

        assertEquals(ResourceType.Servant,extraDeposit.getResourceType());
        assertEquals(PowerType.ExtraDeposit,leaderCard.getLeaderPower().returnPowerType());
        assertEquals(CardCondition.Inactive,leaderCard.getCondition());
        assertEquals(requirements,leaderCard.getCardRequirements());
    }

    /**
     * Testing that adding a resource to the xtra depot works
     * @throws WarehouseDepotsRegularityError
     * @throws PapalCardActivatedException
     */
    @Test
    public void testAdding1ResourceToExtraDepot() throws WarehouseDepotsRegularityError, PapalCardActivatedException {
        StoneResource stone = new StoneResource();
        ResourcesRequirementsForAcquisition requirement1 = new ResourcesRequirementsForAcquisition(5,stone);
        ArrayList<Requirements> requirements= new ArrayList<>();
        requirements.add(requirement1);
        ServantResource servant = new ServantResource();
        ArrayList<Resource> resources= new ArrayList<>();
        resources.add(servant);
        resources.add(servant);
        ExtraDeposit extraDeposit = new ExtraDeposit(resources);
        LeaderCard leaderCard = new LeaderCard(requirements,3,extraDeposit,false);
        CoinResource coin = new CoinResource();
        ShieldResource shield = new ShieldResource();
        FaithResource faith = new FaithResource();
        BlankResource blank = new BlankResource();
        Market market = new Market(faith,stone,servant,servant,coin,blank,blank,blank,coin,shield,shield,stone,blank);
        Dashboard dashboard = new Dashboard(1);

        //new leader card is correctly added to leader card zone
        dashboard.getLeaderCardZone().addNewCard(leaderCard);
        assertEquals(dashboard.getLeaderCardZone().getLeaderCards().get(0),leaderCard);

        leaderCard.activateCardPower(dashboard);

        //leader card is correctly set as Active and his depot has been created
        dashboard.getLeaderCardZone().getLeaderCards().get(0).setCondition(CardCondition.Active);
        assertEquals(dashboard.getLeaderCardZone().getLeaderCards().get(0).getCondition(),CardCondition.Active);
        assertEquals(dashboard.getExtraDepots().get(0).getExtraDepotType(),ResourceType.Servant);
        assertEquals(2,dashboard.getExtraDepots().get(0).getSize());
        assertEquals(0,dashboard.getExtraDepots().get(0).getAmountOfContainedResources());
        assertTrue(dashboard.getExtraDepots().get(0).getAllResources().isEmpty());

        //I should get 1 servant (put in the extradepot) and 1 stone
        market.acquireResourcesFromMarket(false,3,dashboard);
        assertEquals(0,dashboard.getPapalPath().getFaithPosition());
        assertEquals(dashboard.getWarehouse().sizeOfWarehouse(),3);
        assertEquals(null,dashboard.getWarehouse().returnTypeofDepot(1));
        assertEquals(null,dashboard.getWarehouse().returnTypeofDepot(2));
        assertEquals(ResourceType.Stone,dashboard.getWarehouse().returnTypeofDepot(3));
        assertEquals(0,dashboard.getWarehouse().returnLengthOfDepot(1));
        assertEquals(0,dashboard.getWarehouse().returnLengthOfDepot(2));
        assertEquals(1,dashboard.getWarehouse().returnLengthOfDepot(3));
        assertEquals(1,dashboard.getExtraDepots().get(0).getAmountOfContainedResources());
        assertEquals(ResourceType.Servant,dashboard.getExtraDepots().get(0).getExtraDepotType());
    }

    /**
     * Testing that once you get resources from market and you have an extra depot, everything works
     * @throws WarehouseDepotsRegularityError
     * @throws PapalCardActivatedException
     */
    @Test
    public void testingNormalAddInteractionWith1ExtraDepositCard() throws WarehouseDepotsRegularityError, PapalCardActivatedException {
        StoneResource stone = new StoneResource();
        ResourcesRequirementsForAcquisition requirement1 = new ResourcesRequirementsForAcquisition(5,stone);
        ArrayList<Requirements> requirements= new ArrayList<Requirements>();
        requirements.add(requirement1);
        ServantResource servant = new ServantResource();
        ArrayList<Resource> resources= new ArrayList<>();
        resources.add(servant);
        ExtraDeposit extraDeposit = new ExtraDeposit(resources);
        LeaderCard leaderCard = new LeaderCard(requirements,3,extraDeposit,false);

        CoinResource coin = new CoinResource();
        ShieldResource shield = new ShieldResource();
        FaithResource faith = new FaithResource();
        BlankResource blank = new BlankResource();
        Market market = new Market(faith,stone,servant,servant,coin,blank,blank,blank,coin,shield,shield,stone,blank);
        Dashboard dashboard = new Dashboard(1);

        leaderCard.activateCardPower(dashboard);

        //new leader card is correctly added to leader card zone
        dashboard.getLeaderCardZone().addNewCard(leaderCard);
        assertEquals(dashboard.getLeaderCardZone().getLeaderCards().get(0),leaderCard);

        //leader card is correctly set as Active and his depot has been created
        dashboard.getLeaderCardZone().getLeaderCards().get(0).setCondition(CardCondition.Active);
        assertEquals(dashboard.getLeaderCardZone().getLeaderCards().get(0).getCondition(),CardCondition.Active);
        assertEquals(dashboard.getExtraDepots().get(0).getExtraDepotType(),ResourceType.Servant);

        //I receive 1 stone, 2 servans and 1 faith, checking that both servans are on extradepot and stone is in warehouse
        market.acquireResourcesFromMarket(true,0,dashboard);
        //assertEquals(1,dashboard.getPapalPath().getFaithPosition());
        assertEquals(dashboard.getWarehouse().sizeOfWarehouse(),3);
        assertEquals(null,dashboard.getWarehouse().returnTypeofDepot(1));
        assertEquals(null,dashboard.getWarehouse().returnTypeofDepot(2));
        assertEquals(ResourceType.Stone,dashboard.getWarehouse().returnTypeofDepot(3));
        assertEquals(0,dashboard.getWarehouse().returnLengthOfDepot(1));
        assertEquals(0,dashboard.getWarehouse().returnLengthOfDepot(2));
        assertEquals(1,dashboard.getWarehouse().returnLengthOfDepot(3));
        assertEquals(2,dashboard.getExtraDepots().get(0).getAmountOfContainedResources());
        assertEquals(ResourceType.Servant,dashboard.getExtraDepots().get(0).getExtraDepotType());

        //I receive 1 servant, 1 faith and 1 stone. Checking that now servant is added in warehouse (because extradepot is full)
        market.acquireResourcesFromMarket(true,0,dashboard);
        //assertEquals(2,dashboard.getPapalPath().getFaithPosition());
        assertEquals(dashboard.getWarehouse().sizeOfWarehouse(),3);
        assertEquals(null,dashboard.getWarehouse().returnTypeofDepot(1));
        assertEquals(ResourceType.Servant,dashboard.getWarehouse().returnTypeofDepot(2));
        assertEquals(ResourceType.Stone,dashboard.getWarehouse().returnTypeofDepot(3));
        assertEquals(0,dashboard.getWarehouse().returnLengthOfDepot(1));
        //assertEquals(1,dashboard.getWarehouse().returnLengthOfDepot(2));
        assertEquals(2,dashboard.getWarehouse().returnLengthOfDepot(3));
        assertEquals(2,dashboard.getExtraDepots().get(0).getAmountOfContainedResources());
        assertEquals(ResourceType.Servant,dashboard.getExtraDepots().get(0).getExtraDepotType());
    }

    /**
     * Testing that getting resources from market works even when there are 2 extra depots activated
     * @throws WarehouseDepotsRegularityError
     * @throws PapalCardActivatedException
     */
    @Test
    public void testingNormalAddInteractionWith2ExtraDepositLeaderCards() throws WarehouseDepotsRegularityError,PapalCardActivatedException {
        StoneResource stone = new StoneResource();
        ResourcesRequirementsForAcquisition requirement1 = new ResourcesRequirementsForAcquisition(5,stone);
        ArrayList<Requirements> requirements= new ArrayList<Requirements>();
        requirements.add(requirement1);
        ServantResource servant = new ServantResource();
        ArrayList<Resource> resources= new ArrayList<>();
        resources.add(servant);
        ExtraDeposit extraDeposit = new ExtraDeposit(resources);
        LeaderCard leaderCard = new LeaderCard(requirements,3,extraDeposit,false);

        CoinResource coin = new CoinResource();
        ShieldResource shield = new ShieldResource();
        FaithResource faith = new FaithResource();
        BlankResource blank = new BlankResource();
        Market market = new Market(faith,stone,servant,servant,coin,blank,blank,blank,coin,shield,shield,stone,blank);
        Dashboard dashboard = new Dashboard(1);
        ResourcesRequirementsForAcquisition requirement2 = new ResourcesRequirementsForAcquisition(5,shield);
        ArrayList<Requirements> requirements2= new ArrayList<Requirements>();
        requirements2.add(requirement2);
        ArrayList<Resource> resources2= new ArrayList<>();
        resources2.add(coin);
        ExtraDeposit extraDeposit2 = new ExtraDeposit(resources2);
        LeaderCard leaderCard2 = new LeaderCard(requirements2,3,extraDeposit2,false);

        //new leader cards are correctly added to leader card zone
        dashboard.getLeaderCardZone().addNewCard(leaderCard);
        dashboard.getLeaderCardZone().addNewCard(leaderCard2);
        assertEquals(dashboard.getLeaderCardZone().getLeaderCards().get(0),leaderCard);
        assertEquals(dashboard.getLeaderCardZone().getLeaderCards().get(1),leaderCard2);


        leaderCard.activateCardPower(dashboard);

        leaderCard2.activateCardPower(dashboard);

        //leader cards are correctly set as Active and his depot has been created
        dashboard.getLeaderCardZone().getLeaderCards().get(0).setCondition(CardCondition.Active);
        dashboard.getLeaderCardZone().getLeaderCards().get(1).setCondition(CardCondition.Active);
        assertEquals(dashboard.getLeaderCardZone().getLeaderCards().get(0).getCondition(),CardCondition.Active);
        assertEquals(dashboard.getExtraDepots().get(0).getExtraDepotType(),ResourceType.Servant);
        assertEquals(dashboard.getLeaderCardZone().getLeaderCards().get(1).getCondition(),CardCondition.Active);
        assertEquals(dashboard.getExtraDepots().get(1).getExtraDepotType(),ResourceType.Coin);
        //I should get 3 servans (2 of them in extradepot), 3 coins(2 of them in extradepot) and 1 stone
        market.acquireResourcesFromMarket(true,0,dashboard);
        market.acquireResourcesFromMarket(false,0,dashboard);
        market.acquireResourcesFromMarket(false,0,dashboard);
        //assertEquals(1,dashboard.getPapalPath().getFaithPosition());
        assertEquals(dashboard.getWarehouse().sizeOfWarehouse(),3);
        assertEquals(ResourceType.Coin,dashboard.getWarehouse().returnTypeofDepot(2));
        //assertEquals(ResourceType.Servant,dashboard.getWarehouse().returnTypeofDepot(1));
        assertEquals(ResourceType.Stone,dashboard.getWarehouse().returnTypeofDepot(3));
        assertEquals(0,dashboard.getWarehouse().returnLengthOfDepot(1));
        assertEquals(2,dashboard.getWarehouse().returnLengthOfDepot(2));
        assertEquals(2,dashboard.getWarehouse().returnLengthOfDepot(3));
        assertEquals(2,dashboard.getExtraDepots().get(0).getAmountOfContainedResources());
        assertEquals(ResourceType.Servant,dashboard.getExtraDepots().get(0).getExtraDepotType());
        assertEquals(2,dashboard.getExtraDepots().get(1).getAmountOfContainedResources());
        assertEquals(ResourceType.Coin,dashboard.getExtraDepots().get(1).getExtraDepotType());
    }

    /**
     * testing removal of resources from an extra depot
     * @throws WarehouseDepotsRegularityError
     * @throws PapalCardActivatedException
     */
    @Test
    public void testRemovingFromAnExtraDepot() throws WarehouseDepotsRegularityError, PapalCardActivatedException {
        StoneResource stone = new StoneResource();
        ResourcesRequirementsForAcquisition requirement1 = new ResourcesRequirementsForAcquisition(5,stone);
        ArrayList<Requirements> requirements= new ArrayList<Requirements>();
        requirements.add(requirement1);
        ServantResource servant = new ServantResource();
        ArrayList<Resource> resources= new ArrayList<>();
        resources.add(servant);
        ExtraDeposit extraDeposit = new ExtraDeposit(resources);
        LeaderCard leaderCard = new LeaderCard(requirements,3,extraDeposit,false);
        CoinResource coin = new CoinResource();
        ShieldResource shield = new ShieldResource();
        FaithResource faith = new FaithResource();
        BlankResource blank = new BlankResource();
        Market market = new Market(faith,stone,servant,servant,coin,blank,blank,blank,coin,shield,shield,stone,blank);
        Dashboard dashboard = new Dashboard(1);
        dashboard.getLeaderCardZone().addNewCard(leaderCard);
        dashboard.getLeaderCardZone().getLeaderCards().get(0).activateCardPower(dashboard);
        //removing one resource from extradepot and then the other
        market.acquireResourcesFromMarket(true,0,dashboard);
        dashboard.getExtraDepots().get(0).removeResource();
        assertEquals(1,dashboard.getExtraDepots().get(0).getAmountOfContainedResources());
        dashboard.getExtraDepots().get(0).removeResource();
        assertEquals(0,dashboard.getExtraDepots().get(0).getAmountOfContainedResources());
        dashboard.getExtraDepots().get(0).removeResource();
        assertEquals(0,dashboard.getExtraDepots().get(0).getAmountOfContainedResources());
    }

    /**
     * Testing that the method that adds resources to the strongbox based on the type of the depot works
     */
    @Test
    public void testingAddResourceWithoutParameters(){
        StoneResource stone = new StoneResource();
        ResourcesRequirementsForAcquisition requirement1 = new ResourcesRequirementsForAcquisition(5,stone);
        ArrayList<Requirements> requirements= new ArrayList<Requirements>();
        requirements.add(requirement1);
        ServantResource servant = new ServantResource();
        ArrayList<Resource> resources= new ArrayList<>();
        resources.add(servant);
        ExtraDeposit extraDeposit = new ExtraDeposit(resources);
        LeaderCard leaderCard = new LeaderCard(requirements,3,extraDeposit,false);
        assertEquals(leaderCard.getLeaderPower().returnPowerType(), PowerType.ExtraDeposit);
        Dashboard dashboard = new Dashboard(1);
        dashboard.getLeaderCardZone().addNewCard(leaderCard);
        dashboard.getLeaderCardZone().getLeaderCards().get(0).activateCardPower(dashboard);

        assertEquals(0,dashboard.getExtraDepots().get(0).getAmountOfContainedResources());
        dashboard.getExtraDepots().get(0).addResource();
        assertEquals(1,dashboard.getExtraDepots().get(0).getAmountOfContainedResources());
    }

    @Test
    public void activateWhileAlredyHavingThatResourceTest(){
        StoneResource stone = new StoneResource();
        Dashboard dashboard= new Dashboard(1);
        ArrayList <Resource> resources=new ArrayList<>();
        resources.add(stone);   resources.add(stone);
        dashboard.addResourceToWarehouse(stone);
        dashboard.addResourceToWarehouse(stone);
        ExtraDeposit extraDeposit= new ExtraDeposit(resources);
        extraDeposit.activateLeaderPower(dashboard);
        assertEquals(0, dashboard.getWarehouse().amountOfResource(stone));
        assertEquals(2, dashboard.getExtraDepots().get(0).getAllResources().size());
        assertEquals(extraDeposit.getType(), PowerType.ExtraDeposit);
        assertEquals(extraDeposit.returnRelatedResourcesCopy(), resources);
        assertEquals(extraDeposit.toString(),"allows you to store up to "+ resources.size() + " extra "+resources.get(0).getResourceType()+" resources in your warehouse");
    }
}
