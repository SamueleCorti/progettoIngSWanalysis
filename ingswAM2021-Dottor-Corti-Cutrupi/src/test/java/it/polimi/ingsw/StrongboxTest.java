package it.polimi.ingsw;

import it.polimi.ingsw.Model.resource.CoinResource;
import it.polimi.ingsw.Model.resource.ServantResource;
import it.polimi.ingsw.Model.resource.ShieldResource;
import it.polimi.ingsw.Model.resource.StoneResource;
import it.polimi.ingsw.Model.storing.Strongbox;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StrongboxTest {
    private Strongbox strongbox = new Strongbox();

    @Test
    public void testAdd1(){
        //testing that the initial lenght of strongbox is 0
        assertEquals(0,strongbox.sizeOfStrongbox());
        CoinResource coin1 = new CoinResource();
        //testing that there's no coin in the strongbox
        assertEquals(0,strongbox.amountOfResource(coin1));
        //testing that coin in successfully added
        strongbox.addResource(coin1);
        assertEquals(1,strongbox.sizeOfStrongbox());
        assertEquals(1,strongbox.amountOfResource(coin1));
        //testing that there's no servant in the strongbox
        ServantResource servant1 = new ServantResource();
        assertEquals(0,strongbox.amountOfResource(servant1));
        //Testing that coin and servant are correctly in the strongbox
        strongbox.addResource(servant1);
        CoinResource coin2 = new CoinResource();
        strongbox.addResource(coin2);
        assertEquals(2,strongbox.sizeOfStrongbox());
        assertEquals(2,strongbox.amountOfResource(coin1));
        assertEquals(1,strongbox.amountOfResource(servant1));
        //Another test adding some more resources
        StoneResource stone1 = new StoneResource();
        ShieldResource shield1 = new ShieldResource();
        strongbox.addResource(stone1);
        strongbox.addResource(shield1);
        assertEquals(4,strongbox.sizeOfStrongbox());
        assertEquals(2,strongbox.amountOfResource(coin1));
        assertEquals(1,strongbox.amountOfResource(servant1));
        assertEquals(1,strongbox.amountOfResource(shield1));
        assertEquals(1,strongbox.amountOfResource(stone1));
    }

    @Test
    public void testRemove1(){
        CoinResource coin1 = new CoinResource();
        strongbox.addResource(coin1);
        ServantResource servant1 = new ServantResource();
        strongbox.addResource(servant1);
        CoinResource coin2 = new CoinResource();
        strongbox.addResource(coin2);
        StoneResource stone1 = new StoneResource();
        ShieldResource shield1 = new ShieldResource();
        strongbox.addResource(stone1);
        strongbox.addResource(shield1);
        CoinResource coin3 = new CoinResource();
        strongbox.addResource(coin3);
        assertEquals(4,strongbox.sizeOfStrongbox());
        assertEquals(3,strongbox.amountOfResource(coin1));
        assertEquals(1,strongbox.amountOfResource(servant1));
        assertEquals(1,strongbox.amountOfResource(shield1));
        assertEquals(1,strongbox.amountOfResource(stone1));
        //Testing that removing the last element of a certain resource deletes they key in the map
        strongbox.removeResource(shield1);
        assertEquals(3,strongbox.sizeOfStrongbox());
        assertEquals(3,strongbox.amountOfResource(coin1));
        assertEquals(1,strongbox.amountOfResource(servant1));
        assertEquals(0,strongbox.amountOfResource(shield1));
        assertEquals(1,strongbox.amountOfResource(stone1));
        //Testing the correct removal of one of many resources of a certain type
        strongbox.removeResource(coin1);
        assertEquals(3,strongbox.sizeOfStrongbox());
        assertEquals(2,strongbox.amountOfResource(coin1));
        assertEquals(1,strongbox.amountOfResource(servant1));
        assertEquals(0,strongbox.amountOfResource(shield1));
        assertEquals(1,strongbox.amountOfResource(stone1));
        strongbox.addResource(coin1);
        strongbox.addResource(coin1);
        assertEquals(3,strongbox.sizeOfStrongbox());
        assertEquals(4,strongbox.amountOfResource(coin1));
        assertEquals(1,strongbox.amountOfResource(servant1));
        assertEquals(0,strongbox.amountOfResource(shield1));
        assertEquals(1,strongbox.amountOfResource(stone1));
        //Testing that deleting multiple resources but not all of that type is correct
        strongbox.removeResourceWithAmount(coin1,2);
        assertEquals(3,strongbox.sizeOfStrongbox());
        assertEquals(2,strongbox.amountOfResource(coin1));
        assertEquals(1,strongbox.amountOfResource(servant1));
        assertEquals(0,strongbox.amountOfResource(shield1));
        assertEquals(1,strongbox.amountOfResource(stone1));
        //Testing that removing all the resources of a certain type is correct
        strongbox.removeResourceWithAmount(coin1,2);
        assertEquals(2,strongbox.sizeOfStrongbox());
        assertEquals(0,strongbox.amountOfResource(coin1));
        assertEquals(1,strongbox.amountOfResource(servant1));
        assertEquals(0,strongbox.amountOfResource(shield1));
        assertEquals(1,strongbox.amountOfResource(stone1));
        strongbox.addResource(servant1);
        //Testing that removeWithAmount works even when number is one
        strongbox.removeResourceWithAmount(servant1,1);
        assertEquals(2,strongbox.sizeOfStrongbox());
        assertEquals(0,strongbox.amountOfResource(coin1));
        assertEquals(1,strongbox.amountOfResource(servant1));
        assertEquals(0,strongbox.amountOfResource(shield1));
        assertEquals(1,strongbox.amountOfResource(stone1));
        strongbox.removeResourceWithAmount(servant1,1);
        assertEquals(1,strongbox.sizeOfStrongbox());
        assertEquals(0,strongbox.amountOfResource(coin1));
        assertEquals(0,strongbox.amountOfResource(servant1));
        assertEquals(0,strongbox.amountOfResource(shield1));
        assertEquals(1,strongbox.amountOfResource(stone1));
    }
}
