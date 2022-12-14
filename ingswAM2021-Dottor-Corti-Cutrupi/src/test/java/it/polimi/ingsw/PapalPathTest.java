package it.polimi.ingsw;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.exception.BothPlayerAndLorenzoActivatePapalCardException;
import it.polimi.ingsw.exception.LorenzoActivatesPapalCardException;
import it.polimi.ingsw.exception.LorenzoWonTheMatch;
import it.polimi.ingsw.exception.PapalCardActivatedException;
import it.polimi.ingsw.model.papalpath.CardCondition;
import it.polimi.ingsw.model.papalpath.PapalPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

class PapalPathTest {
    PapalPath path= new PapalPath(1);

    PapalPathTest() throws FileNotFoundException {
    }

    @Test
    public void testLenght(){
        assertEquals(3,path.getCards().length);
    }

    @Test
    public void testFirstPapalFavorCard(){
        assertEquals(8,path.getCards(0).getFaithPosition());
        assertEquals(2,path.getCards(0).getVictoryPoints());
        Assertions.assertEquals(CardCondition.Inactive,path.getCards(0).getCondition());
    }

    @Test
    public void testSecondtPapalFavorCard(){
        assertEquals(16,path.getCards(1).getFaithPosition());
        assertEquals(3,path.getCards(1).getVictoryPoints());
        assertEquals(CardCondition.Inactive,path.getCards(1).getCondition());
    }

    @Test
    public void testThirdPapalFavorCard(){
        assertEquals(24,path.getCards(2).getFaithPosition());
        assertEquals(4,path.getCards(2).getVictoryPoints());
        assertEquals(CardCondition.Inactive,path.getCards(2).getCondition());
    }
    @Test
    public void testFaithPositionThirdPlayer() throws FileNotFoundException {
        PapalPath path= new PapalPath(3);
        assertEquals(1,path.getFaithPosition());
    }

    @Test
    public void testFaithPositionFirstPlayer(){
        assertEquals(0,path.getFaithPosition());
    }

    //checks if the points returned and the effects on the cards are right
   /* @Test
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
    }*/

    //checks if the pope meeting cards' constructor works as intended
    @Test
    public void initializingCardsTest() throws FileNotFoundException {
        PapalPath path1= new PapalPath(1);
        PapalPath path2= new PapalPath(2);
        PapalPath path3= new PapalPath(3);
        PapalPath path4= new PapalPath(4);
        for(int i=0;i<3;i++) {
            assertEquals(CardCondition.Inactive, path1.getCards(i).getCondition());
            assertEquals(CardCondition.Inactive, path2.getCards(i).getCondition());
            assertEquals(CardCondition.Inactive, path3.getCards(i).getCondition());
            assertEquals(CardCondition.Inactive, path4.getCards(i).getCondition());
        }
    }

