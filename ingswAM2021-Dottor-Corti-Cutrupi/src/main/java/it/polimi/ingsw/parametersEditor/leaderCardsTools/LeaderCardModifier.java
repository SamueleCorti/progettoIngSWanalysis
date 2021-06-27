package it.polimi.ingsw.parametersEditor.leaderCardsTools;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class LeaderCardModifier {

    private ArrayList<LeaderCardForFA> listOfCards;

    public ArrayList<LeaderCardForFA> getListOfCards() {
        return listOfCards;
    }
    public LeaderCardModifier() {
        this.listOfCards = new ArrayList <LeaderCardForFA>();
    }

    /**
     * this method imports all the leader cards from json into the arraylist of this class
     * @throws FileNotFoundException
     */
    public void importCards() {
        //part where we import all the cards from json
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader("src/main/resources/LeaderCardsInstancingFA.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //index of the card
        int i = 0;

        JsonParser parser = new JsonParser();
        JsonArray cardsArray = parser.parse(reader).getAsJsonArray();
        for(JsonElement jsonElement : cardsArray) {
            Gson gson = new Gson();
            LeaderCardForFA cardRecreated = gson.fromJson(jsonElement.getAsJsonObject(), LeaderCardForFA.class);
            cardRecreated.initializePropertiesForTableView(i);
            this.listOfCards.add(cardRecreated);
            i++;
        }
    }


    /**
     * this method changes the type of requirement of the card from Development Cards to Resources,
     * or the other way around.
     * @param cardIndex
     */
    public void changeRequirementType(int cardIndex){
        if (this.listOfCards.get(cardIndex).getTypeOfRequirement().equals("development")) {
            this.listOfCards.get(cardIndex).setTypeOfRequirement("resources");
        }else if(this.listOfCards.get(cardIndex).getTypeOfRequirement().equals("resources")){
            this.listOfCards.get(cardIndex).setTypeOfRequirement("development");
        }
        this.listOfCards.get(cardIndex).setWasCardModified(true);
    }


    /**
     * this method changes the resources needed to activate the leader power
     * @param cardIndex index of the card we want to make the operation on
     * @param requirementIndex
     * @param quantity
     * @param resource
     */
    public void changeResourcesRequirementOfCard(int cardIndex,int requirementIndex,int quantity, String resource){
        this.listOfCards.get(cardIndex).getAmountOfForResourcesRequirement().set(requirementIndex,quantity);
        this.listOfCards.get(cardIndex).getResourcesRequired().set(requirementIndex,resource);
        this.listOfCards.get(cardIndex).setWasCardModified(true);
    }

    /**
     * this method adds a requirement to the list of the resources requirements of the card
     * @param cardIndex index of the card we want to make the operation on
     * @param quantity
     * @param resource
     */
    public void addResourcesRequirementOfCard(int cardIndex,int quantity, String resource ) {
        this.listOfCards.get(cardIndex).getAmountOfForResourcesRequirement().add(quantity);
        this.listOfCards.get(cardIndex).getResourcesRequired().add(resource);
        this.listOfCards.get(cardIndex).setWasCardModified(true);
    }

    /**
     * this method removes a requirement from the list of the resources requirements of the card
     * @param cardIndex index of the card we want to make the operation on
     * @param requirementIndex
     */
    public void removeResourcesRequirementFromCard(int cardIndex,int requirementIndex){
        this.listOfCards.get(cardIndex).getAmountOfForResourcesRequirement().remove(requirementIndex);
        this.listOfCards.get(cardIndex).getResourcesRequired().remove(requirementIndex);
        this.listOfCards.get(cardIndex).setWasCardModified(true);
    }

    /**
     * this method changes a development requirement for activating the leader card
     * @param cardIndex index of the card we want to make the operation on
     * @param requirementIndex
     * @param newAmountOfDevelopmentRequired
     * @param newLevelRequired
     * @param newColorRequired
     */
    public void changeDevelopmentRequirementOfCard(int cardIndex,int requirementIndex, int newAmountOfDevelopmentRequired,int newLevelRequired,String newColorRequired){
        this.listOfCards.get(cardIndex).getAmountOfForDevelopmentRequirement().set(requirementIndex,newAmountOfDevelopmentRequired);
        this.listOfCards.get(cardIndex).getLevelsRequired().set(requirementIndex,newLevelRequired);
        this.listOfCards.get(cardIndex).getColorsRequired().set(requirementIndex,newColorRequired);
        this.listOfCards.get(cardIndex).setWasCardModified(true);
    }

    /**
     * this method adds a development requirement for activating the leader card
     * @param cardIndex index of the card we want to make the operation on
     * @param newAmountOfDevelopmentRequired
     * @param newLevelRequired
     * @param newColorRequired
     */
    public void addDevelopmentRequirementOfCard(int cardIndex,int newAmountOfDevelopmentRequired,int newLevelRequired,String newColorRequired){
        this.listOfCards.get(cardIndex).getAmountOfForDevelopmentRequirement().add(newAmountOfDevelopmentRequired);
        this.listOfCards.get(cardIndex).getLevelsRequired().add(newLevelRequired);
        this.listOfCards.get(cardIndex).getColorsRequired().add(newColorRequired);
        this.listOfCards.get(cardIndex).setWasCardModified(true);
    }

    /**
     * this method removes a development requirement for activating the leader card
     * @param cardIndex index of the card we want to make the operation on
     * @param requirementIndex index of the requirement we want to remove
     */
    public void removeDevelopmentRequirementOfCard(int cardIndex,int requirementIndex){
        this.listOfCards.get(cardIndex).getAmountOfForDevelopmentRequirement().remove(requirementIndex);
        this.listOfCards.get(cardIndex).getLevelsRequired().remove(requirementIndex);
        this.listOfCards.get(cardIndex).getColorsRequired().remove(requirementIndex);
        this.listOfCards.get(cardIndex).setWasCardModified(true);
    }

    public void clearDevelopmentRequirements(int cardIndex){
        this.listOfCards.get(cardIndex).getAmountOfForDevelopmentRequirement().clear();
        this.listOfCards.get(cardIndex).getColorsRequired().clear();
        this.listOfCards.get(cardIndex).getLevelsRequired().clear();
    }

    public void clearResourceRequirements(int cardIndex){
        this.listOfCards.get(cardIndex).getResourcesRequired().clear();
        this.listOfCards.get(cardIndex).getAmountOfForResourcesRequirement().clear();
    }





    /**
     * this method changes the card victory points
     * @param cardIndex index of the card we want to make the operation on
     * @param victoryPointsToSet
     */
    public void changeCardVictoryPoints(int cardIndex,int victoryPointsToSet) {
        this.listOfCards.get(cardIndex).setVictoryPoints(victoryPointsToSet);
        this.listOfCards.get(cardIndex).setWasCardModified(true);
    }

    /**
     * this method changes the card special power type
     * @param cardIndex
     * @param specialPowerToSet
     */
    public void changeCardSpecialPowerType(int cardIndex,String specialPowerToSet) {
        this.listOfCards.get(cardIndex).setSpecialPower(specialPowerToSet);
        this.listOfCards.get(cardIndex).setWasCardModified(true);
    }

    public void clearSpecialPowerResources(int cardIndex){
        this.listOfCards.get(cardIndex).getSpecialPowerResources().clear();
    }

    public void addSpecialPowerResources(int cardIndex,int amount,String resource){
        for(int i=0; i<amount;i++) {
            this.listOfCards.get(cardIndex).getSpecialPowerResources().add(resource);
        }
    }

    /**
     * this methods just prints the cards to screen
     */
    public void printCards(){
        System.out.println("list of tiles:");
        Gson listOfCardsGson = new GsonBuilder().setPrettyPrinting().create();
        String listJson = listOfCardsGson.toJson(this.listOfCards);
        System.out.println(listJson);
    }

    /**
     * this method is to write the modified arraylist of cards into the json file
     */
    public void writeCardsInJson(){
        //System.out.println("list of cards:");
        Gson listOfCardsGson = new GsonBuilder().setPrettyPrinting().create();
        String listJson = listOfCardsGson.toJson(this.listOfCards);
        //System.out.println(listJson);
        try (FileWriter file = new FileWriter("src/main/resources/LeaderCardsInstancingFA.json")) {
            file.write(listJson);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateProperties() {

        //index of the card
        int i =0;

        for(LeaderCardForFA card:this.listOfCards){
            card.initializePropertiesForTableView(i);
            i++;
        }
    }
}
