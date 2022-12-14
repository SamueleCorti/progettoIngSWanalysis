package it.polimi.ingsw;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.exception.BothPlayerAndLorenzoActivatePapalCardException;
import it.polimi.ingsw.exception.LorenzoActivatesPapalCardException;
import it.polimi.ingsw.exception.LorenzoWonTheMatch;
import it.polimi.ingsw.exception.warehouseErrors.TooManyResourcesInADepot;
import it.polimi.ingsw.model.boardsAndPlayer.GameBoard;
import it.polimi.ingsw.model.lorenzoIlMagnifico.*;
import it.polimi.ingsw.model.boardsAndPlayer.Player;
import it.polimi.ingsw.model.developmentcard.Color;
import it.polimi.ingsw.model.developmentcard.DevelopmentCard;
import it.polimi.ingsw.model.developmentcard.DevelopmentCardDeck;
import it.polimi.ingsw.model.papalpath.PapalPath;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class LorenzoTest {

    GameBoard gameBoard= new GameBoard("Alfredo");
    Player player = new Player("Alfredo",1,gameBoard);
    LorenzoIlMagnifico lorenzoIlMagnifico= new LorenzoIlMagnifico(gameBoard);
    PapalPath papalPath= new PapalPath(3);

    public LorenzoTest() throws FileNotFoundException {
    }

/*
    @Test
     public void lorenzoPapalPathTest(){
         lorenzoIlMagnifico.printDeck();
         System.out.println();
         for(int i=0;i<5;i++)       {
             System.out.println();
             System.out.println("The token activated this turn is "+lorenzoIlMagnifico.printDeck());
             System.out.println();
             try {
                 lorenzoIlMagnifico.playTurn();
             } catch (LorenzoWonTheMatch lorenzoWonTheMatch) {
                 lorenzoWonTheMatch.printStackTrace();
             } catch (LorenzoActivatesPapalCardException e) {
                 e.printStackTrace();
             } catch (BothPlayerAndLorenzoActivatePapalCardException e) {
                 e.printStackTrace();
             }
             lorenzoIlMagnifico.printDeck();
             System.out.println("Lorenzo's papal path position is:  "+ lorenzoIlMagnifico.getFaithPosition());
             System.out.println();
         }
         System.out.println("Lorenzo's papal path position is:  "+ lorenzoIlMagnifico.getFaithPosition());
     }*/

    @Test
    public void discardPowerTest() throws FileNotFoundException, LorenzoWonTheMatch {
        //gameBoard.decksInitializer();
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
        Exception exception = assertThrows(LorenzoWonTheMatch.class, () -> {
            discardToken.tokenEffect(papalPath,lorenzoIlMagnifico,gameBoard);
        });
    }

    @Test
    public void jsonTest() throws FileNotFoundException {
    GameBoard testGameBoard = new GameBoard("antonio");
        /*System.out.println(testGameBoard);
        System.out.println("we're trying to serialize the gameboard");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String gameboardJson = gson.toJson(testGameBoard);
        System.out.println(gameboardJson);*/
        System.out.println("we're trying to serialize the gameboard");
        ArrayList<DevelopmentCard> cardsOnTopOfDecks= new ArrayList<DevelopmentCard>();
        for(int i=0;i<3;i++) {
            for (DevelopmentCardDeck deck : testGameBoard.getDevelopmentCardDecks()[i]) {
                cardsOnTopOfDecks.add(deck.getFirstCard());
            }
        }
        System.out.println("we've created the array");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String cardsJson = gson.toJson(cardsOnTopOfDecks);
        System.out.println(cardsJson);

    }

    @Test
    public void toStringTests() throws LorenzoWonTheMatch, LorenzoActivatesPapalCardException, BothPlayerAndLorenzoActivatePapalCardException {
        GameBoard gameBoard= new GameBoard("Serge");
        Player player= new Player("Serge",1,gameBoard);
        LorenzoIlMagnifico ilMagnifico= new LorenzoIlMagnifico(gameBoard);
        BlackCrossToken blackCrossToken=new BlackCrossToken();
        assertEquals(blackCrossToken.toString(),"Black cross");
        DiscardToken discardToken= new DiscardToken(Color.Blue);
        assertEquals(discardToken.getColor(),Color.Blue);
        discardToken.toString();
        DoubleBlackCrossToken doubleBlackCrossToken= new DoubleBlackCrossToken();
        doubleBlackCrossToken.tokenEffect(player.getPapalPath(), lorenzoIlMagnifico, gameBoard);
        assertEquals("Double black cross", doubleBlackCrossToken.toString());
        ilMagnifico.getFaithPosition();
    }
}
