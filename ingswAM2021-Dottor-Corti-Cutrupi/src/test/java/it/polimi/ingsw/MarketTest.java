package it.polimi.ingsw;

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
    public void getRowTest() throws OutOfBoundException, WarehouseDepotsRegularityError {
        System.out.println("PROVA DELLE RIGHE:");
        market.printMarket();
        System.out.println("RIGA 1");
        market.getResourcesFromMarket(true,0,dashboard);
        market.printMarket();
        System.out.println("RIGA 2");
        market.getResourcesFromMarket(true,1,dashboard);
        market.printMarket();
        System.out.println("RIGA 3");
        market.getResourcesFromMarket(true,2,dashboard);
        market.printMarket();
        System.out.println("RIGA 4");
        //market.getResourcesFromMarket(true,3,dashboard);
        //market.printMarket();
    }
    @Test
    public void getColumnTest() throws OutOfBoundException, WarehouseDepotsRegularityError {
        System.out.println("PROVA DELLE COLONNE:");
        market.printMarket();
        System.out.println("COLONNA 1");
        market.getResourcesFromMarket(false,0,dashboard);
        market.printMarket();
        System.out.println("COLONNA 2");
        market.getResourcesFromMarket(false,1,dashboard);
        market.printMarket();
        System.out.println("COLONNA 3");
        market.getResourcesFromMarket(false,2,dashboard);
        market.printMarket();
        System.out.println("COLONNA 4");
        market.getResourcesFromMarket(false,3,dashboard);
        market.printMarket();
        //System.out.println("COLONNA 5");
        //market.getResourcesFromMarket(false,4,dashboard);
        market.printMarket();
    }

    @Test
    public void FaithResourceTest() throws OutOfBoundException, WarehouseDepotsRegularityError {
        System.out.println("Posizione nel percorso papale: "+dashboard.getPapalPath().getFaithPosition());
        System.out.println("RIGA 3");
        market.getResourcesFromMarket(true,2,dashboard);
        market.printMarket();
        System.out.println("COLONNA 3");
        market.getResourcesFromMarket(false,2,dashboard);
        market.printMarket();
        System.out.println("COLONNA 2");
        market.getResourcesFromMarket(false,1,dashboard);
        market.printMarket();
        System.out.println("RIGA 1");
        market.getResourcesFromMarket(true,0,dashboard);
        market.printMarket();
        System.out.println("Posizione nel percorso papale: "+dashboard.getPapalPath().getFaithPosition());
    }

    @Test
    public void shufflingTest() throws OutOfBoundException, WarehouseDepotsRegularityError, FileNotFoundException {
        Dashboard dashboard= new Dashboard(1);
        Market market= new Market(new BlankResource(),new CoinResource(), new CoinResource(), new ShieldResource(),new ServantResource(),new ShieldResource(), new ServantResource(), new BlankResource(), new BlankResource(), new StoneResource(), new FaithResource(), new BlankResource(), new StoneResource());
        market.getResourcesFromMarket(false,0, dashboard);
        market.getResourcesFromMarket(true,0,dashboard);
        market.getResourcesFromMarket(true,2,dashboard);
        for(int i=0; i<5; i++)  market.getResourcesFromMarket(true,1,dashboard);
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

    }

    @Test
    public void isNewProblemTest(){
        Player player= new Player("Giulio");
        GameBoard gameBoard= new GameBoard("giulio");
        try {
            player.getResourcesFromMarket(gameBoard, true, 0);
        } catch (OutOfBoundException e) {
            e.printStackTrace();
        } catch (WarehouseDepotsRegularityError warehouseDepotsRegularityError) {
            warehouseDepotsRegularityError.printStackTrace();
        }
        String str= "";
    }


}
