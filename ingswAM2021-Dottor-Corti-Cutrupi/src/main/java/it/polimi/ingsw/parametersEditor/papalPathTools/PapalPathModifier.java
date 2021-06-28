package it.polimi.ingsw.parametersEditor.papalPathTools;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * this class is used to change the Papal Path track values in the json file where its values are stored
 */
public class PapalPathModifier {
    private ArrayList<PapalPathTileFA> tileList;
    private ArrayList <Integer> victoryPointsOfFavorCards;



    public PapalPathModifier(){
        this.tileList=new ArrayList<PapalPathTileFA>();
        victoryPointsOfFavorCards=new ArrayList<Integer>();
    }

    public ArrayList<PapalPathTileFA> getTileList() {
        return tileList;
    }

    /**
     * this method imports all the values of the papal path from json
     */
    public void importTiles() throws FileNotFoundException {
        //part where we import all the papal path tiles from json
        JsonReader reader = new JsonReader(new FileReader("src/main/resources/papalpathtilesFA.json"));
        JsonParser parser = new JsonParser();
        JsonArray tilesArray = parser.parse(reader).getAsJsonArray();
        for(JsonElement jsonElement : tilesArray) {
            Gson gson = new Gson();
            PapalPathTileFA tileRecreated = gson.fromJson(jsonElement.getAsJsonObject(), PapalPathTileFA.class);
            this.tileList.add(tileRecreated);
        }
    }

    /**
     * this method imports the values of the Favor Cards from json
     */
    public void importFavorCards(){
        int i = 0;
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader("src/main/resources/favorcardsFA.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        JsonParser parser = new JsonParser();
        JsonArray tilesArray = parser.parse(reader).getAsJsonArray();

            Gson gson = new Gson();

            int[] arr = gson.fromJson(tilesArray, int[].class);
            this.victoryPointsOfFavorCards.add(arr[0]);
            this.victoryPointsOfFavorCards.add(arr[1]);
            this.victoryPointsOfFavorCards.add(arr[2]);

    }

    /**
     * this method sets the victory points of a tile
     */
    public void setVictoryPoints (int tileIndex,int victoryPointsToSet) {
        this.tileList.get(tileIndex).setVictoryPoints(victoryPointsToSet);
    }
    public void setFavorCardsVictoryPoints(int favorCardIndex,int victoryPointsToSet){
        this.victoryPointsOfFavorCards.set(favorCardIndex,victoryPointsToSet);
    }

    /**
     * this method changes if a tile is a Pope Space or not
     */
    public void changeIsPopeSpace(int tileIndex, boolean isPopeSpace){
        this.tileList.get(tileIndex).setPopeSpace(isPopeSpace);
    }

    /**
     * this method changes the num of report section of a tile
     */
    public void changeNumOfReportSection(int tileIndex,int numToSet){
        this.tileList.get(tileIndex).setNumOfReportSection(numToSet);
    }
    /**
     * this method prints the cards to screen
     */
    public void printTiles(){
        //IT JUST PRINTS THE tiles TO SCREEN
        //BEFORE DOING THIS WE SHOULD CHECK IF THE CONFIGURATION IS CORRECT
        System.out.println("list of tiles:");
        Gson listOfCardsGson = new GsonBuilder().setPrettyPrinting().create();
        String listJson = listOfCardsGson.toJson(this.tileList);
        System.out.println(listJson);
    }

    public void printFavorValues(){
        System.out.println("list of values:");
        ArrayList <Integer> array = new ArrayList<Integer>();
        array = this.victoryPointsOfFavorCards;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String favorJson = gson.toJson(array);
        System.out.println(favorJson);
    }

    /**
     * we use this method to re write the list of tiles in json
     */
    public void writeCardsInJson() {
        Gson listOfCardsGson = new GsonBuilder().setPrettyPrinting().create();
        String listJson = listOfCardsGson.toJson(this.tileList);
        try (FileWriter file = new FileWriter("src/main/resources/papalpathtilesFA.json")) {
            file.write(listJson);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * this method writes the favor cards in back in json
     */
    public void writeFavorCardsInJson(){
        ArrayList <Integer> array = new ArrayList<Integer>();
        array = this.victoryPointsOfFavorCards;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String favorJson = gson.toJson(array);
        try (FileWriter file = new FileWriter("src/main/resources/favorcardsFA.json")) {
            file.write(favorJson);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Integer> getVictoryPointsOfFavorCards() {
        return victoryPointsOfFavorCards;
    }
}
