package it.polimi.ingsw;

import it.polimi.ingsw.exception.NotCoherentLevelException;
import it.polimi.ingsw.exception.NotEnoughResourcesException;
import it.polimi.ingsw.exception.NotEnoughResourcesToActivateProductionException;
import it.polimi.ingsw.exception.PapalCardActivatedException;
import it.polimi.ingsw.model.boardsAndPlayer.GameBoard;
import it.polimi.ingsw.model.boardsAndPlayer.Player;
import it.polimi.ingsw.model.developmentcard.Color;
import it.polimi.ingsw.model.developmentcard.DevelopmentCard;
import it.polimi.ingsw.model.resource.*;
import it.polimi.ingsw.exception.warehouseErrors.WarehouseDepotsRegularityError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class PlayerTest {
    GameBoard gameBoard= new GameBoard(new ArrayList<>());
    Player player= new Player("Lopez",1,gameBoard);

    public PlayerTest() throws FileNotFoundException {
    }

    @Test
    public void cardLvl1AcquisitionTest() throws FileNotFoundException, NotEnoughResourcesException, WarehouseDepotsRegularityError, NotCoherentLevelException, NotEnoughResourcesToActivateProductionException {
        gameBoard.decksInitializer();
        for(int i=0;i<3;i++){
            player.getDashboard().getStrongbox().addResource(new CoinResource());
            player.getDashboard().getStrongbox().addResource(new ServantResource());
            player.getDashboard().getStrongbox().addResource(new StoneResource());
            player.getDashboard().getStrongbox().addResource(new ShieldResource());
        }
        player.buyDevelopmentCard(Color.Green,1, 1,gameBoard);
        System.out.println(player.getDashboard().getDevelopmentCardZones().get(1).getFirstCard());
        System.out.println("Coin: "+ player.getDashboard().getStrongbox().amountOfResource(new CoinResource()));
        System.out.println("Stone: "+ player.getDashboard().getStrongbox().amountOfResource(new StoneResource()));
        System.out.println("Shield: "+ player.getDashboard().getStrongbox().amountOfResource(new ShieldResource()));
        System.out.println("Servant: "+ player.getDashboard().getStrongbox().amountOfResource(new ServantResource()));
    }

    @Test
    public void cardLvl2AcquisitionTest() throws FileNotFoundException, NotEnoughResourcesException, WarehouseDepotsRegularityError, NotCoherentLevelException, NotEnoughResourcesToActivateProductionException {
        gameBoard.decksInitializer();
        for(int i=0;i<7;i++){
            player.getDashboard().getStrongbox().addResource(new CoinResource());
            player.getDashboard().getStrongbox().addResource(new ServantResource());
            player.getDashboard().getStrongbox().addResource(new StoneResource());
            player.getDashboard().getStrongbox().addResource(new ShieldResource());
        }
        //testing that the cards are put in the correct zone
        player.buyDevelopmentCard(Color.Green,1, 1,gameBoard);
        System.out.println(player.getDashboard().getDevelopmentCardZones().get(1).getLastCard());
        player.buyDevelopmentCard(Color.Blue,2, 1,gameBoard);
        System.out.println(player.getDashboard().getDevelopmentCardZones().get(1).getLastCard());
        System.out.println("Coin: "+ player.getDashboard().getStrongbox().amountOfResource(new CoinResource()));
        System.out.println("Stone: "+ player.getDashboard().getStrongbox().amountOfResource(new StoneResource()));
        System.out.println("Shield: "+ player.getDashboard().getStrongbox().amountOfResource(new ShieldResource()));
        System.out.println("Servant: "+ player.getDashboard().getStrongbox().amountOfResource(new ServantResource()));
        for (DevelopmentCard devCard:player.getDashboard().getDevelopmentCardZones().get(1).getCards()) {
            System.out.println(devCard);
        }
    }

    @Test
    public void cardLvl2ErrorAcquisitionTest(){
        gameBoard.decksInitializer();
        for(int i=0;i<7;i++){
            player.getDashboard().getStrongbox().addResource(new CoinResource());
            player.getDashboard().getStrongbox().addResource(new ServantResource());
            player.getDashboard().getStrongbox().addResource(new StoneResource());
            player.getDashboard().getStrongbox().addResource(new ShieldResource());
        }
        //testing that the cards are put in the correct zone
        try {
            player.buyDevelopmentCard(Color.Green,1, 1,gameBoard);
        } catch (NotCoherentLevelException e) {
            e.printStackTrace();
        } catch (NotEnoughResourcesException e) {
            e.printStackTrace();
        }
        System.out.println(player.getDashboard().getDevelopmentCardZones().get(1).getLastCard());
        try {
            player.buyDevelopmentCard(Color.Blue,2, 2,gameBoard);
        } catch (NotCoherentLevelException e) {
            System.out.println("WRONG DEVELOPMENT CARD ZONE");
        } catch (NotEnoughResourcesException e) {
            System.out.println("WRONG DEVELOPMENT CARD ZONE");
        }
        System.out.println(player.getDashboard().getDevelopmentCardZones().get(1).getLastCard());
        System.out.println(player.getDashboard().getDevelopmentCardZones().get(2).getLastCard());
        System.out.println("Coin: "+ player.getDashboard().getStrongbox().amountOfResource(new CoinResource()));
        System.out.println("Stone: "+ player.getDashboard().getStrongbox().amountOfResource(new StoneResource()));
        System.out.println("Shield: "+ player.getDashboard().getStrongbox().amountOfResource(new ShieldResource()));
        System.out.println("Servant: "+ player.getDashboard().getStrongbox().amountOfResource(new ServantResource()));
        for (DevelopmentCard devCard:player.getDashboard().getDevelopmentCardZones().get(1).getCards()) {
            System.out.println(devCard);
        }
    }

    @Test
    public void cardLvl3AcquisitionTest() throws FileNotFoundException, NotEnoughResourcesException, WarehouseDepotsRegularityError, NotCoherentLevelException, NotEnoughResourcesToActivateProductionException {
        gameBoard.decksInitializer();
        for(int i=0;i<15;i++){
            player.getDashboard().getStrongbox().addResource(new CoinResource());
            player.getDashboard().getStrongbox().addResource(new ServantResource());
            player.getDashboard().getStrongbox().addResource(new StoneResource());
            player.getDashboard().getStrongbox().addResource(new ShieldResource());
        }
        //testing that the cards are put in the correct zone
        player.buyDevelopmentCard(Color.Green,1, 1,gameBoard);
        System.out.println(player.getDashboard().getDevelopmentCardZones().get(1).getLastCard());
        player.buyDevelopmentCard(Color.Blue,2, 1,gameBoard);
        System.out.println(player.getDashboard().getDevelopmentCardZones().get(1).getLastCard());
        player.buyDevelopmentCard(Color.Yellow,3, 1,gameBoard);
        System.out.println(player.getDashboard().getDevelopmentCardZones().get(1).getLastCard());
        System.out.println("Coin: "+ player.getDashboard().getStrongbox().amountOfResource(new CoinResource()));
        System.out.println("Stone: "+ player.getDashboard().getStrongbox().amountOfResource(new StoneResource()));
        System.out.println("Shield: "+ player.getDashboard().getStrongbox().amountOfResource(new ShieldResource()));
        System.out.println("Servant: "+ player.getDashboard().getStrongbox().amountOfResource(new ServantResource()));
        for (DevelopmentCard devCard:player.getDashboard().getDevelopmentCardZones().get(1).getCards()) {
            System.out.println(devCard);
        }
    }

    @Test
    public void cardLvl3ErrorAcquisitionTest(){
        gameBoard.decksInitializer();
        for(int i=0;i<15;i++){
            player.getDashboard().getStrongbox().addResource(new CoinResource());
            player.getDashboard().getStrongbox().addResource(new ServantResource());
            player.getDashboard().getStrongbox().addResource(new StoneResource());
            player.getDashboard().getStrongbox().addResource(new ShieldResource());
        }
        //testing that the cards are put in the correct zone
        try {
            player.buyDevelopmentCard(Color.Green,1, 1,gameBoard);
            System.out.println(player.getDashboard().getDevelopmentCardZones().get(1).getLastCard());
            player.buyDevelopmentCard(Color.Blue,3, 1,gameBoard);
            System.out.println(player.getDashboard().getDevelopmentCardZones().get(1).getLastCard());
        } catch (NotCoherentLevelException e) {
            System.out.println("WRONG DEVELOPMENT CARD ZONE");
        } catch (NotEnoughResourcesException e) {
            System.out.println("WRONG DEVELOPMENT CARD ZONE");
        }
        System.out.println("Coin: "+ player.getDashboard().getStrongbox().amountOfResource(new CoinResource()));
        System.out.println("Stone: "+ player.getDashboard().getStrongbox().amountOfResource(new StoneResource()));
        System.out.println("Shield: "+ player.getDashboard().getStrongbox().amountOfResource(new ShieldResource()));
        System.out.println("Servant: "+ player.getDashboard().getStrongbox().amountOfResource(new ServantResource()));
        for (DevelopmentCard devCard:player.getDashboard().getDevelopmentCardZones().get(1).getCards()) {
            System.out.println(devCard);
        }
    }
    @Test
    public void cardLvl2DuplicateErrorAcquisitionTest(){
        gameBoard.decksInitializer();
        for(int i=0;i<15;i++){
            player.getDashboard().getStrongbox().addResource(new CoinResource());
            player.getDashboard().getStrongbox().addResource(new ServantResource());
            player.getDashboard().getStrongbox().addResource(new StoneResource());
            player.getDashboard().getStrongbox().addResource(new ShieldResource());
        }
        //testing that the cards are put in the correct zone
        try {
            player.buyDevelopmentCard(Color.Green,1, 1,gameBoard);
            System.out.println(player.getDashboard().getDevelopmentCardZones().get(1).getLastCard());
            player.buyDevelopmentCard(Color.Blue,2, 1,gameBoard);
            System.out.println(player.getDashboard().getDevelopmentCardZones().get(1).getLastCard());
            player.buyDevelopmentCard(Color.Blue,2, 1,gameBoard);
            System.out.println(player.getDashboard().getDevelopmentCardZones().get(1).getLastCard());
        } catch (NotCoherentLevelException e) {
            System.out.println("WRONG DEVELOPMENT CARD ZONE");
        } catch (NotEnoughResourcesException e) {
            System.out.println("WRONG DEVELOPMENT CARD ZONE");
        }
        System.out.println("Coin: "+ player.getDashboard().getStrongbox().amountOfResource(new CoinResource()));
        System.out.println("Stone: "+ player.getDashboard().getStrongbox().amountOfResource(new StoneResource()));
        System.out.println("Shield: "+ player.getDashboard().getStrongbox().amountOfResource(new ShieldResource()));
        System.out.println("Servant: "+ player.getDashboard().getStrongbox().amountOfResource(new ServantResource()));
        for (DevelopmentCard devCard:player.getDashboard().getDevelopmentCardZones().get(1).getCards()) {
            System.out.println(devCard);
        }
    }

    @Test
    public void cardAcquisitionCompleteTest() throws FileNotFoundException, NotEnoughResourcesException, WarehouseDepotsRegularityError, NotCoherentLevelException, NotEnoughResourcesToActivateProductionException {
        gameBoard.decksInitializer();
        for(int i=0;i<30;i++){
            player.getDashboard().getStrongbox().addResource(new CoinResource());
            player.getDashboard().getStrongbox().addResource(new ServantResource());
            player.getDashboard().getStrongbox().addResource(new StoneResource());
            player.getDashboard().getStrongbox().addResource(new ShieldResource());
        }
        //testing that the cards are put in the correct zone
        player.buyDevelopmentCard(Color.Green,1, 1,gameBoard);
        System.out.println(player.getDashboard().getDevelopmentCardZones().get(1).getLastCard());
        player.buyDevelopmentCard(Color.Blue,2, 1,gameBoard);
        System.out.println(player.getDashboard().getDevelopmentCardZones().get(1).getLastCard());
        player.buyDevelopmentCard(Color.Blue,3, 1,gameBoard);
        System.out.println(player.getDashboard().getDevelopmentCardZones().get(1).getLastCard());
        player.buyDevelopmentCard(Color.Green,1, 2,gameBoard);
        System.out.println(player.getDashboard().getDevelopmentCardZones().get(2).getLastCard());
        player.buyDevelopmentCard(Color.Blue,2, 2,gameBoard);
        System.out.println(player.getDashboard().getDevelopmentCardZones().get(2).getLastCard());
        player.buyDevelopmentCard(Color.Green,1, 0,gameBoard);
        System.out.println(player.getDashboard().getDevelopmentCardZones().get(0).getLastCard());
        player.buyDevelopmentCard(Color.Blue,2, 0,gameBoard);
        System.out.println(player.getDashboard().getDevelopmentCardZones().get(0).getLastCard());
        System.out.println("Coin: "+ player.getDashboard().getStrongbox().amountOfResource(new CoinResource()));
        System.out.println("Stone: "+ player.getDashboard().getStrongbox().amountOfResource(new StoneResource()));
        System.out.println("Shield: "+ player.getDashboard().getStrongbox().amountOfResource(new ShieldResource()));
        System.out.println("Servant: "+ player.getDashboard().getStrongbox().amountOfResource(new ServantResource()));
        System.out.println("DevZone1:");
        for (DevelopmentCard devCard:player.getDashboard().getDevelopmentCardZones().get(1).getCards()) {
            System.out.println(devCard);
        }
        System.out.println("DevZone2:");
        for (DevelopmentCard devCard:player.getDashboard().getDevelopmentCardZones().get(2).getCards()) {
            System.out.println(devCard);
        }
        System.out.println("DevZone0:");
        for (DevelopmentCard devCard:player.getDashboard().getDevelopmentCardZones().get(0).getCards()) {
            System.out.println(devCard);
        }
    }

    @Test
    public void victoryPointsOnlyDevCardsAndResourcesTest() throws FileNotFoundException, NotEnoughResourcesException, WarehouseDepotsRegularityError, NotCoherentLevelException, NotEnoughResourcesToActivateProductionException {
        gameBoard.decksInitializer();
        for(int i=0;i<20;i++){
            player.getDashboard().getStrongbox().addResource(new CoinResource());
            player.getDashboard().getStrongbox().addResource(new ServantResource());
            player.getDashboard().getStrongbox().addResource(new StoneResource());
            player.getDashboard().getStrongbox().addResource(new ShieldResource());
        }
        //testing that the cards are put in the correct zone
        player.buyDevelopmentCard(Color.Green,1, 1,gameBoard);
        player.buyDevelopmentCard(Color.Blue,2, 1,gameBoard);
        player.buyDevelopmentCard(Color.Blue,3, 1,gameBoard);
        player.buyDevelopmentCard(Color.Green,1, 2,gameBoard);
        player.buyDevelopmentCard(Color.Blue,2, 2,gameBoard);
        player.buyDevelopmentCard(Color.Green,1, 0,gameBoard);
        player.buyDevelopmentCard(Color.Blue,2, 0,gameBoard);
        for (DevelopmentCard devCard:player.getDashboard().getDevelopmentCardZones().get(1).getCards()) {
            System.out.println(devCard.getVictoryPoints());
        }
        for (DevelopmentCard devCard:player.getDashboard().getDevelopmentCardZones().get(2).getCards()) {
            System.out.println(devCard.getVictoryPoints());
        }
        for (DevelopmentCard devCard:player.getDashboard().getDevelopmentCardZones().get(0).getCards()) {
            System.out.println(devCard.getVictoryPoints());
        }
        System.out.println(((player.getDashboard().availableResourcesForProduction(new CoinResource())+player.getDashboard().availableResourcesForProduction(new ServantResource())+
                player.getDashboard().availableResourcesForProduction(new StoneResource())+player.getDashboard().availableResourcesForProduction(new ShieldResource()))/5));
        System.out.println(player.getVictoryPoints());
    }

    @Test
    public void victoryPointsOnlyLeaderCardsAndResourcesTest(){

    }

    @Test
    public void victoryPointsOnlyPapalPath(){
        assertEquals(0,player.getVictoryPoints());
        try {
            player.getDashboard().getPapalPath().moveForward(7);
            assertEquals(2,player.getVictoryPoints());
            player.getDashboard().getPapalPath().moveForward();
            assertEquals(4,player.getVictoryPoints());
            player.getDashboard().getPapalPath().moveForward(20);
        } catch (PapalCardActivatedException e) {
            e.printStackTrace();
        }
        assertEquals(29,player.getVictoryPoints());
    }
}
