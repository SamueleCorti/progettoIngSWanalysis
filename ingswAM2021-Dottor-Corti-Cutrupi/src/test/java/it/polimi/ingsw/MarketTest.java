package it.polimi.ingsw;

import it.polimi.ingsw.exception.PapalCardActivatedException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.boardsAndPlayer.Dashboard;
import it.polimi.ingsw.model.boardsAndPlayer.GameBoard;
import it.polimi.ingsw.model.boardsAndPlayer.Player;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.market.OutOfBoundException;
import it.polimi.ingsw.model.resource.*;
import it.polimi.ingsw.exception.warehouseErrors.WarehouseDepotsRegularityError;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;


public class MarketTest {
    Market market=new Market();
    Dashboard dashboard= new Dashboard(1);

    public MarketTest() throws FileNotFoundException {
    }

    @Test
    public void getRowTest(){
        System.out.println("ROW TEST:");
        market.printMarket();
        try {
            System.out.println("ROW 1");
            market.acquireResourcesFromMarket(true,0,dashboard);
            market.printMarket();
            System.out.println("ROW 2");
            market.acquireResourcesFromMarket(true,1,dashboard);
            market.printMarket();
            System.out.println("ROW 3");
            market.acquireResourcesFromMarket(true,2,dashboard);
            market.printMarket();
        } catch (WarehouseDepotsRegularityError warehouseDepotsRegularityError) {
            warehouseDepotsRegularityError.printStackTrace();
        } catch (PapalCardActivatedException e) {
            e.printStackTrace();
        }
        //market.getResourcesFromMarket(true,3,dashboard);
        //market.printMarket();
    }
    @Test
    public void getColumnTest() {
        System.out.println("COLUMN TEST:");
        market.printMarket();
        System.out.println("COULMN 1");
        try {
            market.acquireResourcesFromMarket(false,0,dashboard);
            market.printMarket();
            System.out.println("COULMN 2");
            market.acquireResourcesFromMarket(false,1,dashboard);
            market.printMarket();
            System.out.println("COULMN 3");
            market.acquireResourcesFromMarket(false,2,dashboard);
            market.printMarket();
            System.out.println("COULMN 4");
            market.acquireResourcesFromMarket(false,3,dashboard);
            market.printMarket();
        } catch (WarehouseDepotsRegularityError warehouseDepotsRegularityError) {
            warehouseDepotsRegularityError.printStackTrace();
        } catch (PapalCardActivatedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void FaithResourceTest() {
        try {
            System.out.println("PAPAL FAITH POSITION: "+dashboard.getPapalPath().getFaithPosition());
            System.out.println("ROW 3");
            market.acquireResourcesFromMarket(true,2,dashboard);
            market.printMarket();
            System.out.println("COLUMN 3");
            market.acquireResourcesFromMarket(false,2,dashboard);
            market.printMarket();
            System.out.println("COLUMN 2");
            market.acquireResourcesFromMarket(false,1,dashboard);
            market.printMarket();
            System.out.println("ROW 1");
            market.acquireResourcesFromMarket(true,0,dashboard);
            market.printMarket();
        } catch (WarehouseDepotsRegularityError warehouseDepotsRegularityError) {
            warehouseDepotsRegularityError.printStackTrace();
        } catch (PapalCardActivatedException e) {
            e.printStackTrace();
        }
        System.out.println("PAPAL FAITH POSITION: "+dashboard.getPapalPath().getFaithPosition());
    }

    @Test
    public void shufflingTest(){
        Dashboard dashboard= new Dashboard(1);
        Market market= new Market(new BlankResource(),new CoinResource(), new CoinResource(), new ShieldResource(),new ServantResource(),new ShieldResource(), new ServantResource(), new BlankResource(), new BlankResource(), new StoneResource(), new FaithResource(), new BlankResource(), new StoneResource());
        try {
            market.acquireResourcesFromMarket(false,0, dashboard);
            market.acquireResourcesFromMarket(true,0,dashboard);
            market.acquireResourcesFromMarket(true,2,dashboard);
            for(int i=0; i<5; i++)  market.acquireResourcesFromMarket(true,1,dashboard);
            assertEquals(ResourceType.Blank,market.getSingleResource(0,0).getResourceType());
            assertEquals(ResourceType.Stone,market.getSingleResource(0,1).getResourceType());
            assertEquals(ResourceType.Coin,market.getSingleResource(0,2).getResourceType());
            assertEquals(ResourceType.Coin,market.getSingleResource(0,3).getResourceType());
            assertEquals(ResourceType.Blank,market.getSingleResource(1,0).getResourceType());
            assertEquals(ResourceType.Shield,market.getSingleResource(1,1).getResourceType());
            assertEquals(ResourceType.Servant,market.getSingleResource(1,2).getResourceType());
            assertEquals(ResourceType.Blank,market.getSingleResource(1,3).getResourceType());
            assertEquals(ResourceType.Shield,market.getSingleResource(2,0).getResourceType());
            assertEquals(ResourceType.Servant,market.getSingleResource(2,1).getResourceType());
            assertEquals(ResourceType.Stone,market.getSingleResource(2,2).getResourceType());
            assertEquals(ResourceType.Faith,market.getSingleResource(2,3).getResourceType());
            assertEquals(ResourceType.Blank,market.getFloatingMarble().getResourceType());
        } catch (WarehouseDepotsRegularityError warehouseDepotsRegularityError) {
            warehouseDepotsRegularityError.printStackTrace();
        } catch (PapalCardActivatedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void isNewProblemTest() throws PapalCardActivatedException {
        GameBoard gameBoard= new GameBoard("giulio");
        Player player= new Player("Giulio",gameBoard);
        try {
            player.acquireResourcesFromMarket(gameBoard, true, 0);
        } catch (OutOfBoundException e) {
            e.printStackTrace();
        } catch (WarehouseDepotsRegularityError warehouseDepotsRegularityError) {
            warehouseDepotsRegularityError.printStackTrace();
        }
        String str= "";
    }

    @Test
    public void marketFaithInteractionTest(){
        Resource coin= new CoinResource();
        Resource stone= new StoneResource();
        Resource servant= new ServantResource();
        Resource shield= new ShieldResource();
        Resource faith= new FaithResource();
        Resource blank= new BlankResource();
        GameBoard gameBoard= new GameBoard("Piero");
        Player player= new Player("Piero",1, gameBoard);
        try {
            for(int i=0; i<7; i++)
            player.moveForwardFaith();
        } catch (PapalCardActivatedException e) {
        }
        Market market= new Market(coin,faith,shield,coin,blank,blank,blank,stone,servant,servant,stone,shield,blank);
        market.printMarket();
        try {
            market.checkNumOfBlank(true,1);
            market.checkNumOfBlank(true,10);
            market.checkNumOfBlank(false,1);
        } catch (OutOfBoundException e) {
        }
        try {
            player.acquireResourcesFromMarket(gameBoard,true,1);
            player.acquireResourcesFromMarket(gameBoard,true,2);
            player.acquireResourcesFromMarket(gameBoard,true,3);
            player.acquireResourcesFromMarket(gameBoard,false,1);
            player.acquireResourcesFromMarket(gameBoard,false,2);
            player.acquireResourcesFromMarket(gameBoard,false,3);
            player.acquireResourcesFromMarket(gameBoard,false,4);
        } catch (OutOfBoundException e) {
        } catch (WarehouseDepotsRegularityError warehouseDepotsRegularityError) {
        } catch (PapalCardActivatedException e) {
            try {
                market.finishMarket(true,1,player.getDashboardCopy(),1);
            } catch (PapalCardActivatedException papalCardActivatedException) {
            }
        }
        market.getStringMarket();
        market.pushLine(true,1);
        market.pushLine(false,1);
    }

    @Test
    public void marketMethodsTest(){
        Dashboard dashboard= new Dashboard(1);
        Market market= new Market();
        market.pushLine(true,1);
        market.pushLine(false,1);
        try {
            assertTrue(-1<market.checkNumOfBlank(true,1));
            assertTrue(-1<market.checkNumOfBlank(false,1));
            market.finishMarket(true, 1,dashboard, 2);
            market.finishMarket(false, 1,dashboard, 2);
            market.checkNumOfBlank(false,10);
        } catch (PapalCardActivatedException | OutOfBoundException e) {
        }
        assertTrue(market.getSingleResource(1,1) instanceof Resource);
    }


}
