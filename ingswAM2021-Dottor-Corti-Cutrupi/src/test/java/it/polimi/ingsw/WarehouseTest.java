package it.polimi.ingsw;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.resource.CoinResource;
import it.polimi.ingsw.resource.ServantResource;
import it.polimi.ingsw.resource.ShieldResource;
import it.polimi.ingsw.resource.StoneResource;
import org.junit.jupiter.api.Test;

public class WarehouseTest {
    Warehouse warehouse = new Warehouse();

    //After creating a warehouse, all the lists are empty
    @Test
    public void testCorrectCreation(){
        assertEquals("null",warehouse.returnTypeofDepot(1));
        assertEquals("null",warehouse.returnTypeofDepot(2));
        assertEquals("null",warehouse.returnTypeofDepot(3));
    }

    //
    @Test
    public void testCorrectResourceAdd() throws RegularityError {
        CoinResource coin1 = new CoinResource();
        StoneResource stone1 = new StoneResource();
        ShieldResource shield1 = new ShieldResource();
        CoinResource coin2 = new CoinResource();
        ShieldResource shield2 = new ShieldResource();
        StoneResource stone2 = new StoneResource();

        warehouse.addResource(coin1);
        assertEquals("null",warehouse.returnTypeofDepot(1));
        assertEquals("null",warehouse.returnTypeofDepot(2));
        assertEquals("coin",warehouse.returnTypeofDepot(3));

        warehouse.addResource(stone1);
        assertEquals("null",warehouse.returnTypeofDepot(1));
        assertEquals("stone",warehouse.returnTypeofDepot(2));
        assertEquals("coin",warehouse.returnTypeofDepot(3));

        warehouse.addResource(shield1);
        assertEquals("shield",warehouse.returnTypeofDepot(1));
        assertEquals("stone",warehouse.returnTypeofDepot(2));
        assertEquals("coin",warehouse.returnTypeofDepot(3));

        warehouse.addResource(coin2);
        assertEquals(1,warehouse.returnLengthOfDepot(1));
        assertEquals(1,warehouse.returnLengthOfDepot(2));
        assertEquals(2,warehouse.returnLengthOfDepot(3));

        warehouse.addResource(shield2);
        warehouse.swapResources();
        assertEquals("stone",warehouse.returnTypeofDepot(1));
        assertEquals("shield",warehouse.returnTypeofDepot(2));
        assertEquals("coin",warehouse.returnTypeofDepot(3));
        assertEquals(1,warehouse.returnLengthOfDepot(1));
        assertEquals(2,warehouse.returnLengthOfDepot(2));
        assertEquals(2,warehouse.returnLengthOfDepot(3));
    }

    @Test
    public void testVariousException() throws RegularityError {
        CoinResource coin1 = new CoinResource();
        StoneResource stone1 = new StoneResource();
        ShieldResource shield1 = new ShieldResource();
        CoinResource coin2 = new CoinResource();
        ShieldResource shield2 = new ShieldResource();
        StoneResource stone2 = new StoneResource();

        warehouse.addResource(coin1);
        warehouse.addResource(stone1);
        warehouse.addResource(shield1);
        warehouse.addResource(coin2);
        warehouse.addResource(shield2);
        warehouse.swapResources();

        warehouse.addResource(stone2);
        assertTrue(warehouse.getListWithIndex(1).get(1).getIsNew());
        warehouse.swapResources();
        assertTrue(warehouse.getListWithIndex(1).get(1).getIsNew());
        assertEquals("stone",warehouse.returnTypeofDepot(1));
        assertEquals("shield",warehouse.returnTypeofDepot(2));
        assertEquals("coin",warehouse.returnTypeofDepot(3));
        assertEquals(2,warehouse.returnLengthOfDepot(1));
        assertEquals(2,warehouse.returnLengthOfDepot(2));
        assertEquals(2,warehouse.returnLengthOfDepot(3));

        warehouse.removeResource(1);
        assertEquals("stone",warehouse.returnTypeofDepot(1));
        assertEquals("shield",warehouse.returnTypeofDepot(2));
        assertEquals("coin",warehouse.returnTypeofDepot(3));
        assertEquals(1,warehouse.returnLengthOfDepot(1));
        assertEquals(2,warehouse.returnLengthOfDepot(2));
        assertEquals(2,warehouse.returnLengthOfDepot(3));

        ServantResource servant1 = new ServantResource();
        warehouse.addResource(servant1);
        warehouse.swapResources();
        assertEquals("stone",warehouse.returnTypeofDepot(1));
        assertEquals("shield",warehouse.returnTypeofDepot(2));
        assertEquals("coin",warehouse.returnTypeofDepot(3));
        assertEquals("servant",warehouse.returnTypeofDepot(4));
        assertEquals(1,warehouse.returnLengthOfDepot(1));
        assertEquals(2,warehouse.returnLengthOfDepot(2));
        assertEquals(2,warehouse.returnLengthOfDepot(3));
        assertEquals(1,warehouse.returnLengthOfDepot(4));

        assertEquals(4,warehouse.returnWarehouseSize());
        warehouse.removeExceedingDepot(4);
        assertEquals(3,warehouse.returnWarehouseSize());
        assertEquals("stone",warehouse.returnTypeofDepot(1));
        assertEquals("shield",warehouse.returnTypeofDepot(2));
        assertEquals("coin",warehouse.returnTypeofDepot(3));
        assertEquals(1,warehouse.returnLengthOfDepot(1));
        assertEquals(2,warehouse.returnLengthOfDepot(2));
        assertEquals(2,warehouse.returnLengthOfDepot(3));
    }
}
