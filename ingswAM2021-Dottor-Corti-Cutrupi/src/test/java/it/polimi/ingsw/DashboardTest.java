package it.polimi.ingsw;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.exception.NotEnoughResourcesToActivateProductionException;
import it.polimi.ingsw.model.boardsAndPlayer.Dashboard;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.leadercard.leaderpowers.ExtraDeposit;
import it.polimi.ingsw.model.papalpath.CardCondition;
import it.polimi.ingsw.model.requirements.Requirements;
import it.polimi.ingsw.model.requirements.ResourcesRequirementsForAcquisition;
import it.polimi.ingsw.model.resource.*;
import it.polimi.ingsw.exception.warehouseErrors.WarehouseDepotsRegularityError;
import it.polimi.ingsw.model.storing.ExtraDepot;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DashboardTest {

    @Test
    public void testingRemoveResourcesFromDashboard() throws WarehouseDepotsRegularityError, NotEnoughResourcesToActivateProductionException, FileNotFoundException {
        //testing when I remove resources from warehouse only
        Dashboard dashboard = new Dashboard(1);
        CoinResource coin = new CoinResource();
        coin.effectFromMarket(dashboard);
        coin.effectFromMarket(dashboard);
        assertEquals(null,dashboard.getWarehouse().returnTypeofDepot(1));
        assertEquals(null,dashboard.getWarehouse().returnTypeofDepot(2));
        assertEquals(ResourceType.Coin,dashboard.getWarehouse().returnTypeofDepot(3));
        assertEquals(0,dashboard.getWarehouse().returnLengthOfDepot(1));
        assertEquals(0,dashboard.getWarehouse().returnLengthOfDepot(2));
        assertEquals(2,dashboard.getWarehouse().returnLengthOfDepot(3));
        dashboard.removeResourcesFromDashboard(2,coin);
        assertEquals(null,dashboard.getWarehouse().returnTypeofDepot(1));
        assertEquals(null,dashboard.getWarehouse().returnTypeofDepot(2));
        assertEquals(null,dashboard.getWarehouse().returnTypeofDepot(3));
        assertEquals(0,dashboard.getWarehouse().returnLengthOfDepot(1));
        assertEquals(0,dashboard.getWarehouse().returnLengthOfDepot(2));
        assertEquals(0,dashboard.getWarehouse().returnLengthOfDepot(3));


        //I'm creating an extra depot to check if the method works even with it
        StoneResource stone = new StoneResource();
        ResourcesRequirementsForAcquisition requirement1 = new ResourcesRequirementsForAcquisition(5,stone);
        ArrayList<Requirements> requirements= new ArrayList<Requirements>();
        requirements.add(requirement1);
        ServantResource servant = new ServantResource();
        ArrayList<Resource> resources = new ArrayList<Resource>();
        resources.add(servant);
        ExtraDeposit extraDeposit = new ExtraDeposit(resources);
        LeaderCard leaderCard = new LeaderCard(requirements,3,extraDeposit,false);
        dashboard.getLeaderCardZone().addNewCard(leaderCard);
        assertEquals(dashboard.getLeaderCardZone().getLeaderCards().get(0),leaderCard);
        dashboard.getLeaderCardZone().getLeaderCards().get(0).setCondition(CardCondition.Active);
        assertEquals(dashboard.getLeaderCardZone().getLeaderCards().get(0).getCondition(),CardCondition.Active);
        assertEquals(dashboard.getExtraDepots().get(0).getExtraDepotType(),servant);
        servant.effectFromMarket(dashboard);
        servant.effectFromMarket(dashboard);
        assertEquals(2,dashboard.getExtraDepots().get(0).getAmountOfContainedResources());
        assertEquals(servant,dashboard.getExtraDepots().get(0).getExtraDepotType());
        //Testing if the removing works effectively
        dashboard.removeResourcesFromDashboard(2,servant);
        assertEquals(0,dashboard.getExtraDepots().get(0).getAmountOfContainedResources());
        assertEquals(servant,dashboard.getExtraDepots().get(0).getExtraDepotType());


        //Mixing the removal (part 1): 2 from warehouse and 1 from extradepot
        servant.effectFromMarket(dashboard);
        servant.effectFromMarket(dashboard);
        servant.effectFromMarket(dashboard);
        servant.effectFromMarket(dashboard);
        assertEquals(2,dashboard.getExtraDepots().get(0).getAmountOfContainedResources());
        assertEquals(servant,dashboard.getExtraDepots().get(0).getExtraDepotType());
        assertEquals(null,dashboard.getWarehouse().returnTypeofDepot(1));
        assertEquals(null,dashboard.getWarehouse().returnTypeofDepot(2));
        assertEquals(ResourceType.Servant,dashboard.getWarehouse().returnTypeofDepot(3));
        assertEquals(0,dashboard.getWarehouse().returnLengthOfDepot(1));
        assertEquals(0,dashboard.getWarehouse().returnLengthOfDepot(2));
        assertEquals(2,dashboard.getWarehouse().returnLengthOfDepot(3));
        dashboard.removeResourcesFromDashboard(3,servant);
        assertEquals(1,dashboard.getExtraDepots().get(0).getAmountOfContainedResources());
        assertEquals(servant,dashboard.getExtraDepots().get(0).getExtraDepotType());
        assertEquals(null,dashboard.getWarehouse().returnTypeofDepot(1));
        assertEquals(null,dashboard.getWarehouse().returnTypeofDepot(2));
        assertEquals(null,dashboard.getWarehouse().returnTypeofDepot(3));
        assertEquals(0,dashboard.getWarehouse().returnLengthOfDepot(1));
        assertEquals(0,dashboard.getWarehouse().returnLengthOfDepot(2));
        assertEquals(0,dashboard.getWarehouse().returnLengthOfDepot(3));

        //Mixing the removal (part 2): 1 from extradepot and 1 from strongbox
        dashboard.getStrongbox().addResource(servant);
        dashboard.getStrongbox().addResource(servant);
        dashboard.removeResourcesFromDashboard(2,servant);
        assertEquals(0,dashboard.getExtraDepots().get(0).getAmountOfContainedResources());
        assertEquals(servant,dashboard.getExtraDepots().get(0).getExtraDepotType());
        assertEquals(1,dashboard.getStrongbox().amountOfResource(servant));

        //Mixing the removal (part 3): 1 from warehouse and 1 from strongbox
        dashboard.getStrongbox().addResource(servant);
        dashboard.getWarehouse().addResource(servant);
        assertEquals(null,dashboard.getWarehouse().returnTypeofDepot(1));
        assertEquals(null,dashboard.getWarehouse().returnTypeofDepot(2));
        assertEquals(ResourceType.Servant,dashboard.getWarehouse().returnTypeofDepot(3));
        assertEquals(0,dashboard.getWarehouse().returnLengthOfDepot(1));
        assertEquals(0,dashboard.getWarehouse().returnLengthOfDepot(2));
        assertEquals(1,dashboard.getWarehouse().returnLengthOfDepot(3));
        dashboard.removeResourcesFromDashboard(2,servant);
        assertEquals(null,dashboard.getWarehouse().returnTypeofDepot(1));
        assertEquals(null,dashboard.getWarehouse().returnTypeofDepot(2));
        assertEquals(null,dashboard.getWarehouse().returnTypeofDepot(3));
        assertEquals(0,dashboard.getWarehouse().returnLengthOfDepot(1));
        assertEquals(0,dashboard.getWarehouse().returnLengthOfDepot(2));
        assertEquals(0,dashboard.getWarehouse().returnLengthOfDepot(3));
        assertEquals(0,dashboard.getExtraDepots().get(0).getAmountOfContainedResources());
        assertEquals(servant,dashboard.getExtraDepots().get(0).getExtraDepotType());
        assertEquals(1,dashboard.getStrongbox().amountOfResource(servant));

        //Mixing the removal (part 4): 1 from warehouse,1 from extradepot and 1 from strongbox
        dashboard.getStrongbox().addResource(servant);
        dashboard.getWarehouse().addResource(servant);
        dashboard.getExtraDepots().get(0).addResource(servant);
        assertEquals(null,dashboard.getWarehouse().returnTypeofDepot(1));
        assertEquals(null,dashboard.getWarehouse().returnTypeofDepot(2));
        assertEquals(ResourceType.Servant,dashboard.getWarehouse().returnTypeofDepot(3));
        assertEquals(0,dashboard.getWarehouse().returnLengthOfDepot(1));
        assertEquals(0,dashboard.getWarehouse().returnLengthOfDepot(2));
        assertEquals(1,dashboard.getWarehouse().returnLengthOfDepot(3));
        assertEquals(1,dashboard.getExtraDepots().get(0).getAmountOfContainedResources());
        assertEquals(servant,dashboard.getExtraDepots().get(0).getExtraDepotType());
        assertEquals(2,dashboard.getStrongbox().amountOfResource(servant));
        dashboard.removeResourcesFromDashboard(3,servant);
        assertEquals(null,dashboard.getWarehouse().returnTypeofDepot(1));
        assertEquals(null,dashboard.getWarehouse().returnTypeofDepot(2));
        assertEquals(null,dashboard.getWarehouse().returnTypeofDepot(3));
        assertEquals(0,dashboard.getWarehouse().returnLengthOfDepot(1));
        assertEquals(0,dashboard.getWarehouse().returnLengthOfDepot(2));
        assertEquals(0,dashboard.getWarehouse().returnLengthOfDepot(3));
        assertEquals(0,dashboard.getExtraDepots().get(0).getAmountOfContainedResources());
        assertEquals(servant,dashboard.getExtraDepots().get(0).getExtraDepotType());
        assertEquals(1,dashboard.getStrongbox().amountOfResource(servant));


        //Removing from strongbox only
        dashboard.getStrongbox().addResource(servant);
        dashboard.getStrongbox().addResource(servant);
        dashboard.removeResourcesFromDashboard(2,servant);
        assertEquals(1,dashboard.getStrongbox().amountOfResource(servant));
    }

    @Test
    public void testingRemoveResourcesFromDashboard2() throws WarehouseDepotsRegularityError, NotEnoughResourcesToActivateProductionException, FileNotFoundException {
        Dashboard dashboard = new Dashboard(1);
        CoinResource coin = new CoinResource();
        coin.effectFromMarket(dashboard);
        coin.effectFromMarket(dashboard);
        dashboard.getStrongbox().addResource(coin);
        dashboard.getStrongbox().addResource(coin);
        dashboard.removeResourcesFromDashboard(3,coin);
        assertEquals(null,dashboard.getWarehouse().returnTypeofDepot(1));
        assertEquals(null,dashboard.getWarehouse().returnTypeofDepot(2));
        assertEquals(null,dashboard.getWarehouse().returnTypeofDepot(3));
        assertEquals(0,dashboard.getWarehouse().returnLengthOfDepot(1));
        assertEquals(0,dashboard.getWarehouse().returnLengthOfDepot(2));
        assertEquals(0,dashboard.getWarehouse().returnLengthOfDepot(3));
        assertEquals(1,dashboard.getStrongbox().amountOfResource(coin));
    }

    @Test
    public void testingAvailableResourceForProduction() throws FileNotFoundException {
        Dashboard dashboard= new Dashboard(1);
        //dashboard.getExtraDepots().add( new ExtraDepot(new ServantResource()));
        dashboard.getExtraDepots().get(0).addResource(new ServantResource());
        dashboard.getExtraDepots().get(0).addResource(new ServantResource());
        dashboard.getWarehouse().addResource(new ServantResource());
        assertEquals(3,dashboard.availableResourcesForProduction(new ServantResource()));
        assertEquals(3,dashboard.availableResourcesForDevelopment(new ServantResource()));
    }
    @Test
    public void JsonDashboardTest() throws FileNotFoundException {
        Dashboard dashboard= new Dashboard(1);
        ArrayList<Resource> list = new ArrayList<>();
        list.add(new ServantResource());list.add(new ServantResource());
        dashboard.getExtraDepots().add( new ExtraDepot(new ExtraDeposit(list)));
        dashboard.getExtraDepots().get(0).addResource(new ServantResource());
        dashboard.getExtraDepots().get(0).addResource(new ServantResource());
        dashboard.getWarehouse().addResource(new ServantResource());
        assertEquals(3,dashboard.availableResourcesForProduction(new ServantResource()));
        assertEquals(3,dashboard.availableResourcesForDevelopment(new ServantResource()));
        Gson dashboardGson = new GsonBuilder().setPrettyPrinting().create();
        String dashboardJson = dashboardGson.toJson(dashboard);
        System.out.println(dashboardJson);
    }

}
