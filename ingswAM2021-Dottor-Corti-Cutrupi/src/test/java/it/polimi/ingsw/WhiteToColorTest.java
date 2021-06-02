package it.polimi.ingsw;

import it.polimi.ingsw.exception.PapalCardActivatedException;
import it.polimi.ingsw.model.boardsAndPlayer.Dashboard;
import it.polimi.ingsw.model.developmentcard.Color;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.leadercard.leaderpowers.PowerType;
import it.polimi.ingsw.model.leadercard.leaderpowers.WhiteToColor;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.market.OutOfBoundException;
import it.polimi.ingsw.model.papalpath.CardCondition;
import it.polimi.ingsw.model.requirements.DevelopmentRequirements;
import it.polimi.ingsw.model.requirements.Requirements;
import it.polimi.ingsw.model.resource.*;
import it.polimi.ingsw.exception.warehouseErrors.WarehouseDepotsRegularityError;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class WhiteToColorTest {

    @Test
    public void testingWhiteToColorCreation(){
        DevelopmentRequirements requirement1 = new DevelopmentRequirements(1,1, Color.Blue);
        DevelopmentRequirements requirement2 = new DevelopmentRequirements(2,1,Color.Yellow);
        ArrayList<Requirements> requirements= new ArrayList<Requirements>();
        requirements.add(requirement1);
        requirements.add (requirement2);
        ServantResource servant = new ServantResource();
        ArrayList<Resource> array = new ArrayList<>();
        array.add(servant);
        WhiteToColor whiteToColor = new WhiteToColor(array);
        LeaderCard leaderCard = new LeaderCard(requirements,5,whiteToColor,false);

        //assertEquals(servant,whiteToColor.returnRelatedResource());
        assertEquals(PowerType.WhiteToColor,whiteToColor.returnPowerType());
        assertEquals(CardCondition.Inactive,leaderCard.getCondition());
        assertEquals(requirements,leaderCard.getCardRequirements());
    }

    @Test
    public void testingNormalInteraction() throws WarehouseDepotsRegularityError, OutOfBoundException, FileNotFoundException, PapalCardActivatedException {
        DevelopmentRequirements requirement1 = new DevelopmentRequirements(1,1, Color.Blue);
        DevelopmentRequirements requirement2 = new DevelopmentRequirements(2,1,Color.Yellow);
        ArrayList<Requirements> requirements= new ArrayList<Requirements>();
        requirements.add(requirement1);
        requirements.add (requirement2);
        ServantResource servant = new ServantResource();
        ArrayList<Resource> array = new ArrayList<>();
        array.add(servant);
        WhiteToColor whiteToColor = new WhiteToColor(array);
        LeaderCard leaderCard = new LeaderCard(requirements,5,whiteToColor,false);

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
        dashboard.getLeaderCardZone().getLeaderCards().get(0).setCondition(CardCondition.Active);
        assertEquals(dashboard.getLeaderCardZone().getLeaderCards().get(0).getCondition(),CardCondition.Active);
        assertEquals(dashboard.getWhiteToColorResources().get(0),servant);

        //testing that the leader power works correctly
        market.acquireResourcesFromMarket(true,1,dashboard);
        assertEquals(null,dashboard.getWarehouse().returnTypeofDepot(1));
        assertEquals(ResourceType.Coin,dashboard.getWarehouse().returnTypeofDepot(2));
        assertEquals(ResourceType.Servant,dashboard.getWarehouse().returnTypeofDepot(3));
        assertEquals(0,dashboard.getWarehouse().returnLengthOfDepot(1));
        assertEquals(1,dashboard.getWarehouse().returnLengthOfDepot(2));
        assertEquals(3,dashboard.getWarehouse().returnLengthOfDepot(3));
    }
}
