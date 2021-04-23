package it.polimi.ingsw.papalpath;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.leadercard.LeaderCardForJson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * this class is used to change the Papal Path track values in the json file where its values are stored
 */
public class PapalPathModifier {
    private ArrayList<PapalPathTile> tileList;

    public PapalPathModifier(){
        this.tileList=new ArrayList<PapalPathTile>();
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

    /**
     * this method sets the victory points of a tile
     */
    public void setVictoryPoints (int tileIndex,int victoryPointsToSet) {
        this.tileList.get(tileIndex).setVictoryPoints(victoryPointsToSet);
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
     * we use this method to re write the list of tiles in json
     */
    public void writeCardsInJson(){
        //FOR NOW IT JUST PRINTS THE tiles TO SCREEN
        //BEFORE DOING THIS WE SHOULD CHECK IF THE CONFIGURATION IS CORRECT
        System.out.println("list of tiles:");
        Gson listOfCardsGson = new GsonBuilder().setPrettyPrinting().create();
        String listJson = listOfCardsGson.toJson(this.tileList);
        System.out.println(listJson);
    }

}
