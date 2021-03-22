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

    //Testing that all the allowed adds are correctly done
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

    //Testing that adding a 4th depot and deleting it is correctly programmed
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

    @Test
    public void testException2() throws RegularityError {
        CoinResource coin1 = new CoinResource();
        ShieldResource shield1 = new ShieldResource();
        CoinResource coin2 = new CoinResource();

        warehouse.addResource(coin1);
        warehouse.addResource(shield1);
        warehouse.addResource(coin2);

        warehouse.swapResources();
        assertEquals("null",warehouse.returnTypeofDepot(1));
        assertEquals("shield",warehouse.returnTypeofDepot(2));
        assertEquals("coin",warehouse.returnTypeofDepot(3));
        assertEquals(0,warehouse.returnLengthOfDepot(1));
        assertEquals(1,warehouse.returnLengthOfDepot(2));
        assertEquals(2,warehouse.returnLengthOfDepot(3));

        ShieldResource shield2 = new ShieldResource();
        StoneResource stone1 = new StoneResource();
        ServantResource servant1 = new ServantResource();
        ServantResource servant2 = new ServantResource();
        warehouse.addResource(stone1);
        warehouse.addResource(servant1);
        warehouse.addResource(servant2);
        assertEquals("stone",warehouse.returnTypeofDepot(1));
        assertEquals("shield",warehouse.returnTypeofDepot(2));
        assertEquals("coin",warehouse.returnTypeofDepot(3));
        assertEquals(1,warehouse.returnLengthOfDepot(1));
        assertEquals(1,warehouse.returnLengthOfDepot(2));
        assertEquals(2,warehouse.returnLengthOfDepot(3));
        assertEquals(2,warehouse.returnLengthOfDepot(4));
        warehouse.swapResources();
        assertEquals("stone",warehouse.returnTypeofDepot(1));
        assertEquals("shield",warehouse.returnTypeofDepot(2));
        assertEquals("coin",warehouse.returnTypeofDepot(3));
        assertEquals(1,warehouse.returnLengthOfDepot(1));
        assertEquals(1,warehouse.returnLengthOfDepot(2));
        assertEquals(2,warehouse.returnLengthOfDepot(3));
        assertEquals(2,warehouse.returnLengthOfDepot(4));
        assertTrue(warehouse.getListWithIndex(1).get(0).getIsNew());
        assertTrue(warehouse.getListWithIndex(4).get(0).getIsNew());
        assertTrue(warehouse.getListWithIndex(4).get(1).getIsNew());
        assertFalse(warehouse.getListWithIndex(2).get(0).getIsNew());
        assertFalse(warehouse.getListWithIndex(3).get(0).getIsNew());
        assertFalse(warehouse.getListWithIndex(3).get(1).getIsNew());

        int removedResources = warehouse.removeExceedingDepot(2);
        assertEquals("stone",warehouse.returnTypeofDepot(1));
        assertEquals("shield",warehouse.returnTypeofDepot(2));
        assertEquals("coin",warehouse.returnTypeofDepot(3));
        assertEquals(1,warehouse.returnLengthOfDepot(1));
        assertEquals(1,warehouse.returnLengthOfDepot(2));
        assertEquals(2,warehouse.returnLengthOfDepot(3));
        assertEquals(2,warehouse.returnLengthOfDepot(4));
        assertTrue(warehouse.getListWithIndex(1).get(0).getIsNew());
        assertTrue(warehouse.getListWithIndex(4).get(0).getIsNew());
        assertTrue(warehouse.getListWithIndex(4).get(1).getIsNew());
        assertFalse(warehouse.getListWithIndex(2).get(0).getIsNew());
        assertFalse(warehouse.getListWithIndex(3).get(0).getIsNew());
        assertFalse(warehouse.getListWithIndex(3).get(1).getIsNew());

        StoneResource stone2 = new StoneResource();
        warehouse.addResource(stone2);
        removedResources = warehouse.removeExceedingDepot(1);
        assertEquals(2,removedResources);
        assertEquals("shield",warehouse.returnTypeofDepot(1));
        assertEquals("servant",warehouse.returnTypeofDepot(2));
        assertEquals("coin",warehouse.returnTypeofDepot(3));
        assertEquals(1,warehouse.returnLengthOfDepot(1));
        assertEquals(2,warehouse.returnLengthOfDepot(2));
        assertEquals(2,warehouse.returnLengthOfDepot(3));
        assertEquals(0,warehouse.returnLengthOfDepot(4));
        assertFalse(warehouse.getListWithIndex(1).get(0).getIsNew());
        assertFalse(warehouse.getListWithIndex(2).get(0).getIsNew());
        assertFalse(warehouse.getListWithIndex(2).get(1).getIsNew());
        assertFalse(warehouse.getListWithIndex(3).get(0).getIsNew());
        assertFalse(warehouse.getListWithIndex(3).get(1).getIsNew());
    }

    @Test
    public void testRemove2ndDepot() throws RegularityError {
        CoinResource coin1 = new CoinResource();
        StoneResource stone1 = new StoneResource();
        ShieldResource shield1 = new ShieldResource();
        CoinResource coin2 = new CoinResource();
        ShieldResource shield2 = new ShieldResource();
        StoneResource stone2 = new StoneResource();

        warehouse.addResource(coin1);
        warehouse.addResource(shield1);
        warehouse.addResource(stone1);
        warehouse.swapResources();
        assertEquals("stone",warehouse.returnTypeofDepot(1));
        assertEquals("shield",warehouse.returnTypeofDepot(2));
        assertEquals("coin",warehouse.returnTypeofDepot(3));
        assertEquals(1,warehouse.returnLengthOfDepot(1));
        assertEquals(1,warehouse.returnLengthOfDepot(2));
        assertEquals(1,warehouse.returnLengthOfDepot(3));
        assertEquals(0,warehouse.returnLengthOfDepot(4));
        assertFalse(warehouse.getListWithIndex(1).get(0).getIsNew());
        assertFalse(warehouse.getListWithIndex(2).get(0).getIsNew());
        assertFalse(warehouse.getListWithIndex(3).get(0).getIsNew());

        warehouse.addResource(coin2);
        warehouse.addResource(shield2);
        warehouse.addResource(stone2);
        warehouse.swapResources();
        assertEquals("stone",warehouse.returnTypeofDepot(1));
        assertEquals("shield",warehouse.returnTypeofDepot(2));
        assertEquals("coin",warehouse.returnTypeofDepot(3));
        assertEquals(2,warehouse.returnLengthOfDepot(1));
        assertEquals(2,warehouse.returnLengthOfDepot(2));
        assertEquals(2,warehouse.returnLengthOfDepot(3));
        assertEquals(0,warehouse.returnLengthOfDepot(4));
        assertFalse(warehouse.getListWithIndex(1).get(0).getIsNew());
        assertFalse(warehouse.getListWithIndex(2).get(0).getIsNew());
        assertFalse(warehouse.getListWithIndex(3).get(0).getIsNew());
        assertTrue(warehouse.getListWithIndex(1).get(1).getIsNew());
        assertTrue(warehouse.getListWithIndex(2).get(1).getIsNew());
        assertTrue(warehouse.getListWithIndex(3).get(1).getIsNew());

        warehouse.removeResource(3);
        assertEquals("coin",warehouse.returnTypeofDepot(1));
        assertEquals("stone",warehouse.returnTypeofDepot(2));
        assertEquals("shield",warehouse.returnTypeofDepot(3));
        assertEquals(1,warehouse.returnLengthOfDepot(1));
        assertEquals(2,warehouse.returnLengthOfDepot(2));
        assertEquals(2,warehouse.returnLengthOfDepot(3));
        assertEquals(0,warehouse.returnLengthOfDepot(4));
        assertFalse(warehouse.getListWithIndex(1).get(0).getIsNew());
        assertFalse(warehouse.getListWithIndex(2).get(0).getIsNew());
        assertFalse(warehouse.getListWithIndex(3).get(0).getIsNew());
        assertFalse(warehouse.getListWithIndex(2).get(1).getIsNew());
        assertFalse(warehouse.getListWithIndex(3).get(1).getIsNew());
    }

    @Test
    public void testDefinitiveAllIn() throws RegularityError {
        CoinResource coin1 = new CoinResource();
        StoneResource stone1 = new StoneResource();
        ShieldResource shield1 = new ShieldResource();
        CoinResource coin2 = new CoinResource();
        ShieldResource shield2 = new ShieldResource();
        ShieldResource shield3 = new ShieldResource();
        warehouse.addResource(coin1);
        warehouse.addResource(shield1);
        warehouse.addResource(stone1);
        warehouse.addResource(coin2);
        warehouse.addResource(shield2);
        warehouse.addResource(shield3);
        warehouse.swapResources();
        assertEquals("stone",warehouse.returnTypeofDepot(1));
        assertEquals("coin",warehouse.returnTypeofDepot(2));
        assertEquals("shield",warehouse.returnTypeofDepot(3));
        assertEquals(1,warehouse.returnLengthOfDepot(1));
        assertEquals(2,warehouse.returnLengthOfDepot(2));
        assertEquals(3,warehouse.returnLengthOfDepot(3));
        assertEquals(0,warehouse.returnLengthOfDepot(4));
        assertFalse(warehouse.getListWithIndex(1).get(0).getIsNew());
        assertFalse(warehouse.getListWithIndex(2).get(0).getIsNew());
        assertFalse(warehouse.getListWithIndex(3).get(0).getIsNew());
        assertFalse(warehouse.getListWithIndex(2).get(1).getIsNew());
        assertFalse(warehouse.getListWithIndex(3).get(1).getIsNew());

        CoinResource coin3 = new CoinResource();
        StoneResource stone2 = new StoneResource();
        ShieldResource shield4 = new ShieldResource();
        ServantResource servant1 = new ServantResource();
        warehouse.addResource(coin3);
        warehouse.addResource(shield4);
        warehouse.addResource(stone2);
        warehouse.addResource(servant1);
        warehouse.swapResources();

        warehouse.removeExceedingDepot(4);
        warehouse.removeResource(3);
        warehouse.removeResource(2);
        assertEquals("stone",warehouse.returnTypeofDepot(1));
        assertEquals("coin",warehouse.returnTypeofDepot(2));
        assertEquals("shield",warehouse.returnTypeofDepot(3));
        assertEquals(2,warehouse.returnLengthOfDepot(1));
        assertEquals(2,warehouse.returnLengthOfDepot(2));
        assertEquals(3,warehouse.returnLengthOfDepot(3));
        assertEquals(0,warehouse.returnLengthOfDepot(4));
        warehouse.removeResource(1);
        assertEquals("stone",warehouse.returnTypeofDepot(1));
        assertEquals("coin",warehouse.returnTypeofDepot(2));
        assertEquals("shield",warehouse.returnTypeofDepot(3));
        assertEquals(1,warehouse.returnLengthOfDepot(1));
        assertEquals(2,warehouse.returnLengthOfDepot(2));
        assertEquals(3,warehouse.returnLengthOfDepot(3));
        warehouse.removeResource(1);
    }
}
