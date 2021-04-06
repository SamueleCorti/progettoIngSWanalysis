package it.polimi.ingsw;

import it.polimi.ingsw.developmentcard.Color;
import it.polimi.ingsw.leadercard.LeaderCard;
import it.polimi.ingsw.leadercard.leaderpowers.PowerType;
import it.polimi.ingsw.leadercard.leaderpowers.WhiteToColor;
import it.polimi.ingsw.market.Market;
import it.polimi.ingsw.market.OutOfBoundException;
import it.polimi.ingsw.papalpath.CardCondition;
import it.polimi.ingsw.requirements.DevelopmentRequirements;
import it.polimi.ingsw.requirements.Requirements;
import it.polimi.ingsw.resource.*;
import it.polimi.ingsw.storing.RegularityError;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class WhiteToColorTest {

    @Test
    public void testingWhiteToColorCreation(){
        DevelopmentRequirements requirement1 = new DevelopmentRequirements(1, Color.Blue);
        DevelopmentRequirements requirement2 = new DevelopmentRequirements(2,Color.Yellow);
        ArrayList<Requirements> requirements= new ArrayList<Requirements>();
        requirements.add(requirement1);
        requirements.add (requirement2);
        ServantResource servant = new ServantResource();
        WhiteToColor whiteToColor = new WhiteToColor(servant);
        LeaderCard leaderCard = new LeaderCard(requirements,5,whiteToColor);

        assertEquals(servant,whiteToColor.returnRelatedResource());
        assertEquals(PowerType.WhiteToColor,whiteToColor.returnPowerType());
        assertEquals(CardCondition.Inactive,leaderCard.getCondition());
        assertEquals(requirements,leaderCard.getCardRequirements());
    }

    @Test
    public void testingNormalInteraction() throws RegularityError, OutOfBoundException {
        DevelopmentRequirements requirement1 = new DevelopmentRequirements(1, Color.Blue);
        DevelopmentRequirements requirement2 = new DevelopmentRequirements(2,Color.Yellow);
        ArrayList<Requirements> requirements= new ArrayList<Requirements>();
        requirements.add(requirement1);
        requirements.add (requirement2);
        ServantResource servant = new ServantResource();
        WhiteToColor whiteToColor = new WhiteToColor(servant);
        LeaderCard leaderCard = new LeaderCard(requirements,5,whiteToColor);

        CoinResource coin = new CoinResource();
        ShieldResource shield = new ShieldResource();
        StoneResource stone = new StoneResource();
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
        assertEquals(dashboard.getWhiteToColorResources().get(0),servant);

        //testing that the leader power works correctly
        market.getResourcesFromMarket(true,1,dashboard);
        assertEquals("null",dashboard.getWarehouse().returnTypeofDepot(1));
        assertEquals("coin",dashboard.getWarehouse().returnTypeofDepot(2));
        assertEquals("servant",dashboard.getWarehouse().returnTypeofDepot(3));
        assertEquals(0,dashboard.getWarehouse().returnLengthOfDepot(1));
        assertEquals(1,dashboard.getWarehouse().returnLengthOfDepot(2));
        assertEquals(3,dashboard.getWarehouse().returnLengthOfDepot(3));
    }
}