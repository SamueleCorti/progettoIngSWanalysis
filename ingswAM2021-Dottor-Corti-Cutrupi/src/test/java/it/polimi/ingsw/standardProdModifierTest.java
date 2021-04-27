package it.polimi.ingsw;

import it.polimi.ingsw.developmentcard.DevelopmentCardModifier;
import org.junit.Test;

import java.io.FileNotFoundException;

public class standardProdModifierTest {
    /**
     * checks if the values are read correctly from json
     * @throws FileNotFoundException
     */
    @Test
    public void test1() throws FileNotFoundException {
        StandardProdModifier func1 = new StandardProdModifier();
        func1.importValues();
        func1.printValues();
    }
}
