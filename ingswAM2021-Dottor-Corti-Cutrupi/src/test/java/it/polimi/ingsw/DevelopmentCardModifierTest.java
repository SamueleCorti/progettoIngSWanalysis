package it.polimi.ingsw;

import it.polimi.ingsw.developmentcard.DevelopmentCardModifier;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DevelopmentCardModifierTest {
    //here we check if the cards are readed correctly
    @Test
    public void test1() throws FileNotFoundException {
        DevelopmentCardModifier func1 = new DevelopmentCardModifier();
        func1.importCards();
        func1.writeCardsInJson();
    }

    //here we check if the operations work properly
    @Test
    public void test2() throws FileNotFoundException {
        DevelopmentCardModifier func1 = new DevelopmentCardModifier();
        func1.importCards();
        System.out.println("card before operations:");
        func1.writeCardsInJson();

        func1.changePriceOfCard(0,0,25,"stone");
        assertEquals(25,func1.getListOfCards().get(0).getAmountOfForPrice().get(0));

        func1.addPriceToCard(1,23,"servant");
        func1.removePriceFromCard(1,0);
        assertEquals("servant",func1.getListOfCards().get(1).getTypeOfResourceForPrice().get(0));

        func1.changeCardColor(0,"yellow");
        assertEquals("yellow",func1.getListOfCards().get(0).getColor());

        func1.changeCardVictoryPoints(0,30);
        assertEquals(30,func1.getListOfCards().get(0).getVictoryPoints());

        func1.removeProdResultFromCard(0,0);
        func1.addProdResultOfCard(0,200,"stone");
        func1.changeProdResultOfCard(0,0,25,"coin");
        assertEquals(25,func1.getListOfCards().get(0).getAmountOfForProdResults().get(0));

        func1.addProdRequirementOfCard(1,23,"servant");
        func1.removeProdRequirementFromCard(1,1);
        func1.removeProdRequirementFromCard(1,0);
        func1.addProdRequirementOfCard(1,15,"coin");
        func1.changeProdRequirementOfCard(1,0,3,"shield");
        assertEquals("shield",func1.getListOfCards().get(1).getTypeOfResourceForProdRequirements().get(0));

        System.out.println("card after operations");
        func1.writeCardsInJson();
    }
}
