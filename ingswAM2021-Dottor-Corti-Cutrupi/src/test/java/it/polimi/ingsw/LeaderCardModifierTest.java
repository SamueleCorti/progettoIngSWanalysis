package it.polimi.ingsw;

import it.polimi.ingsw.developmentcard.DevelopmentCardModifier;
import it.polimi.ingsw.leadercard.LeaderCardModifier;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LeaderCardModifierTest {

    /**
     * this test checks if the cards were imported correctly
     * @throws FileNotFoundException
     */
    @Test
    public void test1() throws FileNotFoundException {
        LeaderCardModifier func1 = new LeaderCardModifier();
        func1.importCards();
        func1.writeCardsInJson();
    }

    /**
     * this test checks if the methods to change the card values work properly
     */
    @Test
    public void test2() throws FileNotFoundException {
        LeaderCardModifier func1 = new LeaderCardModifier();
        func1.importCards();
        System.out.println("card before operations:");
        func1.writeCardsInJson();
        func1.changeRequirementType(0);
        assertEquals("resources",func1.getListOfCards().get(0).getTypeOfRequirement());
    }


}
