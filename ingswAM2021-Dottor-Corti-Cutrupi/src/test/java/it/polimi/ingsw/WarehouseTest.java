package it.polimi.ingsw;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.resource.CoinResource;
import it.polimi.ingsw.resource.ShieldResource;
import it.polimi.ingsw.resource.StoneResource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class WarehouseTest {
    Warehouse warehouse = new Warehouse();

    @Test
    public void testCorrectCreation(){
        assertEquals("null",warehouse.returnTypeofDepot(1));
        assertEquals("null",warehouse.returnTypeofDepot(2));
        assertEquals("null",warehouse.returnTypeofDepot(3));
    }

    @Test
    public void testCorrectResourceAdd(){
        CoinResource coin1 = new CoinResource();
        StoneResource stone1 = new StoneResource();
        ShieldResource shield1 = new ShieldResource();
        CoinResource coin2 = new CoinResource();

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
        assertEquals(1,warehouse.returnLenghtofDepot(1));
        assertEquals(1,warehouse.returnLenghtofDepot(2));
        assertEquals(2,warehouse.returnLenghtofDepot(3));
    }
}
