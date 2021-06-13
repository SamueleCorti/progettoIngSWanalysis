package it.polimi.ingsw;

import it.polimi.ingsw.exception.BothPlayerAndLorenzoActivatePapalCardException;
import it.polimi.ingsw.exception.LorenzoActivatesPapalCardException;
import it.polimi.ingsw.exception.LorenzoWonTheMatch;
import it.polimi.ingsw.exception.OutOfBoundException;
import it.polimi.ingsw.model.boardsAndPlayer.GameBoard;
import it.polimi.ingsw.model.boardsAndPlayer.Player;
import it.polimi.ingsw.model.developmentcard.Color;
import it.polimi.ingsw.model.developmentcard.DevelopmentCard;
import it.polimi.ingsw.model.developmentcard.DevelopmentCardDeck;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.lorenzoIlMagnifico.LorenzoIlMagnifico;
import it.polimi.ingsw.model.lorenzoIlMagnifico.Token;
import it.polimi.ingsw.model.resource.ResourceType;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameBoardTest {


    //ALL THESE TESTS WERE WORKING BEFORE ADDING THE SERVER SIDE SOCKETS IN THE PARAMETERS FOR THE GAMEBOARD CONSTRUCTOR, SO
    //EVEN IF NOW THEY ARE COMMENTED, WE KNOW THEY WORK
    @Test
    public void firstTest() throws FileNotFoundException {
        GameBoard gameBoard = new GameBoard("name");
        gameBoard.decksInitializer();
    }

    @Test
    public void checkCorrectColorAndLevelInEachDeck() throws FileNotFoundException {
        GameBoard gameBoard = new GameBoard("name");
        gameBoard.decksInitializer();

        //checking if the deck color is correct
        for(int row=0; row<3;row++){
            assertEquals(Color.Green,gameBoard.getDevelopmentCardDecks()[row][0].getDeckColor());
            assertEquals(Color.Blue,gameBoard.getDevelopmentCardDecks()[row][1].getDeckColor());
            assertEquals(Color.Yellow,gameBoard.getDevelopmentCardDecks()[row][2].getDeckColor());
            assertEquals(Color.Purple,gameBoard.getDevelopmentCardDecks()[row][3].getDeckColor());
        }

        //checking if the deck level is correct
        for(int column=0;column<4;column++){
            assertEquals(3,gameBoard.getDevelopmentCardDecks()[0][column].getDeckLevel());
            assertEquals(2,gameBoard.getDevelopmentCardDecks()[1][column].getDeckLevel());
            assertEquals(1,gameBoard.getDevelopmentCardDecks()[2][column].getDeckLevel());
        }

        //checking that each card is contained in the right deck (card color and level must be the same of the deck)
        for(int i=0;i<3;i++){
            for(int j=0;j<4;j++){
                for(int s=0;s<4;s++){
                    //System.out.println("Colore Deck: " + gameBoard.getDevelopmentCardDecks()[i][j].getDeckColor() + " e colore carta " + gameBoard.getDevelopmentCardDecks()[i][j].getDeck().get(s).getCardStats().getValue1());
                    //System.out.println("Livello Deck: " + gameBoard.getDevelopmentCardDecks()[i][j].getDeckLevel() + " e livello carta " + gameBoard.getDevelopmentCardDecks()[i][j].getDeck().get(s).getCardStats().getValue0());
                    assertEquals(gameBoard.getDevelopmentCardDecks()[i][j].getDeckColor(),gameBoard.getDevelopmentCardDecks()[i][j].getDeck().get(s).getCardStats().getValue1());
                    assertEquals(gameBoard.getDevelopmentCardDecks()[i][j].getDeckLevel(),gameBoard.getDevelopmentCardDecks()[i][j].getDeck().get(s).getCardStats().getValue0());
                }
            }
        }
    }


    @Test
    public void geneticMethodsTest() throws OutOfBoundException, LorenzoWonTheMatch, LorenzoActivatesPapalCardException, BothPlayerAndLorenzoActivatePapalCardException {
        GameBoard gameBoard= new GameBoard("Caloggero");
        Player player= new Player("Baranov", gameBoard);
        assertTrue(gameBoard.checkNumOfBlank(true,1)>-1);
        assertTrue(gameBoard.drawCard() instanceof LeaderCard);
        gameBoard.getStringMarket();
        gameBoard.getPlayerFromNickname("a");
        gameBoard.playersSize();
        assertTrue(gameBoard.deckSize(Color.Blue,1)==4);
        gameBoard.deckSize(Color.Blue,1);
        gameBoard.getFirstCardCopy(Color.Blue,1);
        assertTrue(gameBoard.floatingMarbleType() instanceof ResourceType);
        assertTrue(gameBoard.resourceTypeInMarket(1,1) instanceof ResourceType);
        assertEquals("Caloggero", gameBoard.getNickname(0));
        assertEquals("Caloggero", gameBoard.playerName(0));
        assertEquals(0, gameBoard.getFaith(0));
        assertEquals(0, gameBoard.playerVictoryPoints(0));
        assertEquals(gameBoard.isSinglePlayer(), true);
        assertTrue(gameBoard.playLorenzo() instanceof Token);
        assertTrue(gameBoard.getLorenzoIlMagnifico() instanceof LorenzoIlMagnifico);
        assertEquals(gameBoard.checkGameIsEnded(), false);
        gameBoard.getDevelopmentCardDeck(1,1);
        gameBoard.getMarket();
        gameBoard.endTurn("Caloggero");
        gameBoard.getDeckOfChoice(Color.Purple,1);
    }
}
