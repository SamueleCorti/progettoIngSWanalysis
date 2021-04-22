package it.polimi.ingsw;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.papalpath.PapalPathModifier;
import it.polimi.ingsw.papalpath.PapalPathTile;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

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
        funz1.writeCardsInJson();
    }
}
