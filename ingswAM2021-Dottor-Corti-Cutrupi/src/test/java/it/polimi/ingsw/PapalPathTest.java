package it.polimi.ingsw;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.papalpath.CardCondition;
import it.polimi.ingsw.papalpath.PapalPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PapalPathTest {
    PapalPath path= new PapalPath(1);
    @Test
    public void testLenght(){
        assertEquals(3,path.getCards().length);
    }

    @Test
    public void testFirstPapalFavorCard(){
        assertEquals(8,path.getCards(0).getNumberID());
        assertEquals(2,path.getCards(0).getVictoryPoints());
        Assertions.assertEquals(CardCondition.Inactive,path.getCards(0).getCondition());
    }

    @Test
    public void testSecondtPapalFavorCard(){
        assertEquals(16,path.getCards(1).getNumberID());
        assertEquals(3,path.getCards(1).getVictoryPoints());
        assertEquals(CardCondition.Inactive,path.getCards(1).getCondition());
    }

    @Test
    public void testThirdPapalFavorCard(){
        assertEquals(24,path.getCards(2).getNumberID());
        assertEquals(4,path.getCards(2).getVictoryPoints());
        assertEquals(CardCondition.Inactive,path.getCards(2).getCondition());
    }
    @Test
    public void testFaithPositionThirdPlayer(){
        PapalPath path= new PapalPath(3);
        assertEquals(1,path.getFaithPosition());
    }

    @Test
    public void testFaithPositionFirstPlayer(){
        assertEquals(0,path.getFaithPosition());
    }

    //checks if the points returned and the effects on the cards are right
    @Test
    public void checkPositionConsequences(){
        PapalPath path= new PapalPath(3);
        path.moveForward();
        path.moveForward();
        path.moveForward();
        path.moveForward();
        assertEquals(1,path.getPositionVP());
        path.moveForward(); //6
        assertEquals(2,path.getPositionVP());
        path.moveForward();
        assertEquals(CardCondition.Inactive,path.getCards(0).getCondition());
        path.moveForward();
        assertEquals(CardCondition.Active,path.getCards(0).getCondition());
        for (int i=0; i<9; i++) {path.moveForward();}
        assertEquals(CardCondition.Active,path.getCards(1).getCondition());
        assertEquals(9,path.getPositionVP());
    }
}