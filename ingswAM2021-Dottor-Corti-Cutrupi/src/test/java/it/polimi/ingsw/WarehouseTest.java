package it.polimi.ingsw;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.exception.warehouseErrors.*;
import it.polimi.ingsw.model.storing.Warehouse;
import it.polimi.ingsw.model.resource.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class WarehouseTest {
    Warehouse warehouse = new Warehouse();

    /**
     * After creating a warehouse, all the lists are empty
     */
    @Test
    public void testCorrectCreation(){
        assertEquals(null,warehouse.returnTypeofDepot(1));
        assertEquals(null,warehouse.returnTypeofDepot(2));
        assertEquals(null,warehouse.returnTypeofDepot(3));
    }

    /**
     * Testing that all the allowed adds are correctly done
     * @throws WarehouseDepotsRegularityError is never thrown as long as we do allowed adds
     */
    @Test
    public void testCorrectResourceAdd() throws WarehouseDepotsRegularityError {
        CoinResource coin1 = new CoinResource();
        StoneResource stone1 = new StoneResource();
        ShieldResource shield1 = new ShieldResource();
        CoinResource coin2 = new CoinResource();
        ShieldResource shield2 = new ShieldResource();
        StoneResource stone2 = new StoneResource();

        warehouse.addResource(coin1);
        assertEquals(null,warehouse.returnTypeofDepot(1));
        assertEquals(null,warehouse.returnTypeofDepot(2));
        assertEquals(ResourceType.Coin,warehouse.returnTypeofDepot(3));

        warehouse.addResource(stone1);
        assertEquals(null,warehouse.returnTypeofDepot(1));
        assertEquals(ResourceType.Stone,warehouse.returnTypeofDepot(2));
        assertEquals(ResourceType.Coin,warehouse.returnTypeofDepot(3));

        warehouse.addResource(shield1);
        assertEquals(ResourceType.Shield,warehouse.returnTypeofDepot(1));
        assertEquals(ResourceType.Stone,warehouse.returnTypeofDepot(2));
        assertEquals(ResourceType.Coin,warehouse.returnTypeofDepot(3));

        warehouse.addResource(coin2);
        assertEquals(1,warehouse.returnLengthOfDepot(1));
        assertEquals(1,warehouse.returnLengthOfDepot(2));
        assertEquals(2,warehouse.returnLengthOfDepot(3));

        warehouse.addResource(shield2);
        warehouse.swapResources();
        assertEquals(ResourceType.Stone,warehouse.returnTypeofDepot(1));
        assertEquals(ResourceType.Shield,warehouse.returnTypeofDepot(2));
        assertEquals(ResourceType.Coin,warehouse.returnTypeofDepot(3));
        assertEquals(1,warehouse.returnLengthOfDepot(1));
        assertEquals(2,warehouse.returnLengthOfDepot(2));
        assertEquals(2,warehouse.returnLengthOfDepot(3));
    }

    /**
     * Testing that adding a 4th depot and deleting it is correctly programmed
     * @throws WarehouseDepotsRegularityError once you add a fourth type of resource
     */
    @Test
    public void testVariousException() throws WarehouseDepotsRegularityError {
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


        Exception exception = assertThrows(TooManyResourcesInADepot.class, () -> {
            warehouse.swapResources();
        });
        assertTrue(warehouse.getListWithIndex(1).get(1).getIsNew());
        assertEquals(ResourceType.Stone,warehouse.returnTypeofDepot(1));
        assertEquals(ResourceType.Shield,warehouse.returnTypeofDepot(2));
        assertEquals(ResourceType.Coin,warehouse.returnTypeofDepot(3));
        assertEquals(2,warehouse.returnLengthOfDepot(1));
        assertEquals(2,warehouse.returnLengthOfDepot(2));
        assertEquals(2,warehouse.returnLengthOfDepot(3));

        warehouse.removeResource(1);
        assertEquals(ResourceType.Stone,warehouse.returnTypeofDepot(1));
        assertEquals(ResourceType.Shield,warehouse.returnTypeofDepot(2));
        assertEquals(ResourceType.Coin,warehouse.returnTypeofDepot(3));
        assertEquals(1,warehouse.returnLengthOfDepot(1));
        assertEquals(2,warehouse.returnLengthOfDepot(2));
        assertEquals(2,warehouse.returnLengthOfDepot(3));

        ServantResource servant1 = new ServantResource();
        warehouse.addResource(servant1);

        Exception exception1 = assertThrows(FourthDepotWarehouseError.class, () -> {
            warehouse.swapResources();
        });
        assertEquals(ResourceType.Stone,warehouse.returnTypeofDepot(1));
        assertEquals(ResourceType.Shield,warehouse.returnTypeofDepot(2));
        assertEquals(ResourceType.Coin,warehouse.returnTypeofDepot(3));
        assertEquals(ResourceType.Servant,warehouse.returnTypeofDepot(4));
        assertEquals(1,warehouse.returnLengthOfDepot(1));
        assertEquals(2,warehouse.returnLengthOfDepot(2));
        assertEquals(2,warehouse.returnLengthOfDepot(3));
        assertEquals(1,warehouse.returnLengthOfDepot(4));

        assertEquals(4,warehouse.sizeOfWarehouse());
        warehouse.removeExceedingDepot(4);
        assertEquals(3,warehouse.sizeOfWarehouse());
        assertEquals(ResourceType.Stone,warehouse.returnTypeofDepot(1));
        assertEquals(ResourceType.Shield,warehouse.returnTypeofDepot(2));
        assertEquals(ResourceType.Coin,warehouse.returnTypeofDepot(3));
        assertEquals(1,warehouse.returnLengthOfDepot(1));
        assertEquals(2,warehouse.returnLengthOfDepot(2));
        assertEquals(2,warehouse.returnLengthOfDepot(3));
    }

    /**
     * Testing that everything works fine when mixing allowed adds with not allowed ones
     * @throws WarehouseDepotsRegularityError when there is a 4th depot or there are too many resources in a depot
     */
    @Test
    public void testException2() throws WarehouseDepotsRegularityError {
        CoinResource coin1 = new CoinResource();
        ShieldResource shield1 = new ShieldResource();
        CoinResource coin2 = new CoinResource();

        warehouse.addResource(coin1);
        warehouse.addResource(shield1);
        warehouse.addResource(coin2);

        warehouse.swapResources();
        assertEquals(null,warehouse.returnTypeofDepot(1));
        assertEquals(ResourceType.Shield,warehouse.returnTypeofDepot(2));
        assertEquals(ResourceType.Coin,warehouse.returnTypeofDepot(3));
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
        assertEquals(ResourceType.Stone,warehouse.returnTypeofDepot(1));
        assertEquals(ResourceType.Shield,warehouse.returnTypeofDepot(2));
        assertEquals(ResourceType.Coin,warehouse.returnTypeofDepot(3));
        assertEquals(1,warehouse.returnLengthOfDepot(1));
        assertEquals(1,warehouse.returnLengthOfDepot(2));
        assertEquals(2,warehouse.returnLengthOfDepot(3));
        assertEquals(2,warehouse.returnLengthOfDepot(4));

        Exception exception = assertThrows(FourthDepotWarehouseError.class, () -> {
            warehouse.swapResources();
        });
        //warehouse.swapResources();
        assertEquals(ResourceType.Stone,warehouse.returnTypeofDepot(1));
        assertEquals(ResourceType.Shield,warehouse.returnTypeofDepot(2));
        assertEquals(ResourceType.Coin,warehouse.returnTypeofDepot(3));
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

        Exception exception1 = assertThrows(NotAllNewResourcesInDepotError.class, () -> {
            warehouse.removeExceedingDepot(2);
        });
        assertEquals(ResourceType.Stone,warehouse.returnTypeofDepot(1));
        assertEquals(ResourceType.Shield,warehouse.returnTypeofDepot(2));
        assertEquals(ResourceType.Coin,warehouse.returnTypeofDepot(3));
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
        int removedResources = warehouse.removeExceedingDepot(1);
        assertEquals(2,removedResources);
        warehouse.swapResources();
        assertEquals(ResourceType.Shield,warehouse.returnTypeofDepot(1));
        assertEquals(ResourceType.Servant,warehouse.returnTypeofDepot(2));
        assertEquals(ResourceType.Coin,warehouse.returnTypeofDepot(3));
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

    /**
     *
     * @throws TooManyResourcesInADepot when you have too many resources in a depot
     */
    @Test
    public void testRemove2ndDepot() throws WarehouseDepotsRegularityError {
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
        assertEquals(ResourceType.Stone,warehouse.returnTypeofDepot(1));
        assertEquals(ResourceType.Shield,warehouse.returnTypeofDepot(2));
        assertEquals(ResourceType.Coin,warehouse.returnTypeofDepot(3));
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

        Exception exception = assertThrows(TooManyResourcesInADepot.class, () -> {
            warehouse.swapResources();
        });
        assertEquals(ResourceType.Stone,warehouse.returnTypeofDepot(1));
        assertEquals(ResourceType.Shield,warehouse.returnTypeofDepot(2));
        assertEquals(ResourceType.Coin,warehouse.returnTypeofDepot(3));
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
        warehouse.swapResources();
        assertEquals(ResourceType.Coin,warehouse.returnTypeofDepot(1));
        assertEquals(ResourceType.Stone,warehouse.returnTypeofDepot(2));
        assertEquals(ResourceType.Shield,warehouse.returnTypeofDepot(3));
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

    /**
     * Basically testing that once you mix all the possible moves everything works
     * @throws WarehouseDepotsRegularityError
     */
    @Test
    public void testDefinitiveAllIn() throws WarehouseDepotsRegularityError {
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
        assertEquals(ResourceType.Stone,warehouse.returnTypeofDepot(1));
        assertEquals(ResourceType.Coin,warehouse.returnTypeofDepot(2));
        assertEquals(ResourceType.Shield,warehouse.returnTypeofDepot(3));
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
        Exception exception = assertThrows(FourthDepotWarehouseError.class, () -> {
            warehouse.swapResources();
        });

        warehouse.removeExceedingDepot(4);
        warehouse.removeResource(3);
        warehouse.removeResource(2);
        assertEquals(ResourceType.Stone,warehouse.returnTypeofDepot(1));
        assertEquals(ResourceType.Coin,warehouse.returnTypeofDepot(2));
        assertEquals(ResourceType.Shield,warehouse.returnTypeofDepot(3));
        assertEquals(2,warehouse.returnLengthOfDepot(1));
        assertEquals(2,warehouse.returnLengthOfDepot(2));
        assertEquals(3,warehouse.returnLengthOfDepot(3));
        assertEquals(0,warehouse.returnLengthOfDepot(4));
        warehouse.removeResource(1);
        assertEquals(ResourceType.Stone,warehouse.returnTypeofDepot(1));
        assertEquals(ResourceType.Coin,warehouse.returnTypeofDepot(2));
        assertEquals(ResourceType.Shield,warehouse.returnTypeofDepot(3));
        assertEquals(1,warehouse.returnLengthOfDepot(1));
        assertEquals(2,warehouse.returnLengthOfDepot(2));
        assertEquals(3,warehouse.returnLengthOfDepot(3));
        Exception exception2 = assertThrows(LastElementOfDepotNotNewError.class, () -> {
            warehouse.removeResource(1);;
        });
    }

    /**
     * testing that removing resources giving also resources as type works
     * @throws WarehouseDepotsRegularityError
     */
    @Test
    public void testingRemovalWithResourceAsParameter() throws WarehouseDepotsRegularityError {
        CoinResource coin = new CoinResource();
        ShieldResource shield = new ShieldResource();
        StoneResource stone = new StoneResource();
        ServantResource servant = new ServantResource();
        warehouse.addResource(coin);
        warehouse.addResource(coin);
        warehouse.addResource(coin);
        warehouse.addResource(shield);
        warehouse.addResource(shield);
        warehouse.addResource(stone);
        assertEquals(ResourceType.Stone,warehouse.returnTypeofDepot(1));
        assertEquals(ResourceType.Shield,warehouse.returnTypeofDepot(2));
        assertEquals(ResourceType.Coin,warehouse.returnTypeofDepot(3));
        assertEquals(1,warehouse.returnLengthOfDepot(1));
        assertEquals(2,warehouse.returnLengthOfDepot(2));
        assertEquals(3,warehouse.returnLengthOfDepot(3));
        //testing that removing 2 of the 3 coins of the depot is correct
        assertEquals(2,warehouse.removeResource(coin,2));
        warehouse.swapResources();
        assertEquals(ResourceType.Stone,warehouse.returnTypeofDepot(1));
        assertEquals(ResourceType.Coin,warehouse.returnTypeofDepot(2));
        assertEquals(ResourceType.Shield,warehouse.returnTypeofDepot(3));
        assertEquals(1,warehouse.returnLengthOfDepot(1));
        assertEquals(1,warehouse.returnLengthOfDepot(2));
        assertEquals(2,warehouse.returnLengthOfDepot(3));
        //Testing that removes all the resources in the depot if you ask to remove more than it has, and returns old depot size
        assertEquals(1,warehouse.removeResource(coin,2));
        warehouse.swapResources();
        assertEquals(null,warehouse.returnTypeofDepot(1));
        assertEquals(ResourceType.Stone,warehouse.returnTypeofDepot(2));
        assertEquals(ResourceType.Shield,warehouse.returnTypeofDepot(3));
        assertEquals(0,warehouse.returnLengthOfDepot(1));
        assertEquals(1,warehouse.returnLengthOfDepot(2));
        assertEquals(2,warehouse.returnLengthOfDepot(3));
        //Testing that removing the exact amount contained in the depot is correct
        assertEquals(2,warehouse.removeResource(shield,2));
        warehouse.swapResources();
        assertEquals(null,warehouse.returnTypeofDepot(1));
        assertEquals(null,warehouse.returnTypeofDepot(2));
        assertEquals(ResourceType.Stone,warehouse.returnTypeofDepot(3));
        assertEquals(0,warehouse.returnLengthOfDepot(1));
        assertEquals(0,warehouse.returnLengthOfDepot(2));
        assertEquals(1,warehouse.returnLengthOfDepot(3));
        //Last test similar to the latest ones
        assertEquals(1,warehouse.removeResource(stone,2));
        warehouse.swapResources();
        assertEquals(null,warehouse.returnTypeofDepot(1));
        assertEquals(null,warehouse.returnTypeofDepot(2));
        assertEquals(null,warehouse.returnTypeofDepot(3));
        assertEquals(0,warehouse.returnLengthOfDepot(1));
        assertEquals(0,warehouse.returnLengthOfDepot(2));
        assertEquals(0,warehouse.returnLengthOfDepot(3));

        assertEquals(0,warehouse.removeResource(stone,2));
        warehouse.swapResources();
        assertEquals(null,warehouse.returnTypeofDepot(1));
        assertEquals(null,warehouse.returnTypeofDepot(2));
        assertEquals(null,warehouse.returnTypeofDepot(3));
        assertEquals(0,warehouse.returnLengthOfDepot(1));
        assertEquals(0,warehouse.returnLengthOfDepot(2));
        assertEquals(0,warehouse.returnLengthOfDepot(3));
    }

    /**
     * Testing that the method that removes a resource given its type works
     */
    @Test
    public void TestingRemovalGivenAResourceType(){
        CoinResource coin = new CoinResource();
        ShieldResource shield = new ShieldResource();
        StoneResource stone = new StoneResource();
        ServantResource servant = new ServantResource();
        warehouse.addResource(coin);
        warehouse.addResource(coin);
        warehouse.addResource(coin);
        warehouse.addResource(shield);
        warehouse.addResource(shield);
        warehouse.addResource(stone);

        warehouse.removeResource(ResourceType.Coin);
        warehouse.removeResource(ResourceType.Shield);
        warehouse.removeResource(ResourceType.Stone);
        assertEquals(null,warehouse.returnTypeofDepot(1));
        assertEquals(ResourceType.Shield,warehouse.returnTypeofDepot(2));
        assertEquals(ResourceType.Coin,warehouse.returnTypeofDepot(3));
        assertEquals(0,warehouse.returnLengthOfDepot(1));
        assertEquals(1,warehouse.returnLengthOfDepot(2));
        assertEquals(2,warehouse.returnLengthOfDepot(3));

        warehouse.addResource(new ServantResource());
        assertEquals(ResourceType.Servant,warehouse.returnTypeofDepot(1));
        assertEquals(ResourceType.Shield,warehouse.returnTypeofDepot(2));
        assertEquals(ResourceType.Coin,warehouse.returnTypeofDepot(3));
        assertEquals(1,warehouse.returnLengthOfDepot(1));
        assertEquals(1,warehouse.returnLengthOfDepot(2));
        assertEquals(2,warehouse.returnLengthOfDepot(3));

        warehouse.removeResource(ResourceType.Servant);
        assertEquals(null,warehouse.returnTypeofDepot(1));
        assertEquals(ResourceType.Shield,warehouse.returnTypeofDepot(2));
        assertEquals(ResourceType.Coin,warehouse.returnTypeofDepot(3));
        assertEquals(0,warehouse.returnLengthOfDepot(1));
        assertEquals(1,warehouse.returnLengthOfDepot(2));
        assertEquals(2,warehouse.returnLengthOfDepot(3));
    }

    /**
     * Testing that the method that count the number of a given resource is good
     */
    @Test
    public void TestingAmountOfResource(){
        CoinResource coin = new CoinResource();
        ShieldResource shield = new ShieldResource();
        StoneResource stone = new StoneResource();
        ServantResource servant = new ServantResource();
        warehouse.addResource(coin);
        warehouse.addResource(coin);
        warehouse.addResource(coin);
        warehouse.addResource(shield);
        warehouse.addResource(shield);
        warehouse.addResource(stone);

        assertEquals(3,warehouse.amountOfResource(new CoinResource()));
        assertEquals(2,warehouse.amountOfResource(new ShieldResource()));
        assertEquals(1,warehouse.amountOfResource(new StoneResource()));
        assertEquals(0,warehouse.amountOfResource(new ServantResource()));
    }

    /**
     * Testing that the mthod which returns the list of the resources in the warehouse works
     */
    @Test
    public void TestingGetAllResourcesMethod(){
        CoinResource coin = new CoinResource();
        ShieldResource shield = new ShieldResource();
        StoneResource stone = new StoneResource();
        ServantResource servant = new ServantResource();
        warehouse.addResource(coin);
        warehouse.addResource(coin);
        warehouse.addResource(coin);
        warehouse.addResource(shield);
        warehouse.addResource(shield);
        warehouse.addResource(stone);

        ArrayList<Resource> list = new ArrayList<Resource>();
        list.add(stone);
        list.add(shield);
        list.add(shield);
        list.add(coin);
        list.add(coin);
        list.add(coin);

        assertEquals(list, warehouse.getAllResources());
    }

    /**
     * testing that the method counting the effective size of the warehouse works
     */
    @Test
    public void TestingRealSizeOfWarehouse(){
        assertEquals(0,warehouse.realSizeOfWarehouse());

        CoinResource coin = new CoinResource();
        ShieldResource shield = new ShieldResource();
        StoneResource stone = new StoneResource();
        ServantResource servant = new ServantResource();
        warehouse.addResource(coin);
        warehouse.addResource(coin);
        warehouse.addResource(coin);

        assertEquals(1,warehouse.realSizeOfWarehouse());
        warehouse.addResource(shield);
        warehouse.addResource(shield);
        assertEquals(2,warehouse.realSizeOfWarehouse());
        warehouse.addResource(stone);
        assertEquals(3,warehouse.realSizeOfWarehouse());
        warehouse.addResource(servant);
        assertEquals(4,warehouse.realSizeOfWarehouse());
    }
}
