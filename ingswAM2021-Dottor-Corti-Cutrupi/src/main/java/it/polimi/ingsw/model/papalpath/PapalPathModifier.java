package it.polimi.ingsw.model.papalpath;

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
    private ArrayList<PapalPathTile> tileList;
    private ArrayList <Integer> victoryPointsOfFavorCards;



    public PapalPathModifier(){
        this.tileList=new ArrayList<PapalPathTile>();
        victoryPointsOfFavorCards=new ArrayList<Integer>();
    }

    public void addTile(PapalPathTile tileToAdd){
        this.tileList.add(tileToAdd);
    }

    public ArrayList<PapalPathTile> getTileList() {
        return tileList;
    }

    public void importTiles() throws FileNotFoundException {
        //part where we import all the papal path tiles from json
        JsonReader reader = new JsonReader(new FileReader("C:\\Users\\Sam\\Desktop\\Progetto ingegneria del software\\papalpathtiles.json"));
        JsonParser parser = new JsonParser();
        JsonArray tilesArray = parser.parse(reader).getAsJsonArray();
        for(JsonElement jsonElement : tilesArray) {
            Gson gson = new Gson();
            PapalPathTile tileRecreated = gson.fromJson(jsonElement.getAsJsonObject(), PapalPathTile.class);
            this.tileList.add(tileRecreated);
        }
    }

    public void importFavorCards(){
        int i = 0;
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader("favorcards.json"));
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
    public void changeIsPopeSpace(int tileIndex){
        if(this.tileList.get(tileIndex).isPopeSpace()==true){
            this.tileList.get(tileIndex).setPopeSpace(false);
        }else if(this.tileList.get(tileIndex).isPopeSpace()==false){
            this.tileList.get(tileIndex).setPopeSpace(true);
        }
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
        //BEFORE DOING THIS WE SHOULD CHECK IF THE CONFIGURATION IS CORRECT
        Gson listOfCardsGson = new GsonBuilder().setPrettyPrinting().create();
       // System.out.println("cards that we want to print to file:");
        String listJson = listOfCardsGson.toJson(this.tileList);
        //System.out.println(listJson);
        //THE FILE DESTINATION WILL HAVE TO BE CHANGED
        try (FileWriter file = new FileWriter("C:\\Users\\Sam\\Desktop\\Progetto ingegneria del software\\papalpathtilesmodified.json")) {
            file.write(listJson);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void writeFavorCardsInJson(){
        //BEFORE DOING THIS WE SHOULD CHECK IF THE CONFIGURATION IS CORRECT
        ArrayList <Integer> array = new ArrayList<Integer>();
        array = this.victoryPointsOfFavorCards;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String favorJson = gson.toJson(array);
        //THE FILE DESTINATION WILL HAVE TO BE CHANGED
        try (FileWriter file = new FileWriter("favorcards.json")) {
            file.write(favorJson);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}