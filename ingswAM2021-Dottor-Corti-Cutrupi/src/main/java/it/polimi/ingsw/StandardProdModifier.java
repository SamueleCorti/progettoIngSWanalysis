package it.polimi.ingsw;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.leadercard.LeaderCardForJson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * this class is used to change the standard prod values
 */
public class StandardProdModifier {
    /*arraylist that contains the 2 values required: the number of resources consumed by the standard prod
    *  and the number of resources produced
    */
    private ArrayList<Integer> values;

    public void importValues() throws FileNotFoundException {
        //part where we import the values from json
        JsonReader reader = new JsonReader(new FileReader("standardprodParameters.json"));
        JsonParser parser = new JsonParser();
        JsonArray cardsArray = parser.parse(reader).getAsJsonArray();
        for(JsonElement jsonElement : cardsArray) {
            Gson gson = new Gson();
            Integer valueRecreated = gson.fromJson(jsonElement.getAsJsonObject(), Integer.class);
            this.values.add(valueRecreated);
        }
    }

    /**
     * this method changes the number of resources consumed by the standard prod
     */
    public void changeNumOfResourcesRequired(int newResourcesRequired){
        this.values.set(0,newResourcesRequired);
    }

    /**
     * this method changes the number of resources produced by the standard prod
     */
    public void changeNumOfResourcesProduced(int newResourcesProduced){
        this.values.set(0,newResourcesProduced);
    }

    /**
     * this method just print the values to screen
     */
    public void printValues(){
            System.out.println("list of tiles:");
            Gson listOfCardsGson = new GsonBuilder().setPrettyPrinting().create();
            String listJson = listOfCardsGson.toJson(this.values);
            System.out.println(listJson);
    }
    /**
     * this method re writes the updated values to json
     */
    public void writeValuesInJson(){
        System.out.println("list of values to write in json:");
        Gson listOfCardsGson = new GsonBuilder().setPrettyPrinting().create();
        String listJson = listOfCardsGson.toJson(this.values);
        System.out.println(listJson);
        try (FileWriter file = new FileWriter("standardProdParameters")) {
            file.write(listJson);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
