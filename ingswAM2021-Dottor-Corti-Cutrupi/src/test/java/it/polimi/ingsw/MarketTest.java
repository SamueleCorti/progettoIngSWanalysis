package it.polimi.ingsw;

import it.polimi.ingsw.market.Market;
import it.polimi.ingsw.market.OutOfBoundException;
import it.polimi.ingsw.papalpath.PapalPath;
import it.polimi.ingsw.resource.*;
import it.polimi.ingsw.storing.RegularityError;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class MarketTest {
    Market market=new Market();
    Dashboard dashboard= new Dashboard(1);


    @Test
    public void getRowTest() throws OutOfBoundException, RegularityError {
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
        market.getResourcesFromMarket(true,3,dashboard);
        market.printMarket();
    }
    @Test
    public void getColumnTest() throws OutOfBoundException, RegularityError {
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
        System.out.println("COLONNA 5");
        market.getResourcesFromMarket(false,4,dashboard);
        market.printMarket();
    }

    @Test
    public void FaithResourceTest() throws OutOfBoundException, RegularityError{
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
    public void shufflingTest() throws OutOfBoundException, RegularityError{
        Dashboard dashboard= new Dashboard(1);
        Market market= new Market(new BlankResource(),new CoinResource(), new CoinResource(), new ShieldResource(),new ServantResource(),new ShieldResource(), new ServantResource(), new BlankResource(), new BlankResource(), new StoneResource(), new FaithResource(), new BlankResource(), new StoneResource());
        market.getResourcesFromMarket(false,0, dashboard);
        market.getResourcesFromMarket(true,0,dashboard);
        market.getResourcesFromMarket(true,2,dashboard);
        for(int i=0; i<5; i++)  market.getResourcesFromMarket(true,1,dashboard);
        assertEquals("blank",market.getSingleResource(0,0).getResourceType());
        assertEquals("stone",market.getSingleResource(0,1).getResourceType());
        assertEquals("coin",market.getSingleResource(0,2).getResourceType());
        assertEquals("coin",market.getSingleResource(0,3).getResourceType());
        assertEquals("blank",market.getSingleResource(1,0).getResourceType());
        assertEquals("shield",market.getSingleResource(1,1).getResourceType());
        assertEquals("servant",market.getSingleResource(1,2).getResourceType());
        assertEquals("blank",market.getSingleResource(1,3).getResourceType());
        assertEquals("shield",market.getSingleResource(2,0).getResourceType());
        assertEquals("servant",market.getSingleResource(2,1).getResourceType());
        assertEquals("stone",market.getSingleResource(2,2).getResourceType());
        assertEquals("faith",market.getSingleResource(2,3).getResourceType());
        assertEquals("blank",market.getFloatingMarble().getResourceType());

    }


}