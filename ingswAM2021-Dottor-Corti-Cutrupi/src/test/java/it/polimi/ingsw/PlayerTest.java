package it.polimi.ingsw;

import it.polimi.ingsw.Exceptions.NotEnoughResourcesException;
import it.polimi.ingsw.developmentcard.Color;
import it.polimi.ingsw.developmentcard.DevelopmentCard;
import it.polimi.ingsw.resource.*;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

public class PlayerTest {
    GameBoard gameBoard= new GameBoard();
    Player player= new Player("Lopez",1,gameBoard);

    @Test
    public void cardAcquisitionTest() throws FileNotFoundException, NotEnoughResourcesException {
        gameBoard.decksInitializer();
        for(int i=0;i<1;i++){
            player.getDashboard().getStrongbox().addResource(new CoinResource());
            player.getDashboard().getStrongbox().addResource(new ServantResource());
            player.getDashboard().getStrongbox().addResource(new StoneResource());
            player.getDashboard().getStrongbox().addResource(new ShieldResource());
        }
        player.buyDevelopmentCard(Color.Green,1, player.getDashboard().getDevelopmentCardZones().get(1));
        System.out.println(player.getDashboard().getDevelopmentCardZones().get(1).getFirstCard());
        System.out.println("Coin: "+ player.getDashboard().getStrongbox().amountOfResource(new CoinResource()));
        System.out.println("Stone: "+ player.getDashboard().getStrongbox().amountOfResource(new StoneResource()));
        System.out.println("Shield: "+ player.getDashboard().getStrongbox().amountOfResource(new ShieldResource()));
        System.out.println("Servant: "+ player.getDashboard().getStrongbox().amountOfResource(new ServantResource()));
    }
}
