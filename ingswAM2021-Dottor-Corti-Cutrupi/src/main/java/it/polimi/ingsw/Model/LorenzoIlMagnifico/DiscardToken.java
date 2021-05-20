package it.polimi.ingsw.Model.LorenzoIlMagnifico;

import it.polimi.ingsw.Exceptions.LorenzoWonTheMatch;
import it.polimi.ingsw.Model.boardsAndPlayer.GameBoard;
import it.polimi.ingsw.Model.developmentcard.Color;
import it.polimi.ingsw.Model.papalpath.PapalPath;

/**
 * Tokens tht discard 2 dev card
 */
public class DiscardToken implements Token{
    Color color;

    //this token discards two devCards of its color, and if that color doesn't have any cards left, Lorenzo wins the game
    public DiscardToken(Color color) {
        this.color = color;
    }

    /**
     * The token discard 2 dev card of its color, starting from tier1, going all the wat to tier 3. If a certain color of dev card is no longer present on the
     * gameboard, the game ends with Lorenzo's victory
     */
    public void tokenEffect(PapalPath papalPath, LorenzoIlMagnifico lorenzoIlMagnifico, GameBoard gameBoard) throws LorenzoWonTheMatch {
        int indexToDiscardFrom=0;
        if (color.equals(Color.Green))       indexToDiscardFrom=0;
        if (color.equals(Color.Blue))        indexToDiscardFrom=1;
        if (color.equals(Color.Yellow))      indexToDiscardFrom=2;if (color.equals(Color.Purple))      indexToDiscardFrom=3;
        int cardsToDiscard=2;
        int level=2;
        //correctly checks  every deck, starting from the lower level, and goes on until it discards 2 cards. If there aren't any more cards
        // when it finishes, the game ends
        while(cardsToDiscard>0 && level>=0){
            if(gameBoard.getDevelopmentCardDecks()[level][indexToDiscardFrom].deckSize()>0)    {
                cardsToDiscard--;
                gameBoard.getDevelopmentCardDecks()[level][indexToDiscardFrom].removeCard();}
            else level--;
        }
        if(gameBoard.getDevelopmentCardDecks()[0][indexToDiscardFrom].deckSize()==0 &&
                gameBoard.getDevelopmentCardDecks()[1][indexToDiscardFrom].deckSize()==0 &&
                gameBoard.getDevelopmentCardDecks()[2][indexToDiscardFrom].deckSize()==0)    {
            throw new LorenzoWonTheMatch();
        }
    }

    @Override
    public String toString() {
        return "Discard "+this.color;
    }
}
