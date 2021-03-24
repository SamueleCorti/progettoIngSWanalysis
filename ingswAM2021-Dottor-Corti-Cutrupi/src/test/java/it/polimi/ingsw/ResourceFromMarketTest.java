package it.polimi.ingsw;

import it.polimi.ingsw.market.OutOfBoundException;
import it.polimi.ingsw.market.PremadeMarket;
import it.polimi.ingsw.papalpath.PapalPath;
import it.polimi.ingsw.storing.RegularityError;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResourceFromMarketTest {

    PremadeMarket market1= new PremadeMarket();
    PapalPath papalPath1= new PapalPath(1);
    Warehouse warehouse1= new Warehouse();


    //tests the interation between papalPath, warehouse and market
    @Test
    public void faithFromMarketTest() throws OutOfBoundException, RegularityError{
        for(int x=0;x<5;x++)    {
            market1.getResourcesFromMarket(true,2,warehouse1,papalPath1);
        }
        assertEquals(4,papalPath1.getFaithPosition());
    }

    //tests if the quantities returned have the right type and number
    @Test
    public void resourceFromMarketTest() throws OutOfBoundException, RegularityError {
        market1.getResourcesFromMarket(true,0,warehouse1,papalPath1);
        market1.getResourcesFromMarket(false,1,warehouse1,papalPath1);
        assertEquals(warehouse1.returnTypeofDepot(1),"servant");
        assertEquals(warehouse1.returnTypeofDepot(2),"shield");
        assertEquals(warehouse1.returnTypeofDepot(3),"coin");
        assertEquals(1,warehouse1.returnLengthOfDepot(1));
        assertEquals(2,warehouse1.returnLengthOfDepot(2));
        assertEquals(3,warehouse1.returnLengthOfDepot(3));
    }
}
