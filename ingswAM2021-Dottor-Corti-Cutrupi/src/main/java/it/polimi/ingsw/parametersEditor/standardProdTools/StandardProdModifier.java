package it.polimi.ingsw.parametersEditor.standardProdTools;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.adapters.DirHandler;

import java.io.*;

/**
 * this class is used to change the standard prod values
 */
public class StandardProdModifier {
    //number of resources consumed by the standard prod
    private int numOfStandardProdRequirements;
    //number of resources produced by the standard prod
    private int numOfStandardProdResults;

    /**
     * this method imports values from json
     */
    public void importValues(){
        //part where we import the values from json
        JsonReader reader = null;

        DirHandler dirHandler = new DirHandler();
        try {
            reader = new JsonReader(new FileReader(dirHandler.getWorkingDirectory() + "/json/standardprodParametersFa.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        JsonParser parser = new JsonParser();
        JsonArray tilesArray = parser.parse(reader).getAsJsonArray();
        Gson gson = new Gson();
        int[] arr = gson.fromJson(tilesArray, int[].class);
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

    public void increaseNumOfStandardProdRequirements(){
        this.numOfStandardProdRequirements+=1;
    }
    public void decreaseNumOfStandardProdRequirements(){
        this.numOfStandardProdRequirements-=1;
    }
    public void increaseNumOfStandardProdResults(){
        this.numOfStandardProdResults+=1;
    }
    public void decreaseNumOfStandardProdResults(){
        this.numOfStandardProdResults-=1;
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

        Gson listOfCardsGson = new GsonBuilder().setPrettyPrinting().create();
        String listJson = listOfCardsGson.toJson(temp);

        DirHandler dirHandler = new DirHandler();
        try {
            Writer writer = new FileWriter(dirHandler.getWorkingDirectory() + "/json/standardProdParametersFA.json");
            writer.write(listJson);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
