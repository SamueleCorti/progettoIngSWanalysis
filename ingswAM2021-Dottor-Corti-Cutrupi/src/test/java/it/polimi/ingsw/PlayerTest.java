package it.polimi.ingsw;

import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.exception.warehouseErrors.TooManyResourcesInADepot;
import it.polimi.ingsw.model.boardsAndPlayer.Dashboard;
import it.polimi.ingsw.model.boardsAndPlayer.GameBoard;
import it.polimi.ingsw.model.boardsAndPlayer.Player;
import it.polimi.ingsw.model.developmentcard.Color;
import it.polimi.ingsw.model.developmentcard.DevelopmentCard;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.leadercard.leaderpowers.ExtraDeposit;
import it.polimi.ingsw.model.leadercard.leaderpowers.ExtraProd;
import it.polimi.ingsw.model.leadercard.leaderpowers.PowerType;
import it.polimi.ingsw.model.leadercard.leaderpowers.WhiteToColor;
import it.polimi.ingsw.model.requirements.DevelopmentRequirements;
import it.polimi.ingsw.model.requirements.Requirements;
import it.polimi.ingsw.model.requirements.ResourcesRequirements;
import it.polimi.ingsw.model.requirements.ResourcesRequirementsForAcquisition;
import it.polimi.ingsw.model.resource.*;
import it.polimi.ingsw.exception.warehouseErrors.WarehouseDepotsRegularityError;
import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    GameBoard gameBoard= new GameBoard(new ArrayList<>());
    Player player= new Player("Lopez",1,gameBoard);

    /**
     * Testing that the creation of player works
     */
    @Test
    public void TestingCreation(){
        GameBoard gameBoard = new GameBoard("Paolo");
        Player player = new Player("Paolo",gameBoard);
        assertEquals(1,player.getOrder());
        assertEquals("Paolo",player.getNickname());
        assertEquals(2,player.baseProductionRequirements());
        assertEquals(1,player.baseProductionResults());
        assertTrue(player.resourcesProduced().isEmpty());
        assertTrue(player.resourcesUsableForProd().isEmpty());
        assertEquals(0,player.numOfLeaderCards());
        assertTrue(player.noPapalCardActivated());
        assertEquals(0,player.numberOfActivatedPapalCards());
        assertEquals(8,player.nextPapalCardToActivateInfo());
        assertTrue(player.activatedWhiteToColor().isEmpty());
        assertEquals(0,player.checkPositionOfGivenPapalCard(1));
        assertEquals(3,player.sizeOfWarehouse());
        assertEquals(0,player.numberOfExtraDepots());
        assertEquals(0,player.lengthOfDepotGivenItsIndex(1));
        assertTrue(player.isLastCardOfTheSelectedDevZoneNull(1));
        assertFalse(player.checkGameIsEnded());
        assertEquals(null,player.typeOfDepotGivenItsIndex(1));
        assertEquals(0,player.getFaithPosition());
    }

    /**
     * Testing that the acquisition of a card works
     * @throws NotEnoughResourcesException
     * @throws NotCoherentLevelException
     */
    @Test
    public void cardLvl1AcquisitionTest() throws NotEnoughResourcesException, NotCoherentLevelException{
        gameBoard.decksInitializer();
        Exception exception1 = assertThrows(NotEnoughResourcesException.class, () -> {
            player.buyDevelopmentCard(Color.Green,1, 1,gameBoard);
        });
        for(int i=0;i<3;i++){
            player.getDashboardCopy().getStrongbox().addResource(new CoinResource());
            player.getDashboardCopy().getStrongbox().addResource(new ServantResource());
            player.getDashboardCopy().getStrongbox().addResource(new StoneResource());
            player.getDashboardCopy().getStrongbox().addResource(new ShieldResource());
        }
        assertEquals(3,player.availableResourceOfType(ResourceType.Coin));
        assertEquals(0,player.producedThisTurn(ResourceType.Coin));
        assertTrue(player.getProducedResources().isEmpty());
        assertTrue(player.getDevelopmentCardsInADevCardZone(1).isEmpty());
        for(int i=0;i<4;i++){
            assertEquals(3,player.availableResourcesForDevelopment()[i]);
        }
        player.buyDevelopmentCard(Color.Green,1, 1,gameBoard);
        System.out.println(player.getDashboardCopy().getDevelopmentCardZones().get(1).getFirstCard());
        System.out.println("Coin: "+ player.getDashboardCopy().getStrongbox().amountOfResource(new CoinResource()));
        System.out.println("Stone: "+ player.getDashboardCopy().getStrongbox().amountOfResource(new StoneResource()));
        System.out.println("Shield: "+ player.getDashboardCopy().getStrongbox().amountOfResource(new ShieldResource()));
        System.out.println("Servant: "+ player.getDashboardCopy().getStrongbox().amountOfResource(new ServantResource()));

        player.buyDevelopmentCardFake(Color.Blue,1,2);
        Exception exception = assertThrows(NotCoherentLevelException.class, () -> {
            player.buyDevelopmentCardFake(Color.Blue,3,1);
        });

    }

    /**
     * Testing that buying a lvl2 card when allowed works
     * @throws NotEnoughResourcesException
     * @throws NotCoherentLevelException
     */
    @Test
    public void cardLvl2AcquisitionTest() throws NotEnoughResourcesException, NotCoherentLevelException {
        gameBoard.decksInitializer();
        for(int i=0;i<7;i++){
            player.getDashboardCopy().getStrongbox().addResource(new CoinResource());
            player.getDashboardCopy().getStrongbox().addResource(new ServantResource());
            player.getDashboardCopy().getStrongbox().addResource(new StoneResource());
            player.getDashboardCopy().getStrongbox().addResource(new ShieldResource());
        }
        //testing that the cards are put in the correct zone
        player.buyDevelopmentCard(Color.Green,1, 1,gameBoard);
        System.out.println(player.getDashboardCopy().getDevelopmentCardZones().get(1).getLastCard());
        player.buyDevelopmentCard(Color.Blue,2, 1,gameBoard);
        System.out.println(player.getDashboardCopy().getDevelopmentCardZones().get(1).getLastCard());
        System.out.println("Coin: "+ player.getDashboardCopy().getStrongbox().amountOfResource(new CoinResource()));
        System.out.println("Stone: "+ player.getDashboardCopy().getStrongbox().amountOfResource(new StoneResource()));
        System.out.println("Shield: "+ player.getDashboardCopy().getStrongbox().amountOfResource(new ShieldResource()));
        System.out.println("Servant: "+ player.getDashboardCopy().getStrongbox().amountOfResource(new ServantResource()));
        for (DevelopmentCard devCard:player.getDashboardCopy().getDevelopmentCardZones().get(1).getCards()) {
            System.out.println(devCard);
        }
    }

    /**
     * Testing that buying a lvl2 card when not allowed works
     * @throws NotEnoughResourcesException
     * @throws NotCoherentLevelException
     */
    @Test
    public void cardLvl2ErrorAcquisitionTest() throws NotCoherentLevelException, NotEnoughResourcesException {
        gameBoard.decksInitializer();
        for(int i=0;i<7;i++){
            player.getDashboardCopy().getStrongbox().addResource(new CoinResource());
            player.getDashboardCopy().getStrongbox().addResource(new ServantResource());
            player.getDashboardCopy().getStrongbox().addResource(new StoneResource());
            player.getDashboardCopy().getStrongbox().addResource(new ShieldResource());
        }
        //testing that the cards are put in the correct zone
        player.buyDevelopmentCard(Color.Green,1, 1,gameBoard);
        System.out.println(player.getDashboardCopy().getDevelopmentCardZones().get(1).getLastCard());

        Exception exception = assertThrows(NotCoherentLevelException.class, () -> {
            player.buyDevelopmentCard(Color.Blue,2, 2,gameBoard);
        });

        System.out.println(player.getDashboardCopy().getDevelopmentCardZones().get(1).getLastCard());
        System.out.println(player.getDashboardCopy().getDevelopmentCardZones().get(2).getLastCard());
        System.out.println("Coin: "+ player.getDashboardCopy().getStrongbox().amountOfResource(new CoinResource()));
        System.out.println("Stone: "+ player.getDashboardCopy().getStrongbox().amountOfResource(new StoneResource()));
        System.out.println("Shield: "+ player.getDashboardCopy().getStrongbox().amountOfResource(new ShieldResource()));
        System.out.println("Servant: "+ player.getDashboardCopy().getStrongbox().amountOfResource(new ServantResource()));
        for (DevelopmentCard devCard:player.getDashboardCopy().getDevelopmentCardZones().get(1).getCards()) {
            System.out.println(devCard);
        }
    }

    /**
     * Testing that buying a lvl3 card when allowed works
     * @throws NotEnoughResourcesException
     * @throws NotCoherentLevelException
     */
    @Test
    public void cardLvl3AcquisitionTest() throws NotEnoughResourcesException, NotCoherentLevelException{
        gameBoard.decksInitializer();
        for(int i=0;i<15;i++){
            player.getDashboardCopy().getStrongbox().addResource(new CoinResource());
            player.getDashboardCopy().getStrongbox().addResource(new ServantResource());
            player.getDashboardCopy().getStrongbox().addResource(new StoneResource());
            player.getDashboardCopy().getStrongbox().addResource(new ShieldResource());
        }
        //testing that the cards are put in the correct zone
        player.buyDevelopmentCard(Color.Green,1, 1,gameBoard);
        System.out.println(player.getDashboardCopy().getDevelopmentCardZones().get(1).getLastCard());
        player.buyDevelopmentCard(Color.Blue,2, 1,gameBoard);
        System.out.println(player.getDashboardCopy().getDevelopmentCardZones().get(1).getLastCard());
        player.buyDevelopmentCard(Color.Yellow,3, 1,gameBoard);
        System.out.println(player.getDashboardCopy().getDevelopmentCardZones().get(1).getLastCard());
        System.out.println("Coin: "+ player.getDashboardCopy().getStrongbox().amountOfResource(new CoinResource()));
        System.out.println("Stone: "+ player.getDashboardCopy().getStrongbox().amountOfResource(new StoneResource()));
        System.out.println("Shield: "+ player.getDashboardCopy().getStrongbox().amountOfResource(new ShieldResource()));
        System.out.println("Servant: "+ player.getDashboardCopy().getStrongbox().amountOfResource(new ServantResource()));
        for (DevelopmentCard devCard:player.getDashboardCopy().getDevelopmentCardZones().get(1).getCards()) {
            System.out.println(devCard);
        }
    }

    /**
     * Testing that buying a lvl3 card when not allowed works
     * @throws NotEnoughResourcesException
     * @throws NotCoherentLevelException
     */
    @Test
    public void cardLvl3ErrorAcquisitionTest(){
        gameBoard.decksInitializer();
        for(int i=0;i<15;i++){
            player.getDashboardCopy().getStrongbox().addResource(new CoinResource());
            player.getDashboardCopy().getStrongbox().addResource(new ServantResource());
            player.getDashboardCopy().getStrongbox().addResource(new StoneResource());
            player.getDashboardCopy().getStrongbox().addResource(new ShieldResource());
        }
        //testing that the cards are put in the correct zone
        try {
            player.buyDevelopmentCard(Color.Green,1, 1,gameBoard);
            System.out.println(player.getDashboardCopy().getDevelopmentCardZones().get(1).getLastCard());
            player.buyDevelopmentCard(Color.Blue,3, 1,gameBoard);
            System.out.println(player.getDashboardCopy().getDevelopmentCardZones().get(1).getLastCard());
        } catch (NotCoherentLevelException e) {
            System.out.println("WRONG DEVELOPMENT CARD ZONE");
        } catch (NotEnoughResourcesException e) {
            System.out.println("WRONG DEVELOPMENT CARD ZONE");
        }
        System.out.println("Coin: "+ player.getDashboardCopy().getStrongbox().amountOfResource(new CoinResource()));
        System.out.println("Stone: "+ player.getDashboardCopy().getStrongbox().amountOfResource(new StoneResource()));
        System.out.println("Shield: "+ player.getDashboardCopy().getStrongbox().amountOfResource(new ShieldResource()));
        System.out.println("Servant: "+ player.getDashboardCopy().getStrongbox().amountOfResource(new ServantResource()));
        for (DevelopmentCard devCard:player.getDashboardCopy().getDevelopmentCardZones().get(1).getCards()) {
            System.out.println(devCard);
        }
    }


    @Test
    public void cardLvl2DuplicateErrorAcquisitionTest() throws NotCoherentLevelException, NotEnoughResourcesException {
        gameBoard.decksInitializer();
        for(int i=0;i<15;i++){
            player.getDashboardCopy().getStrongbox().addResource(new CoinResource());
            player.getDashboardCopy().getStrongbox().addResource(new ServantResource());
            player.getDashboardCopy().getStrongbox().addResource(new StoneResource());
            player.getDashboardCopy().getStrongbox().addResource(new ShieldResource());
        }
        //testing that the cards are put in the correct zone
        player.buyDevelopmentCard(Color.Green,1, 1,gameBoard);
        System.out.println(player.getDashboardCopy().getDevelopmentCardZones().get(1).getLastCard());
        player.buyDevelopmentCard(Color.Blue,2, 1,gameBoard);
        System.out.println(player.getDashboardCopy().getDevelopmentCardZones().get(1).getLastCard());

        Exception exception = assertThrows(NotCoherentLevelException.class, () -> {
            player.buyDevelopmentCard(Color.Blue,2, 1,gameBoard);
        });

        System.out.println(player.getDashboardCopy().getDevelopmentCardZones().get(1).getLastCard());

        System.out.println("Coin: "+ player.getDashboardCopy().getStrongbox().amountOfResource(new CoinResource()));
        System.out.println("Stone: "+ player.getDashboardCopy().getStrongbox().amountOfResource(new StoneResource()));
        System.out.println("Shield: "+ player.getDashboardCopy().getStrongbox().amountOfResource(new ShieldResource()));
        System.out.println("Servant: "+ player.getDashboardCopy().getStrongbox().amountOfResource(new ServantResource()));
        for (DevelopmentCard devCard:player.getDashboardCopy().getDevelopmentCardZones().get(1).getCards()) {
            System.out.println(devCard);
        }
    }

    /**
     * Testing that buying multiple cards works
     * @throws NotEnoughResourcesException
     * @throws NotCoherentLevelException
     */
    @Test
    public void cardAcquisitionCompleteTest() throws NotEnoughResourcesException, NotCoherentLevelException{
        gameBoard.decksInitializer();
        for(int i=0;i<30;i++){
            player.getDashboardCopy().getStrongbox().addResource(new CoinResource());
            player.getDashboardCopy().getStrongbox().addResource(new ServantResource());
            player.getDashboardCopy().getStrongbox().addResource(new StoneResource());
            player.getDashboardCopy().getStrongbox().addResource(new ShieldResource());
        }
        //testing that the cards are put in the correct zone
        player.buyDevelopmentCard(Color.Green,1, 1,gameBoard);
        System.out.println(player.getDashboardCopy().getDevelopmentCardZones().get(1).getLastCard());
        player.buyDevelopmentCard(Color.Blue,2, 1,gameBoard);
        System.out.println(player.getDashboardCopy().getDevelopmentCardZones().get(1).getLastCard());
        player.buyDevelopmentCard(Color.Blue,3, 1,gameBoard);
        System.out.println(player.getDashboardCopy().getDevelopmentCardZones().get(1).getLastCard());
        player.buyDevelopmentCard(Color.Green,1, 2,gameBoard);
        System.out.println(player.getDashboardCopy().getDevelopmentCardZones().get(2).getLastCard());
        player.buyDevelopmentCard(Color.Blue,2, 2,gameBoard);
        System.out.println(player.getDashboardCopy().getDevelopmentCardZones().get(2).getLastCard());
        player.buyDevelopmentCard(Color.Green,1, 0,gameBoard);
        System.out.println(player.getDashboardCopy().getDevelopmentCardZones().get(0).getLastCard());
        player.buyDevelopmentCard(Color.Blue,2, 0,gameBoard);
        System.out.println(player.getDashboardCopy().getDevelopmentCardZones().get(0).getLastCard());
        System.out.println("Coin: "+ player.getDashboardCopy().getStrongbox().amountOfResource(new CoinResource()));
        System.out.println("Stone: "+ player.getDashboardCopy().getStrongbox().amountOfResource(new StoneResource()));
        System.out.println("Shield: "+ player.getDashboardCopy().getStrongbox().amountOfResource(new ShieldResource()));
        System.out.println("Servant: "+ player.getDashboardCopy().getStrongbox().amountOfResource(new ServantResource()));
        System.out.println("DevZone1:");
        for (DevelopmentCard devCard:player.getDashboardCopy().getDevelopmentCardZones().get(1).getCards()) {
            System.out.println(devCard);
        }
        System.out.println("DevZone2:");
        for (DevelopmentCard devCard:player.getDashboardCopy().getDevelopmentCardZones().get(2).getCards()) {
            System.out.println(devCard);
        }
        System.out.println("DevZone0:");
        for (DevelopmentCard devCard:player.getDashboardCopy().getDevelopmentCardZones().get(0).getCards()) {
            System.out.println(devCard);
        }
    }

    /**
     * Testing that the amount of victory points due to only dev cards and resources is correct
     * @throws NotEnoughResourcesException
     * @throws NotCoherentLevelException
     */
    @Test
    public void victoryPointsOnlyDevCardsAndResourcesTest() throws NotEnoughResourcesException, NotCoherentLevelException{
        gameBoard.decksInitializer();
        for(int i=0;i<30;i++){
            player.getDashboardCopy().getStrongbox().addResource(new CoinResource());
            player.getDashboardCopy().getStrongbox().addResource(new ServantResource());
            player.getDashboardCopy().getStrongbox().addResource(new StoneResource());
            player.getDashboardCopy().getStrongbox().addResource(new ShieldResource());
        }
        //testing that the cards are put in the correct zone
        player.buyDevelopmentCard(Color.Yellow,1, 1,gameBoard);
        player.buyDevelopmentCard(Color.Blue,2, 1,gameBoard);
        player.buyDevelopmentCard(Color.Blue,3, 1,gameBoard);
        player.buyDevelopmentCard(Color.Yellow,1, 2,gameBoard);
        player.buyDevelopmentCard(Color.Blue,2, 2,gameBoard);
        player.buyDevelopmentCard(Color.Green,1, 0,gameBoard);
        player.buyDevelopmentCard(Color.Blue,2, 0,gameBoard);
        for (DevelopmentCard devCard:player.getDashboardCopy().getDevelopmentCardZones().get(1).getCards()) {
            System.out.println(devCard.getVictoryPoints());
        }
        for (DevelopmentCard devCard:player.getDashboardCopy().getDevelopmentCardZones().get(2).getCards()) {
            System.out.println(devCard.getVictoryPoints());
        }
        for (DevelopmentCard devCard:player.getDashboardCopy().getDevelopmentCardZones().get(0).getCards()) {
            System.out.println(devCard.getVictoryPoints());
        }
        System.out.println(((player.getDashboardCopy().availableResourcesForProduction(new CoinResource())+player.getDashboardCopy().availableResourcesForProduction(new ServantResource())+
                player.getDashboardCopy().availableResourcesForProduction(new StoneResource())+player.getDashboardCopy().availableResourcesForProduction(new ShieldResource()))/5));


        DevelopmentRequirements requirement1 = new DevelopmentRequirements(1,1, Color.Blue);
        DevelopmentRequirements requirement2 = new DevelopmentRequirements(2,1,Color.Yellow);
        ArrayList<Requirements> requirements= new ArrayList<Requirements>();
        requirements.add(requirement1);
        requirements.add (requirement2);
        ServantResource servant = new ServantResource();
        ArrayList<Resource> array = new ArrayList<>();
        array.add(servant);
        WhiteToColor whiteToColor = new WhiteToColor(array);
        LeaderCard leaderCard = new LeaderCard(requirements,5,whiteToColor,false);
        player.giveCard(leaderCard);

        assertEquals(leaderCard,player.getLeaderCard(0));

        try {
            player.activateLeaderCard(0);
        } catch (NotInactiveException e) {
            e.printStackTrace();
        } catch (RequirementsUnfulfilledException e) {
            e.printStackTrace();
        }
        System.out.println(player.getVictoryPoints());
    }

    /**
     * Testing that victory points based on papal path is correct
     */
    @Test
    public void victoryPointsOnlyPapalPath(){
        assertEquals(0,player.getVictoryPoints());
        try {
            player.getDashboardCopy().getPapalPath().moveForward(7);
            assertEquals(2,player.getVictoryPoints());
            player.getDashboardCopy().getPapalPath().moveForward();
            assertEquals(4,player.getVictoryPoints());
        } catch (PapalCardActivatedException e) {

        }
        for(int i=0; i<20; i++){
            try {
                player.getDashboardCopy().getPapalPath().moveForward();
            } catch (PapalCardActivatedException e) {
            }
        }
        assertEquals(29,player.getVictoryPoints());
    }

    @Test
    public void TestingBaseProductions() throws PapalCardActivatedException {
        ArrayList<Resource> list = new ArrayList<>();
        ArrayList<Resource> list1 = new ArrayList<>();

        //wrong amount of resources exception thrown
        assertEquals(2,player.activateBaseProduction(list,list));

        list.add(new CoinResource()); list.add(new CoinResource()); list1.add(new CoinResource());

        //not enough resources to activate exception
        assertEquals(1,player.activateBaseProduction(list,list1));

        for(int i=0;i<30;i++){
            player.getDashboardCopy().getStrongbox().addResource(new CoinResource());
            player.getDashboardCopy().getStrongbox().addResource(new ServantResource());
            player.getDashboardCopy().getStrongbox().addResource(new StoneResource());
            player.getDashboardCopy().getStrongbox().addResource(new ShieldResource());
        }
        //now base prod has been activated
        assertEquals(0,player.activateBaseProduction(list,list1));

        for(int i=0;i<7;i++) {
            player.moveForwardFaith();
        }
        ArrayList<Resource> list2 = new ArrayList<>();
        list2.add(new FaithResource());
        assertEquals(3,player.activateBaseProduction(list,list2));
    }

    @Test
    public void testingDevProduction() throws NotCoherentLevelException, NotEnoughResourcesException, NotEnoughResourcesToActivateProductionException, PapalCardActivatedException {
        player.buyDevelopmentCardFake(Color.Blue,1,0);
        Exception exception = assertThrows(NotEnoughResourcesToActivateProductionException.class, () -> {
            player.activateDevelopmentProduction(0);
        });
        for(int i=0;i<30;i++){
            player.getDashboardCopy().getStrongbox().addResource(new CoinResource());
            player.getDashboardCopy().getStrongbox().addResource(new ServantResource());
            player.getDashboardCopy().getStrongbox().addResource(new StoneResource());
            player.getDashboardCopy().getStrongbox().addResource(new ShieldResource());
        }
        player.activateDevelopmentProduction(0);
    }

    @Test
    public void testingWarehouseInteraction() throws WarehouseDepotsRegularityError, LorenzoWonTheMatch, LorenzoActivatesPapalCardException, BothPlayerAndLorenzoActivatePapalCardException {
        player.addResourceInWarehouse(new CoinResource());
        player.addResourceInWarehouse(new CoinResource());
        player.addResourceInWarehouse(new StoneResource());
        assertEquals(ResourceType.Coin,player.typeOfDepotGivenItsIndex(3));
        assertEquals(ResourceType.Stone,player.typeOfDepotGivenItsIndex(2));
        player.removeResource(3);
        player.removeResource(3);
        player.swapResources();
        player.moveForwardLorenzo(2);
        player.moveForwardLorenzo();
        assertEquals(3,player.getPapalPath().getFaithPositionLorenzo());
        assertEquals(0,player.availableResourceOfType(ResourceType.Coin));
        assertEquals(1,player.availableResourceOfType(ResourceType.Stone));
        assertEquals(ResourceType.Stone,player.typeOfDepotGivenItsIndex(3));
        player.addResourceInWarehouse(new CoinResource());
        player.addResourceInWarehouse(new ServantResource());
        player.addResourceInWarehouse(new ShieldResource());
        assertEquals(ResourceType.Shield,player.typeOfDepotGivenItsIndex(4));
        player.removeExceedingDepot(4);
        assertEquals(ResourceType.Coin,player.typeOfDepotGivenItsIndex(2));
        assertEquals(ResourceType.Stone,player.typeOfDepotGivenItsIndex(3));
        assertEquals(ResourceType.Servant,player.typeOfDepotGivenItsIndex(1));
        assertEquals(null,player.typeOfDepotGivenItsIndex(4));
    }

    /**
     * Testing that all the method for leader cards work correctly
     */
    @Test
    public void testingLeaderCardsExtraDepot() throws NotInactiveException, RequirementsUnfulfilledException {
        ArrayList<Integer> indexes = new ArrayList<>();
        indexes.add(0); indexes.add(1);
        player.giveCard(gameBoard.drawCard());player.giveCard(gameBoard.drawCard());player.giveCard(gameBoard.drawCard());player.giveCard(gameBoard.drawCard());
        assertEquals(4,player.numOfLeaderCards());
        player.discardLeaderCards(indexes);
        assertEquals(2,player.numOfLeaderCards());
        assertNull(player.getLeaderCard(3));
        assertNotNull(player.getLeaderCard(1));
        Exception exception = assertThrows(RequirementsUnfulfilledException.class, () -> {
            player.activateLeaderCard(1);
        });


        StoneResource stone = new StoneResource();
        ResourcesRequirementsForAcquisition requirement1 = new ResourcesRequirementsForAcquisition(5,stone);
        ArrayList<Requirements> requirements= new ArrayList<Requirements>();
        requirements.add(requirement1);
        ServantResource servant = new ServantResource();
        ArrayList<Resource> resources= new ArrayList<Resource>();
        resources.add(servant);
        ExtraDeposit extraDeposit = new ExtraDeposit(resources);
        LeaderCard leaderCard = new LeaderCard(requirements,3,extraDeposit,false);

        for(int i=0;i<5;i++) player.addResourceInStrongbox(new StoneResource());
        player.discardLeaderCard(0);player.discardLeaderCard(0);
        assertEquals(-1,player.indexOfALeaderCard(leaderCard));
        player.giveCard(leaderCard);
        player.activateLeaderCard(0);
        Exception exception1 = assertThrows(NotInactiveException.class, () -> {
            player.activateLeaderCard(0);
        });
        assertEquals(PowerType.ExtraDeposit,player.returnPowerTypeOfTheSelectedCard(0));
        assertEquals(player.getLeaderCard(0),player.getLeaderCardsCopy().get(0));
        assertFalse(player.isALeaderProdCard(0));
        assertEquals(5,player.getStrongbox().amountOfResource(new StoneResource()));
        assertEquals(0,player.getWarehouse().amountOfResource(new StoneResource()));
        assertEquals(0,player.indexOfALeaderCard(leaderCard));
        Exception exception2 = assertThrows(WrongTypeOfLeaderPowerException.class, () -> {
            player.checkLeaderProduction(0);
        });
        assertEquals(1,player.numberOfExtraDepots());
        assertEquals(ResourceType.Servant,player.typeOfExtraDepotGivenItsIndex(0));
        assertEquals(0,player.resourcesContainedInAnExtraDepotGivenItsIndex(0));
        assertNotNull(player.getDashboardCopy().resourcesUsableForProd());
        assertNotNull(player.getDashboardCopy().allAvailableResources(new ServantResource()));
    }

    /**
     * testing that method for white to color leader cards work
     */
    @Test
    public void testingLeaderCardWTC() throws NotInactiveException, RequirementsUnfulfilledException, PapalCardActivatedException, NotCoherentLevelException, NotEnoughResourcesException {
        DevelopmentRequirements requirement1 = new DevelopmentRequirements(1,1, Color.Blue);
        DevelopmentRequirements requirement2 = new DevelopmentRequirements(2,1,Color.Yellow);
        ArrayList<Requirements> requirements= new ArrayList<Requirements>();
        requirements.add(requirement1);
        requirements.add (requirement2);
        ServantResource servant = new ServantResource();
        ArrayList<Resource> array = new ArrayList<>();
        array.add(servant);
        WhiteToColor whiteToColor = new WhiteToColor(array);
        LeaderCard leaderCard = new LeaderCard(requirements,5,whiteToColor,false);

        player.giveCard(leaderCard);
        player.buyDevelopmentCardFake(Color.Blue,1,0);player.buyDevelopmentCardFake(Color.Yellow,1,1);player.buyDevelopmentCardFake(Color.Yellow,1,2);
        player.activateLeaderCard(0);
        assertEquals(0,player.getWarehouse().amountOfResource(new ServantResource()));
        player.activateWhiteToColorCardWithSelectedIndex(0);
        assertEquals(1,player.getWarehouse().amountOfResource(new ServantResource()));
    }

    @Test
    public void testingProdLeaderCard() throws NotInactiveException, RequirementsUnfulfilledException, NotEnoughResourcesToActivateProductionException, LeaderCardNotActiveException, WrongTypeOfLeaderPowerException {
        ArrayList<Requirements> requirements = new ArrayList<>();
        ArrayList<Resource> resources= new ArrayList<>();
        StoneResource stone= new StoneResource();
        resources.add(stone);
        ExtraProd extraProd= new ExtraProd(resources);
        LeaderCard leaderCard = new LeaderCard(requirements,5,extraProd,false);

        player.giveCard(leaderCard);
        Exception exception = assertThrows(LeaderCardNotActiveException.class, () -> {
            player.checkLeaderProduction(0);
        });
        player.activateLeaderCard(0);
        Exception exception2 = assertThrows(NotInactiveException.class, () -> {
            player.activateLeaderCard(0);
        });
        Exception exception1 = assertThrows(NotEnoughResourcesToActivateProductionException.class, () -> {
            player.checkLeaderProduction(0);
        });
        player.addResourceInStrongbox(new StoneResource());

        try {
            player.checkLeaderProduction(0);
        } catch(Exception e) {
            fail("Should not have thrown any exception");
        }

        assertEquals(1,player.resourcesToProduceInTheSpecifiedLeaderCard(0));
        assertTrue(player.isALeaderProdCard(0));
        ArrayList<Resource> list = new ArrayList<>();
        list.add(new CoinResource());
        player.leaderCardProduction(0,list);

        try {
            player.acquireResourcesFromMarket(gameBoard,false,0);
            player.endTurn();
            assertEquals(1,player.getStrongbox().amountOfResource(new CoinResource()));
        } catch(Exception e) {
            fail("Should not have thrown any exception");
        }
    }
}