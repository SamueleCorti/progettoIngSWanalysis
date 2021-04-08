package it.polimi.ingsw;

import it.polimi.ingsw.leadercard.LeaderCard;
import it.polimi.ingsw.leadercard.leaderpowers.ExtraDeposit;
import it.polimi.ingsw.papalpath.CardCondition;
import it.polimi.ingsw.requirements.Requirements;
import it.polimi.ingsw.requirements.ResourcesRequirementsForAcquisition;
import it.polimi.ingsw.resource.CoinResource;
import it.polimi.ingsw.resource.ResourceType;
import it.polimi.ingsw.resource.ServantResource;
import it.polimi.ingsw.resource.StoneResource;
import it.polimi.ingsw.storing.ExtraDepot;
import it.polimi.ingsw.storing.RegularityError;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DashboardTest {

    @Test
    public void testingRemoveResourcesFromDashboard() throws RegularityError {
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
        ExtraDeposit extraDeposit = new ExtraDeposit(servant);
        LeaderCard leaderCard = new LeaderCard(requirements,3,extraDeposit);
        dashboard.getLeaderCardZone().addNewCard(leaderCard);
        assertEquals(dashboard.getLeaderCardZone().getLeaderCards().get(0),leaderCard);
        dashboard.getLeaderCardZone().getLeaderCards().get(0).setCondition(CardCondition.Active,dashboard);
        assertEquals(dashboard.getLeaderCardZone().getLeaderCards().get(0).getCondition(),CardCondition.Active);
        assertEquals(dashboard.getExtraDepots().get(0).getExtraDepotType(),servant);
        servant.effectFromMarket(dashboard);
        servant.effectFromMarket(dashboard);
        assertEquals(2,dashboard.getExtraDepots().get(0).getExtraDepotSize());
        assertEquals(servant,dashboard.getExtraDepots().get(0).getExtraDepotType());
        //Testing if the removing works effectively
        dashboard.removeResourcesFromDashboard(2,servant);
        assertEquals(0,dashboard.getExtraDepots().get(0).getExtraDepotSize());
        assertEquals(servant,dashboard.getExtraDepots().get(0).getExtraDepotType());


        //Mixing the removal (part 1): 2 from warehouse and 1 from extradepot
        servant.effectFromMarket(dashboard);
        servant.effectFromMarket(dashboard);
        servant.effectFromMarket(dashboard);
        servant.effectFromMarket(dashboard);
        assertEquals(2,dashboard.getExtraDepots().get(0).getExtraDepotSize());
        assertEquals(servant,dashboard.getExtraDepots().get(0).getExtraDepotType());
        assertEquals(null,dashboard.getWarehouse().returnTypeofDepot(1));
        assertEquals(null,dashboard.getWarehouse().returnTypeofDepot(2));
        assertEquals(ResourceType.Servant,dashboard.getWarehouse().returnTypeofDepot(3));
        assertEquals(0,dashboard.getWarehouse().returnLengthOfDepot(1));
        assertEquals(0,dashboard.getWarehouse().returnLengthOfDepot(2));
        assertEquals(2,dashboard.getWarehouse().returnLengthOfDepot(3));
        dashboard.removeResourcesFromDashboard(3,servant);
        assertEquals(1,dashboard.getExtraDepots().get(0).getExtraDepotSize());
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
        assertEquals(0,dashboard.getExtraDepots().get(0).getExtraDepotSize());
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
        assertEquals(0,dashboard.getExtraDepots().get(0).getExtraDepotSize());
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
        assertEquals(1,dashboard.getExtraDepots().get(0).getExtraDepotSize());
        assertEquals(servant,dashboard.getExtraDepots().get(0).getExtraDepotType());
        assertEquals(2,dashboard.getStrongbox().amountOfResource(servant));
        dashboard.removeResourcesFromDashboard(3,servant);
        assertEquals(null,dashboard.getWarehouse().returnTypeofDepot(1));
        assertEquals(null,dashboard.getWarehouse().returnTypeofDepot(2));
        assertEquals(null,dashboard.getWarehouse().returnTypeofDepot(3));
        assertEquals(0,dashboard.getWarehouse().returnLengthOfDepot(1));
        assertEquals(0,dashboard.getWarehouse().returnLengthOfDepot(2));
        assertEquals(0,dashboard.getWarehouse().returnLengthOfDepot(3));
        assertEquals(0,dashboard.getExtraDepots().get(0).getExtraDepotSize());
        assertEquals(servant,dashboard.getExtraDepots().get(0).getExtraDepotType());
        assertEquals(1,dashboard.getStrongbox().amountOfResource(servant));


        //Removing from strongbox only
        dashboard.getStrongbox().addResource(servant);
        dashboard.getStrongbox().addResource(servant);
        dashboard.removeResourcesFromDashboard(2,servant);
        assertEquals(1,dashboard.getStrongbox().amountOfResource(servant));
    }

    @Test
    public void testingRemoveResourcesFromDashboard2() throws RegularityError{
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
    public void testingAvailableResourceForProduction(){
        Dashboard dashboard= new Dashboard(1);
        dashboard.getExtraDepots().add( new ExtraDepot(new ServantResource()));
        dashboard.getExtraDepots().get(0).addResource(new ServantResource());
        dashboard.getExtraDepots().get(0).addResource(new ServantResource());
        dashboard.getWarehouse().addResource(new ServantResource());
        assertEquals(3,dashboard.availableResourcesForProduction(new ServantResource()));
        assertEquals(3,dashboard.availableResourcesForDevelopment(new ServantResource()));
    }

}
