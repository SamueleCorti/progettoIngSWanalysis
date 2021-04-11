package it.polimi.ingsw.LorenzoIlMagnifico;

import it.polimi.ingsw.Dashboard;
import it.polimi.ingsw.GameBoard;
import it.polimi.ingsw.developmentcard.Color;
import it.polimi.ingsw.papalpath.PapalPath;

public class DiscardToken implements Token{
    Color color;

    //this token discards two devCards of its color, and if that color doesn't have any cards left, Lorenzo wins the game
    public DiscardToken(Color color) {
        this.color = color;
    }


    public void tokenEffect(PapalPath papalPath, LorenzoIlMagnifico lorenzoIlMagnifico, GameBoard gameBoard){
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
            gameBoard.lorenzoDevelopmentWin();
        }
    }

    @Override
    public String toString() {
        return "Discard "+this.color;
    }
}
