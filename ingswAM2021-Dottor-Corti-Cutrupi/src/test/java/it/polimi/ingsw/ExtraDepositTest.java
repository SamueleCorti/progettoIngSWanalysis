package it.polimi.ingsw;

import it.polimi.ingsw.developmentcard.Color;
import it.polimi.ingsw.leadercard.LeaderCard;
import it.polimi.ingsw.leadercard.leaderpowers.ExtraDeposit;
import it.polimi.ingsw.leadercard.leaderpowers.PowerType;
import it.polimi.ingsw.leadercard.leaderpowers.WhiteToColor;
import it.polimi.ingsw.market.Market;
import it.polimi.ingsw.market.OutOfBoundException;
import it.polimi.ingsw.papalpath.CardCondition;
import it.polimi.ingsw.requirements.DevelopmentRequirements;
import it.polimi.ingsw.requirements.Requirements;
import it.polimi.ingsw.requirements.ResourcesRequirements;
import it.polimi.ingsw.resource.*;
import it.polimi.ingsw.storing.RegularityError;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExtraDepositTest {
    @Test
    public void testingCreation(){
        StoneResource stone = new StoneResource();
        ResourcesRequirements requirement1 = new ResourcesRequirements(5,stone);
        ArrayList<Requirements> requirements= new ArrayList<Requirements>();
        requirements.add(requirement1);
        ServantResource servant = new ServantResource();
        ExtraDeposit extraDeposit = new ExtraDeposit(servant);
        LeaderCard leaderCard = new LeaderCard(requirements,3,extraDeposit);

        assertEquals(servant,extraDeposit.returnRelatedResource());
        assertEquals(PowerType.ExtraDeposit,leaderCard.getLeaderPower().returnPowerType());
        assertEquals(CardCondition.Inactive,leaderCard.getCondition());
        assertEquals(requirements,leaderCard.getCardRequirements());
    }

    @Test
    public void testingNormalInteractionWith1Card() throws RegularityError, OutOfBoundException {
        StoneResource stone = new StoneResource();
        ResourcesRequirements requirement1 = new ResourcesRequirements(5,stone);
        ArrayList<Requirements> requirements= new ArrayList<Requirements>();
        requirements.add(requirement1);
        ServantResource servant = new ServantResource();
        ExtraDeposit extraDeposit = new ExtraDeposit(servant);
        LeaderCard leaderCard = new LeaderCard(requirements,3,extraDeposit);

        CoinResource coin = new CoinResource();
        ShieldResource shield = new ShieldResource();
        FaithResource faith = new FaithResource();
        BlankResource blank = new BlankResource();
        Market market = new Market(faith,stone,servant,servant,coin,blank,blank,blank,coin,shield,shield,stone,blank);
        Dashboard dashboard = new Dashboard(1);

        //new leader card is correctly added to leader card zone
        dashboard.getLeaderCardZone().addNewCard(leaderCard);
        assertEquals(dashboard.getLeaderCardZone().getLeaderCards().get(0),leaderCard);

        //leader card is correctly set as Active and servant resource is added to dashboard in whiteToColorResources
        dashboard.getLeaderCardZone().getLeaderCards().get(0).setCondition(CardCondition.Active,dashboard);
        assertEquals(dashboard.getLeaderCardZone().getLeaderCards().get(0).getCondition(),CardCondition.Active);
        assertEquals(dashboard.getExtraDepots().get(0).getExtraDepotType(),servant);

        //I receive 1 stone, 2 servans and 1 faith, checking that both servans are on extradepot and stone is in warehouse
        market.getResourcesFromMarket(true,0,dashboard);
        assertEquals(1,dashboard.getPapalPath().getFaithPosition());
        assertEquals(dashboard.getWarehouse().returnWarehouseSize(),3);
        assertEquals("null",dashboard.getWarehouse().returnTypeofDepot(1));
        assertEquals("null",dashboard.getWarehouse().returnTypeofDepot(2));
        assertEquals("stone",dashboard.getWarehouse().returnTypeofDepot(3));
        assertEquals(0,dashboard.getWarehouse().returnLengthOfDepot(1));
        assertEquals(0,dashboard.getWarehouse().returnLengthOfDepot(2));
        assertEquals(1,dashboard.getWarehouse().returnLengthOfDepot(3));
        assertEquals(2,dashboard.getExtraDepots().get(0).getExtraDepotSize());
        assertEquals(servant,dashboard.getExtraDepots().get(0).getExtraDepotType());

        //I receive 1 servant, 1 faith and 1 stone. Checking that now servant is added in warehouse (because extradepot is full)
        market.getResourcesFromMarket(true,0,dashboard);
        assertEquals(2,dashboard.getPapalPath().getFaithPosition());
        assertEquals(dashboard.getWarehouse().returnWarehouseSize(),3);
        assertEquals("null",dashboard.getWarehouse().returnTypeofDepot(1));
        assertEquals("servant",dashboard.getWarehouse().returnTypeofDepot(2));
        assertEquals("stone",dashboard.getWarehouse().returnTypeofDepot(3));
        assertEquals(0,dashboard.getWarehouse().returnLengthOfDepot(1));
        assertEquals(1,dashboard.getWarehouse().returnLengthOfDepot(2));
        assertEquals(2,dashboard.getWarehouse().returnLengthOfDepot(3));
        assertEquals(2,dashboard.getExtraDepots().get(0).getExtraDepotSize());
        assertEquals(servant,dashboard.getExtraDepots().get(0).getExtraDepotType());
    }
}
