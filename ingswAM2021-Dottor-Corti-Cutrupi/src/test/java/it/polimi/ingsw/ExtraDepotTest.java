package it.polimi.ingsw;


import it.polimi.ingsw.model.leadercard.leaderpowers.ExtraDeposit;
import it.polimi.ingsw.model.resource.*;
import it.polimi.ingsw.model.storing.ExtraDepot;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExtraDepotTest {
    /**
     * Testing that the creation of an extra depot works
     */
    @Test
    public void TestingCreation(){
        ArrayList<Resource> list = new ArrayList<>();
        list.add(new CoinResource());
        list.add(new CoinResource());
        ExtraDeposit extraDeposit = new ExtraDeposit(list);
        ExtraDepot extraDepot = new ExtraDepot(extraDeposit);

        assertEquals(ResourceType.Coin, extraDepot.getExtraDepotType());
        assertEquals(2,extraDepot.getSize());
        assertEquals(0,extraDepot.getAmountOfContainedResources());
        assertTrue(extraDepot.getAllResources().isEmpty());
    }

    /**
     * Testing that adding resources to the depot works
     */
    @Test
    public void TestingAdd(){
        ArrayList<Resource> list = new ArrayList<>();
        list.add(new CoinResource());
        list.add(new CoinResource());
        ExtraDeposit extraDeposit = new ExtraDeposit(list);
        ExtraDepot extraDepot = new ExtraDepot(extraDeposit);

        extraDepot.addResource();
        assertEquals(1,extraDepot.getAmountOfContainedResources());

        extraDepot.addResource(new CoinResource());
        assertEquals(2,extraDepot.getAmountOfContainedResources());
    }

    /**
     * Testing that removing resources works
     */
    @Test
    public void TestingRemoval(){
        ArrayList<Resource> list = new ArrayList<>();
        list.add(new CoinResource());
        list.add(new CoinResource());
        ExtraDeposit extraDeposit = new ExtraDeposit(list);
        ExtraDepot extraDepot = new ExtraDepot(extraDeposit);

        extraDepot.removeResource();
        assertEquals(0,extraDepot.getAmountOfContainedResources());

        extraDepot.addResource();
        extraDepot.addResource(new CoinResource());

        extraDepot.removeResource();
        assertEquals(1,extraDepot.getAmountOfContainedResources());
        extraDepot.removeResource();
        assertEquals(0,extraDepot.getAmountOfContainedResources());
    }
}
