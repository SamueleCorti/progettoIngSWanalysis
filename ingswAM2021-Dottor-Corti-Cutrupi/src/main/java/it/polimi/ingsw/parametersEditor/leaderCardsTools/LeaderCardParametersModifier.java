package it.polimi.ingsw.parametersEditor.leaderCardsTools;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class LeaderCardParametersModifier {
    //number of resources consumed by the standard prod
    private int numOfLeaderCardsGiven;
    //number of resources produced by the standard prod
    private int numOfLeaderCardsKept;

    /**
     * this method imports values from json
     */
    public void importValues(){
        //part where we import the values from json
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader("src/main/resources/leadercardsparametersFA.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        JsonParser parser = new JsonParser();
        JsonArray tilesArray = parser.parse(reader).getAsJsonArray();
        Gson gson = new Gson();
        int[] arr = gson.fromJson(tilesArray, int[].class);
        this.numOfLeaderCardsGiven = arr[0];
        this.numOfLeaderCardsKept = arr[1];
    }

    public int getNumOfLeaderCardsGiven() {
        return numOfLeaderCardsGiven;
    }

    public int getNumOfLeaderCardsKept() {
        return numOfLeaderCardsKept;
    }

    public void setNumOfLeaderCardsGiven(int numOfLeaderCardsGiven) {
        this.numOfLeaderCardsGiven = numOfLeaderCardsGiven;
    }

    public void setNumOfLeaderCardsKept(int numOfLeaderCardsKept) {
        this.numOfLeaderCardsKept = numOfLeaderCardsKept;
    }

    public void increaseNumOfLeaderCardsGiven(){
        this.numOfLeaderCardsGiven +=1;
    }
    public void decreaseNumOfLeaderCardsGiven(){
        this.numOfLeaderCardsGiven -=1;
    }
    public void increaseNumOfLeaderCardsKept(){
        this.numOfLeaderCardsKept +=1;
    }
    public void decreaseNumOfLeaderCardsKept(){
        this.numOfLeaderCardsKept -=1;
    }



    /**
     * this method re writes the updated values to json
     */
    public void writeValuesInJson(){
        int[] temp = new int[2];

        temp[0]= numOfLeaderCardsGiven;
        temp[1]= numOfLeaderCardsKept;

        Gson listOfCardsGson = new GsonBuilder().setPrettyPrinting().create();
        String listJson = listOfCardsGson.toJson(temp);

        try (FileWriter file = new FileWriter("src/main/resources/leadercardsparametersFA.json")) {
            file.write(listJson);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}