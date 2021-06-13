package it.polimi.ingsw;

import it.polimi.ingsw.adapters.StandardProdModifier;
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

    /**
     * checks if the files are correctly modified by the function
     * @throws FileNotFoundException
     */
    @Test
    public void test2() throws FileNotFoundException {
        StandardProdModifier func1 = new StandardProdModifier();
        func1.importValues();
        func1.changeNumOfResourcesProduced(20);
        func1.changeNumOfResourcesRequired(3);
        func1.printValues();
    }


}
