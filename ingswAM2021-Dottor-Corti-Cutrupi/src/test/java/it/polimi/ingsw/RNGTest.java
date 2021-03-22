package it.polimi.ingsw;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class RNGTest {
   // RNG test= new RNG(13);
    Market market = new Market();


    @Test
    public void alwaysTrueTest(){
        market.printMarket();
        assertEquals(1,1);
    }

}
