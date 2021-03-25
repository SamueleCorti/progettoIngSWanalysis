package it.polimi.ingsw;
import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.developmentcard.Color;
import it.polimi.ingsw.developmentcard.Deck;
import it.polimi.ingsw.developmentcard.DevelopmentCard;
import it.polimi.ingsw.developmentcard.DevelopmentCardZone;
import it.polimi.ingsw.resource.CoinResource;
import it.polimi.ingsw.resource.Resource;
import it.polimi.ingsw.resource.StoneResource;
import org.javatuples.Pair;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class DevelopmentCardTest {

    CoinResource coin1=new CoinResource();
    StoneResource stone1=new StoneResource();
    /*List <Integer> integerList = new ArrayList<Integer>(){{
        add(1);
        add(3);
    } };

    List <Resource> resourceList = new ArrayList<Resource>(){{
        add(coin1);
        add(stone1);
    } };
    List <Color> colorList = new ArrayList<Color>(){{
        add(Color.Yellow);
        add(Color.Blue);
    } };;
    Pair <List <Integer>,List <Resource>> price=new Pair(integerList,resourceList);
    Pair <List <Integer>,List <Color>> cardStats=new Pair(integerList,colorList);
    Pair <List <Integer>,List <Resource>> prodRequirements=new Pair(integerList,resourceList);
    Pair <List <Integer>,List <Resource>> prodResults=new Pair(integerList,resourceList);
    DevelopmentCard card1=new DevelopmentCard(price,cardStats,prodRequirements,prodResults,6);
    DevelopmentCard card2=new DevelopmentCard(price,cardStats,prodRequirements,prodResults,3);
    DevelopmentCard card3=new DevelopmentCard(price,cardStats,prodRequirements,prodResults,9);
    DevelopmentCardZone zone1 = new DevelopmentCardZone();
    Deck deck1 = new Deck();

    // here we check if the victory points are working properly
    @Test
    public void testVictoryPoints(){
        assertEquals(6,card1.getVictoryPoints());
    }
    // here we check if the cards are correctly added in the DevelopmentCardZone, and if the calculateVictoryPoints works properly
    @Test
    public void testDevelopmentCardZone(){
        zone1.addNewCard(card1);
        zone1.addNewCard(card2);
        zone1.addNewCard(card3);
        assertEquals(18,zone1.calculateVictoryPoints());
    }
    //here we check if the deck methods and structure are working properly
    @Test
    public void testDeck(){
        deck1.addNewCard(card1);
        deck1.addNewCard(card2);
        deck1.addNewCard(card3);

        assertEquals(6, deck1.getFirstCard().getVictoryPoints());

        deck1.drawCard();
        assertEquals(3, deck1.getFirstCard().getVictoryPoints());

        deck1.drawCard();
        assertEquals(9, deck1.getFirstCard().getVictoryPoints());

        assertEquals(1, deck1.getFirstCard().getPrice().getValue0().get(0));

        assertEquals(Color.Blue, deck1.getFirstCard().getCardStats().getValue1().get(1));

        deck1.addNewCard(card1);
        deck1.addNewCard(card2);
        deck1.addNewCard(card1);
        deck1.addNewCard(card2);
        deck1.shuffle();
        //since the order is now random, i cant use asserts, so i print the results (in this case i check if victory points are ordered casually)
        for(int i=0;i<deck1.deckSize();){
            System.out.print(deck1.getFirstCard().getVictoryPoints());
            System.out.println();
            deck1.drawCard();
        }

    }*/
}
