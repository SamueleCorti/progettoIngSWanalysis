package it.polimi.ingsw;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.Exceptions.LorenzoWonTheMatch;
import it.polimi.ingsw.Model.boardsAndPlayer.GameBoard;
import it.polimi.ingsw.Model.LorenzoIlMagnifico.*;
import it.polimi.ingsw.Model.boardsAndPlayer.Player;
import it.polimi.ingsw.Model.developmentcard.Color;
import it.polimi.ingsw.Model.developmentcard.DevelopmentCard;
import it.polimi.ingsw.Model.developmentcard.DevelopmentCardDeck;
import it.polimi.ingsw.Model.papalpath.PapalPath;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class LorenzoTest {

    Player player = new Player("Alfredo",1);
    GameBoard gameBoard= new GameBoard("Alfredo");
    LorenzoIlMagnifico lorenzoIlMagnifico= new LorenzoIlMagnifico(gameBoard);
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
    public void discardPowerTest() throws FileNotFoundException, LorenzoWonTheMatch {
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
}
