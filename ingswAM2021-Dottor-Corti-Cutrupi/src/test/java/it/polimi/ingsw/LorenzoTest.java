package it.polimi.ingsw;

import it.polimi.ingsw.LorenzoIlMagnifico.*;
import it.polimi.ingsw.developmentcard.Color;
import it.polimi.ingsw.papalpath.PapalPath;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;

public class LorenzoTest {

    Player player = new Player("Alfredo",1);
    LorenzoIlMagnifico lorenzoIlMagnifico= new LorenzoIlMagnifico(player.getDashboard().getPapalPath());
    GameBoard gameBoard= new GameBoard(lorenzoIlMagnifico);
    PapalPath papalPath= new PapalPath(3);

    public LorenzoTest() throws FileNotFoundException {
    }


    /* @Test
     public void lorenzoPapalPathTest(){
         lorenzoIlMagnifico.printDeck();
         System.out.println();
         for(int i=0;i<50;i++)       {
             System.out.println();
             System.out.println("The token activated this turn is "+lorenzoIlMagnifico.nextToken());
             System.out.println();
             lorenzoIlMagnifico.playTurn();
             lorenzoIlMagnifico.printDeck();
             System.out.println("Lorenzo's papal path position is:  "+ lorenzoIlMagnifico.getFaithPosition());
             System.out.println();
         }
         System.out.println("Lorenzo's papal path position is:  "+ lorenzoIlMagnifico.getFaithPosition());
     }
 */
    @Test
    public void discardPowerTest() throws FileNotFoundException {
        gameBoard.decksInitializer();
        DiscardToken discardToken= new DiscardToken(Color.Green);
        discardToken.tokenEffect(papalPath,lorenzoIlMagnifico,gameBoard);
        assertEquals(2,gameBoard.getDevelopmentCardDecks()[2][0].deckSize());
        assertEquals(4,gameBoard.getDevelopmentCardDecks()[2][1].deckSize());
        assertEquals(4,gameBoard.getDevelopmentCardDecks()[2][2].deckSize());
        assertEquals(4,gameBoard.getDevelopmentCardDecks()[2][3].deckSize());
        assertEquals(4,gameBoard.getDevelopmentCardDecks()[1][0].deckSize());
        assertEquals(4,gameBoard.getDevelopmentCardDecks()[0][0].deckSize());
        discardToken.tokenEffect(papalPath,lorenzoIlMagnifico,gameBoard);
        discardToken.tokenEffect(papalPath,lorenzoIlMagnifico,gameBoard);
        assertEquals(0,gameBoard.getDevelopmentCardDecks()[2][0].deckSize());
        assertEquals(4,gameBoard.getDevelopmentCardDecks()[2][1].deckSize());
        assertEquals(4,gameBoard.getDevelopmentCardDecks()[2][2].deckSize());
        assertEquals(4,gameBoard.getDevelopmentCardDecks()[2][3].deckSize());
        assertEquals(2,gameBoard.getDevelopmentCardDecks()[1][0].deckSize());
        assertEquals(4,gameBoard.getDevelopmentCardDecks()[0][0].deckSize());
        discardToken.tokenEffect(papalPath,lorenzoIlMagnifico,gameBoard);
        discardToken.tokenEffect(papalPath,lorenzoIlMagnifico,gameBoard);
        discardToken.tokenEffect(papalPath,lorenzoIlMagnifico,gameBoard);
        discardToken.tokenEffect(papalPath,lorenzoIlMagnifico,gameBoard);
        //NOW SHOULD PRINT "AGG PERSO!"

    }
}