    //checks if the pope meeting cards actually work, in future the checkPosition methods should get called automatically
    @Test
    public void popeMeetingTest() throws FileNotFoundException, PapalCardActivatedException {
        PapalPath path1= new PapalPath(1);
        PapalPath path2= new PapalPath(2);
        PapalPath path3= new PapalPath(3);
        PapalPath path4= new PapalPath(4);

        path3.moveForward(5);
        try{path2.moveForward(10);}
        catch (PapalCardActivatedException e){

        }
        path1.checkPosition(0);
        path3.checkPosition(0);
        path4.checkPosition(0);
        assertEquals(CardCondition.Discarded,path1.getCards(0).getCondition());
        assertEquals(CardCondition.Active,path2.getCards(0).getCondition());
        assertEquals(CardCondition.Active,path3.getCards(0).getCondition());
        assertEquals(CardCondition.Discarded,path4.getCards(0).getCondition());

        try{path1.moveForward(16);}
        catch (PapalCardActivatedException e){}
        path2.checkPosition(1);
        path3.checkPosition(1);
        path4.checkPosition(1);
        //the first card must keep its condition in each path
        assertEquals(CardCondition.Discarded,path1.getCards(0).getCondition());
        assertEquals(CardCondition.Active,path2.getCards(0).getCondition());
        assertEquals(CardCondition.Active,path3.getCards(0).getCondition());
        assertEquals(CardCondition.Discarded,path4.getCards(0).getCondition());
        //only the first path's card should be active now
        assertEquals(CardCondition.Active,path1.getCards(1).getCondition());
        assertEquals(CardCondition.Discarded,path2.getCards(1).getCondition());
        assertEquals(CardCondition.Discarded,path3.getCards(1).getCondition());
        assertEquals(CardCondition.Discarded,path4.getCards(1).getCondition());

        try{path4.moveForward(23);}
        catch (PapalCardActivatedException e){}
        path1.checkPosition(2);
        path2.checkPosition(2);
        path3.checkPosition(2);
        //the first card must keep its condition in each path
        assertEquals(CardCondition.Discarded,path1.getCards(0).getCondition());
        assertEquals(CardCondition.Active,path2.getCards(0).getCondition());
        assertEquals(CardCondition.Active,path3.getCards(0).getCondition());
        assertEquals(CardCondition.Discarded,path4.getCards(0).getCondition());
        //checking the same thing as above
        assertEquals(CardCondition.Active,path1.getCards(1).getCondition());
        assertEquals(CardCondition.Discarded,path2.getCards(1).getCondition());
        assertEquals(CardCondition.Discarded,path3.getCards(1).getCondition());
        assertEquals(CardCondition.Discarded,path4.getCards(1).getCondition());
        //only the fourth papal path should have its card active
        assertEquals(CardCondition.Discarded,path1.getCards(2).getCondition());
        assertEquals(CardCondition.Discarded,path2.getCards(2).getCondition());
        assertEquals(CardCondition.Discarded,path3.getCards(2).getCondition());
        assertEquals(CardCondition.Active,path4.getCards(2).getCondition());
        System.out.println("Victory points 4: "+path4.getVictoryPoints());
        System.out.println("Victory points 1: "+path1.getVictoryPoints());
    }

    @Test
    public void testLorenzo() throws LorenzoWonTheMatch, LorenzoActivatesPapalCardException, BothPlayerAndLorenzoActivatePapalCardException {
        PapalPath papalPath= new PapalPath(1);
        assertEquals(0, papalPath.getFaithPositionLorenzo());
        papalPath.moveForwardLorenzo(3);
        assertEquals(3, papalPath.getFaithPositionLorenzo());
        try {
            papalPath.moveForward();
            papalPath.moveForward(7);
        } catch (PapalCardActivatedException e) {
        }
        for(int i=0; i<25; i++){
            try {
                papalPath.moveForwardLorenzo();
            } catch (LorenzoActivatesPapalCardException e) {
            } catch (LorenzoWonTheMatch e) {
            } catch (BothPlayerAndLorenzoActivatePapalCardException e) {
            }
        }
        assertEquals(1, papalPath.numOfReportSection(7));
        assertEquals(1, papalPath.cardsActivated());
        papalPath.isPopeSpace(3);   papalPath.isPopeSpace(8);
        papalPath.getPapalTiles();  papalPath.getNextCardToActivatePosition();
        papalPath.cardsActivated();
        assertEquals(0, papalPath.getNextCardToActivatePosition());
        PapalPath copy= new PapalPath(papalPath);
    }

    @Test
    public void moveForwardTest() throws PapalCardActivatedException, LorenzoWonTheMatch, LorenzoActivatesPapalCardException, BothPlayerAndLorenzoActivatePapalCardException {
        PapalPath path= new PapalPath(1);
        path.moveForward();
        path.moveForwardLorenzo();
        path.getNextCardToActivatePosition();
        path.moveForward(6);
        try{
            path.moveForwardLorenzo(10);
        } catch (BothPlayerAndLorenzoActivatePapalCardException e) {
        }
    }
}