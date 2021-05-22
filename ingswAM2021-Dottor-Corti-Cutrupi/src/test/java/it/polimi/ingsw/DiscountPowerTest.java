package it.polimi.ingsw;

import it.polimi.ingsw.model.boardsAndPlayer.Dashboard;
import it.polimi.ingsw.model.resource.CoinResource;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ServantResource;
import it.polimi.ingsw.model.resource.StoneResource;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class DiscountPowerTest {

    @Test
    public void availableResourcesForDevelopmentTest() throws FileNotFoundException {
        Resource stone= new StoneResource();
        Resource coin= new CoinResource();
        Dashboard dashboard= new Dashboard(1);
        ArrayList<Resource> array1 = new ArrayList<>();
        ArrayList<Resource> array2 = new ArrayList<>();
        array1.add(stone);
        array2.add(coin);
        dashboard.activateDiscountCard(array1);
        dashboard.activateDiscountCard(array2);
        dashboard.getStrongbox().addResource(new StoneResource());
        dashboard.getStrongbox().addResource(new StoneResource());
        dashboard.getWarehouse().addResource(new StoneResource());
        dashboard.getStrongbox().addResource(new ServantResource());
        dashboard.getWarehouse().addResource(new ServantResource());
        dashboard.getWarehouse().addResource(new CoinResource());
        assertEquals(1,dashboard.getWarehouse().amountOfResource(stone));
        assertEquals(2,dashboard.getStrongbox().amountOfResource(stone));
        assertEquals(4,dashboard.availableResourcesForDevelopment(stone));
        assertEquals(dashboard.availableResourcesForProduction(stone),3);
        assertEquals(dashboard.availableResourcesForProduction(coin),1);
        assertEquals(dashboard.availableResourcesForDevelopment(coin),1);
        assertEquals(2,dashboard.availableResourcesForProduction(new ServantResource()));
        assertEquals(3,dashboard.availableResourcesForDevelopment(new ServantResource()));
        dashboard.removeResourcesFromDashboard(2, new ServantResource());
        assertEquals(0,dashboard.availableResourcesForProduction(new ServantResource()));
        assertEquals(1,dashboard.availableResourcesForDevelopment(new ServantResource()));
    }



}
