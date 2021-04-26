package it.polimi.ingsw;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.papalpath.PapalPathModifier;
import it.polimi.ingsw.papalpath.PapalPathTile;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PapalPathModifierTest {

    /**
     * test used to create a prototype of PapalPathTile in Json
     */
    @Test
    public void test1(){
        PapalPathModifier funz1 = new PapalPathModifier();
        PapalPathTile tile1 = new PapalPathTile(4,2,true);
        funz1.getTileList().add(tile1);
        funz1.getTileList().add(tile1);
        funz1.getTileList().add(tile1);
        Gson cardGson = new GsonBuilder().setPrettyPrinting().create();
        String listOfTilesJson = cardGson.toJson(funz1.getTileList());
        System.out.println(listOfTilesJson);
    }

    /**
     * test used to check if the tiles are correctly imported from Json
     */
    @Test
    public void test2() throws FileNotFoundException {
        PapalPathModifier funz1 = new PapalPathModifier();
        funz1.importTiles();
        funz1.printTiles();
    }

    /**
     * this tests is used to see if the PapalPathModifier methods work properly
     */
    @Test
    public void test3() throws FileNotFoundException {
        PapalPathModifier funz1 = new PapalPathModifier();
        funz1.importTiles();
        funz1.setVictoryPoints(0,150);
        assertEquals(150,funz1.getTileList().get(0).getVictoryPoints());
        funz1.changeIsPopeSpace(0);
        assertEquals(true,funz1.getTileList().get(0).isPopeSpace());
        funz1.changeIsPopeSpace(8);
        assertEquals(false,funz1.getTileList().get(8).isPopeSpace());
        funz1.changeNumOfReportSection(15,10);
        assertEquals(10,funz1.getTileList().get(15).getNumOfReportSection());
    }
    /**
     * this test checks if the writeCardsInJson method is working
     */
    @Test
    public void test4() throws IOException {
        PapalPathModifier funz1 = new PapalPathModifier();
        funz1.importTiles();
        funz1.setVictoryPoints(0,150);
        funz1.changeIsPopeSpace(0);
        funz1.changeIsPopeSpace(8);
        funz1.changeNumOfReportSection(15,10);
        funz1.writeCardsInJson();
    }
}
