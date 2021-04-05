package it.polimi.ingsw;

import it.polimi.ingsw.developmentcard.Color;
import it.polimi.ingsw.developmentcard.DevelopmentCardDeck;
import it.polimi.ingsw.market.Market;

public class GameBoard {

    private Market market;
    private DevelopmentCardDeck[][] developmentCardDecks= new DevelopmentCardDeck[3][4];

    public GameBoard(){
        market= new Market();

    }

    public void discardTokenEffect(Color developmentCardColor){
        //also ends the game if Lorenzo is playing and a certain color of development card is no longer present
    }

    public Market getMarket(){
        return market;
    }

}
