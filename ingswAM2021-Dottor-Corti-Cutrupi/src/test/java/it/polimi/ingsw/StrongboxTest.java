package it.polimi.ingsw;

import it.polimi.ingsw.resource.CoinResource;
import it.polimi.ingsw.resource.ServantResource;
import it.polimi.ingsw.resource.ShieldResource;
import it.polimi.ingsw.resource.StoneResource;
import it.polimi.ingsw.storing.Strongbox;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StrongboxTest {
    private Strongbox strongbox = new Strongbox();

    @Test
    public void testAdd1(){
        CoinResource coin1 = new CoinResource();
        ShieldResource shield1 = new ShieldResource();
        StoneResource stone1 = new StoneResource();
        ServantResource servant1 = new ServantResource();
        CoinResource coin2 = new CoinResource();

        strongbox.addResource(coin1);
        assertEquals(1,strongbox.lengthOfStrongbox());
        assertEquals(1,strongbox.depotOfStrongboxLength(coin1));
        assertEquals(0,strongbox.depotOfStrongboxLength(servant1));

        strongbox.addResource(servant1);
        strongbox.addResource(stone1);
        strongbox.addResource(shield1);
        assertEquals(4,strongbox.lengthOfStrongbox());
        assertEquals(1,strongbox.depotOfStrongboxLength(coin1));
        assertEquals(1,strongbox.depotOfStrongboxLength(servant1));
        assertEquals(1,strongbox.depotOfStrongboxLength(stone1));
        assertEquals(1,strongbox.depotOfStrongboxLength(shield1));

        strongbox.addResource(coin2);
        assertEquals(4,strongbox.lengthOfStrongbox());
        assertEquals(2,strongbox.depotOfStrongboxLength(coin1));
        assertEquals(1,strongbox.depotOfStrongboxLength(servant1));
        assertEquals(1,strongbox.depotOfStrongboxLength(stone1));
        assertEquals(1,strongbox.depotOfStrongboxLength(shield1));

        CoinResource coin3 = new CoinResource();
        CoinResource coin4 = new CoinResource();
        CoinResource coin5 = new CoinResource();
        CoinResource coin6 = new CoinResource();
        strongbox.addResource(coin3);
        strongbox.addResource(coin4);
        strongbox.addResource(coin5);
        strongbox.addResource(coin6);
        assertEquals(4,strongbox.lengthOfStrongbox());
        assertEquals(6,strongbox.depotOfStrongboxLength(coin1));
        assertEquals(1,strongbox.depotOfStrongboxLength(servant1));
        assertEquals(1,strongbox.depotOfStrongboxLength(stone1));
        assertEquals(1,strongbox.depotOfStrongboxLength(shield1));
    }

    @Test
    public void testRemove1(){
        CoinResource coin1 = new CoinResource();
        ShieldResource shield1 = new ShieldResource();
        StoneResource stone1 = new StoneResource();
        ServantResource servant1 = new ServantResource();
        CoinResource coin2 = new CoinResource();
        strongbox.addResource(coin1);
        strongbox.addResource(servant1);
        strongbox.addResource(stone1);
        strongbox.addResource(shield1);
        strongbox.addResource(coin2);
        CoinResource coin3 = new CoinResource();
        CoinResource coin4 = new CoinResource();
        CoinResource coin5 = new CoinResource();
        CoinResource coin6 = new CoinResource();
        strongbox.addResource(coin3);
        strongbox.addResource(coin4);
        strongbox.addResource(coin5);
        strongbox.addResource(coin6);
        assertEquals(4,strongbox.lengthOfStrongbox());
        assertEquals(6,strongbox.depotOfStrongboxLength(coin1));
        assertEquals(1,strongbox.depotOfStrongboxLength(servant1));
        assertEquals(1,strongbox.depotOfStrongboxLength(stone1));
        assertEquals(1,strongbox.depotOfStrongboxLength(shield1));

        strongbox.removeResource(servant1);
        assertEquals(3,strongbox.lengthOfStrongbox());
        assertEquals(6,strongbox.depotOfStrongboxLength(coin1));
        assertEquals(0,strongbox.depotOfStrongboxLength(servant1));
        assertEquals(1,strongbox.depotOfStrongboxLength(stone1));
        assertEquals(1,strongbox.depotOfStrongboxLength(shield1));

        strongbox.removeResourceWithAmount(stone1,1);
        assertEquals(2,strongbox.lengthOfStrongbox());
        assertEquals(6,strongbox.depotOfStrongboxLength(coin1));
        assertEquals(0,strongbox.depotOfStrongboxLength(servant1));
        assertEquals(0,strongbox.depotOfStrongboxLength(stone1));
        assertEquals(1,strongbox.depotOfStrongboxLength(shield1));

        strongbox.removeResourceWithAmount(coin1,3);
        assertEquals(2,strongbox.lengthOfStrongbox());
        assertEquals(3,strongbox.depotOfStrongboxLength(coin1));
        assertEquals(0,strongbox.depotOfStrongboxLength(servant1));
        assertEquals(0,strongbox.depotOfStrongboxLength(stone1));
        assertEquals(1,strongbox.depotOfStrongboxLength(shield1));

        strongbox.removeResourceWithAmount(coin1,3);
        assertEquals(1,strongbox.lengthOfStrongbox());
        assertEquals(0,strongbox.depotOfStrongboxLength(coin1));
        assertEquals(0,strongbox.depotOfStrongboxLength(servant1));
        assertEquals(0,strongbox.depotOfStrongboxLength(stone1));
        assertEquals(1,strongbox.depotOfStrongboxLength(shield1));
    }
}
