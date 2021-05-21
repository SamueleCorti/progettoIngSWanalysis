package it.polimi.ingsw.model.adapters;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * this class is used to change the standard prod values
 */
public class StandardProdModifier {
    //number of resources consumed by the standard prod
    private int numOfStandardProdRequirements;
    //number of resources produced by the standard prod
    private int numOfStandardProdResults;

    public void importValues() throws FileNotFoundException {
        //part where we import the values from json
        JsonReader reader = new JsonReader(new FileReader("standardprodParameters.json"));
        JsonParser parser = new JsonParser();
        JsonArray cardsArray = parser.parse(reader).getAsJsonArray();
        Gson gson = new Gson();
        int[] arr = gson.fromJson(cardsArray, int[].class);
        this.numOfStandardProdRequirements=arr[0];
        this.numOfStandardProdResults=arr[1];
    }

    public int getNumOfStandardProdRequirements() {
        return numOfStandardProdRequirements;
    }

    public int getNumOfStandardProdResults() {
        return numOfStandardProdResults;
    }

    public void setNumOfStandardProdRequirements(int numOfStandardProdRequirements) {
        this.numOfStandardProdRequirements = numOfStandardProdRequirements;
    }

    public void setNumOfStandardProdResults(int numOfStandardProdResults) {
        this.numOfStandardProdResults = numOfStandardProdResults;
    }

    /**
     * this method changes the number of resources consumed by the standard prod
     */
    public void changeNumOfResourcesRequired(int newResourcesRequired){
        this.numOfStandardProdRequirements = newResourcesRequired;
    }

    /**
     * this method changes the number of resources produced by the standard prod
     */
    public void changeNumOfResourcesProduced(int newResourcesProduced){
        this.numOfStandardProdResults=newResourcesProduced;
    }

    /**
     * this method just print the values to screen
     */
    public void printValues(){
        int[] temp = new int[2];
        temp[0]=numOfStandardProdRequirements;
        temp[1]=numOfStandardProdResults;
        System.out.println("values");
        Gson listOfCardsGson = new GsonBuilder().setPrettyPrinting().create();
        String listJson = listOfCardsGson.toJson(temp);
        System.out.println(listJson);
    }
    /**
     * this method re writes the updated values to json
     */
    public void writeValuesInJson(){
        int[] temp = new int[2];

        temp[0]=numOfStandardProdRequirements;
        temp[1]=numOfStandardProdResults;

        System.out.println("list of values to write in json:");
        Gson listOfCardsGson = new GsonBuilder().setPrettyPrinting().create();
        String listJson = listOfCardsGson.toJson(temp);
        System.out.println(listJson);

        try (FileWriter file = new FileWriter("standardProdParameters")) {
            file.write(listJson);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
