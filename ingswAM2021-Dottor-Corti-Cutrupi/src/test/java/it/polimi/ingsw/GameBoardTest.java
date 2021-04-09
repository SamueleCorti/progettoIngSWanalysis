package it.polimi.ingsw;

import it.polimi.ingsw.developmentcard.DevelopmentCard;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

public class GameBoardTest {

    @Test
    public void firstTest() throws FileNotFoundException {
        GameBoard gameBoard = new GameBoard();
        gameBoard.decksInitializer();
        for(DevelopmentCard card : gameBoard.getDevelopmentCardDecks()[0][0].getDeck()){
            System.out.println(card.toString());
        }
    }

}
