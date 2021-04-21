package it.polimi.ingsw;

import it.polimi.ingsw.Exceptions.NotEnoughResourcesToActivateProductionException;
import it.polimi.ingsw.resource.CoinResource;
import it.polimi.ingsw.resource.Resource;
import it.polimi.ingsw.resource.ServantResource;
import it.polimi.ingsw.resource.StoneResource;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class DiscountPowerTest {

    @Test
    public void availableResourcesForDevelopmentTest(){
        Resource stone= new StoneResource();
        Resource coin= new CoinResource();
        Dashboard dashboard= new Dashboard(1);
        dashboard.activateDiscountCard(new StoneResource());
        dashboard.activateDiscountCard(new ServantResource());
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
        try {
            dashboard.removeResourcesFromDashboard(2, new ServantResource());
        } catch (NotEnoughResourcesToActivateProductionException regularityError) {
            regularityError.printStackTrace();
        }
        assertEquals(0,dashboard.availableResourcesForProduction(new ServantResource()));
        assertEquals(1,dashboard.availableResourcesForDevelopment(new ServantResource()));
    }



}
