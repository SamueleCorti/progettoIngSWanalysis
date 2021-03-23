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
    }
}
