package it.polimi.ingsw;
import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.developmentcard.Color;
import it.polimi.ingsw.developmentcard.DevelopmentCard;
import it.polimi.ingsw.resource.CoinResource;
import it.polimi.ingsw.resource.Resource;
import org.javatuples.Pair;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DevelopmentCardTest {

    CoinResource coin1=new CoinResource();
    Pair<Integer, Resource> price=new Pair(4,coin1);
    Pair <Integer, Color> cardStats=new Pair(5,"Blue");
    Pair <Integer, Resource> prodRequirements=new Pair(3,coin1);
    Pair <Integer,Resource> prodResults=new Pair(4,coin1);
    DevelopmentCard card=new DevelopmentCard(price,cardStats,prodRequirements,prodResults,6);
    @Test
    public void testVictoryPoints(){
        assertEquals(6,card.getVictoryPoints());
    }

}
