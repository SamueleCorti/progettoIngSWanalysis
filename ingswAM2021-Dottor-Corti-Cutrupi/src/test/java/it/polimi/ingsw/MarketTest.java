package it.polimi.ingsw;

import it.polimi.ingsw.market.Market;
import it.polimi.ingsw.market.OutOfBoundException;
import it.polimi.ingsw.papalpath.PapalPath;
import it.polimi.ingsw.storing.RegularityError;
import org.junit.jupiter.api.Test;


public class MarketTest {
    Market market=new Market();
    Warehouse warehouse= new Warehouse();
    PapalPath papalPath= new PapalPath(1);


    @Test
    public void getRowTest() throws OutOfBoundException, RegularityError {
        System.out.println("PROVA DELLE RIGHE:");
        market.printMarket();
        System.out.println("RIGA 1");
        market.getResourcesFromMarket(true,0,warehouse,papalPath);
        market.printMarket();
        System.out.println("RIGA 2");
        market.getResourcesFromMarket(true,1,warehouse,papalPath);
        market.printMarket();
        System.out.println("RIGA 3");
        market.getResourcesFromMarket(true,2,warehouse,papalPath);
        market.printMarket();
        System.out.println("RIGA 4");
        market.getResourcesFromMarket(true,3,warehouse,papalPath);
        market.printMarket();
    }
    @Test
    public void getColumnTest() throws OutOfBoundException, RegularityError {
        System.out.println("PROVA DELLE COLONNE:");
        market.printMarket();
        System.out.println("COLONNA 1");
        market.getResourcesFromMarket(false,0,warehouse,papalPath);
        market.printMarket();
        System.out.println("COLONNA 2");
        market.getResourcesFromMarket(false,1,warehouse,papalPath);
        market.printMarket();
        System.out.println("COLONNA 3");
        market.getResourcesFromMarket(false,2,warehouse,papalPath);
        market.printMarket();
        System.out.println("COLONNA 4");
        market.getResourcesFromMarket(false,3,warehouse,papalPath);
        market.printMarket();
        System.out.println("COLONNA 5");
        market.getResourcesFromMarket(false,4,warehouse,papalPath);
        market.printMarket();
    }

    @Test
    public void FaithResourceTest() throws OutOfBoundException, RegularityError{
        System.out.println("Posizione nel percorso papale: "+papalPath.getFaithPosition());
        System.out.println("RIGA 3");
        market.getResourcesFromMarket(true,2,warehouse,papalPath);
        market.printMarket();
        System.out.println("COLONNA 3");
        market.getResourcesFromMarket(false,2,warehouse,papalPath);
        market.printMarket();
        System.out.println("COLONNA 2");
        market.getResourcesFromMarket(false,1,warehouse,papalPath);
        market.printMarket();
        System.out.println("RIGA 1");
        market.getResourcesFromMarket(true,0,warehouse,papalPath);
        market.printMarket();
        System.out.println("Posizione nel percorso papale: "+papalPath.getFaithPosition());
    }

    /*

     */
}
