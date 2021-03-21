package it.polimi.ingsw.papalpath;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PapalPathTest {

    @Test
    public void testLenght(){
        PapalPath path= new PapalPath();
        assertEquals(3,path.getCards().length);
    }

    @Test
    public void testFirstPapalFavorCard(){
        PapalPath path= new PapalPath();
        assertEquals(8,path.getCards(0).getNumberID());
        assertEquals(2,path.getCards(0).getVictoryPoints());
        assertEquals(PapalCardCondition.Inactive,path.getCards(0).getCondition());
    }

    @Test
    public void testSecondtPapalFavorCard(){
        PapalPath path= new PapalPath();
        assertEquals(16,path.getCards(1).getNumberID());
        assertEquals(3,path.getCards(1).getVictoryPoints());
        assertEquals(PapalCardCondition.Inactive,path.getCards(1).getCondition());
    }

    @Test
    public void testThirdPapalFavorCard(){
        PapalPath path= new PapalPath();
        assertEquals(24,path.getCards(2).getNumberID());
        assertEquals(4,path.getCards(2).getVictoryPoints());
        assertEquals(PapalCardCondition.Inactive,path.getCards(2).getCondition());
    }
    @Test
    public void testFaithPosition(){
        PapalPath path= new PapalPath();
        assertEquals(0,path.getFaithPosition());
    }
}