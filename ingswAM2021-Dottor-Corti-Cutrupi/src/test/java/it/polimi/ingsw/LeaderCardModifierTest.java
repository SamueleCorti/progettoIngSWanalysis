package it.polimi.ingsw;

import it.polimi.ingsw.developmentcard.DevelopmentCardModifier;
import it.polimi.ingsw.leadercard.LeaderCardModifier;
import org.junit.Test;

import java.io.FileNotFoundException;

public class LeaderCardModifierTest {

    /**this test checks if the cards were imported correctly
     *
     * @throws FileNotFoundException
     */
    @Test
    public void test1() throws FileNotFoundException {
        LeaderCardModifier func1 = new LeaderCardModifier();
        func1.importCards();
        func1.writeCardsInJson();
    }

}
