package it.polimi.ingsw.parametersEditor.devCardsTools;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * this class is used to modify the values relative to the development cards
 */
public class DevelopmentCardModifier {

        private ArrayList<DevelopmentCardForFA> listOfCards;

        public ArrayList<DevelopmentCardForFA> getListOfCards() {
            return listOfCards;
        }

        public DevelopmentCardModifier() {
            this.listOfCards = new ArrayList <DevelopmentCardForFA>();
        }

    /**
     * this method imports all the development cards from json into the arraylist of this class
     *
     * @throws FileNotFoundException
     */
    public void importCards() {
            //part where we import all the cards from json
            JsonReader reader;
            reader = new JsonReader(new InputStreamReader(getClass().getResourceAsStream("/DevCardInstancingFA.json")));
            JsonParser parser = new JsonParser();
            JsonArray cardsArray = parser.parse(reader).getAsJsonArray();
            int i=0;
            for(JsonElement jsonElement : cardsArray) {
                Gson gson = new Gson();
                DevelopmentCardForFA cardRecreated = gson.fromJson(jsonElement.getAsJsonObject(), DevelopmentCardForFA.class);
                cardRecreated.initializePropertiesForTableView(i);
                this.listOfCards.add(cardRecreated);
                i++;
            }
        }

        /**
         *this method changes the values of a requisite for buying the card
         */
        public void changePriceOfCard(int cardIndex,int priceIndex,int quantity, String resource){
            this.listOfCards.get(cardIndex).getAmountOfForPrice().set(priceIndex,quantity);
            this.listOfCards.get(cardIndex).getTypeOfResourceForPrice().set(priceIndex,resource);
        }

        /**
         *this method adds a requisite for buying the card
        */
        public void addPriceToCard(int cardIndex,int quantity, String resource ) {
            this.listOfCards.get(cardIndex).getAmountOfForPrice().add(quantity);
            this.listOfCards.get(cardIndex).getTypeOfResourceForPrice().add(resource);
        }
        /**
        *this method removes a requisite for buying the card
         */
        public void removePriceFromCard(int cardIndex,int priceIndex){
            this.listOfCards.get(cardIndex).getAmountOfForPrice().remove(priceIndex);
            this.listOfCards.get(cardIndex).getTypeOfResourceForPrice().remove(priceIndex);
        }

    /** this method changes the card level
     *
     * @param cardIndex index of the card we want to make the operation on
     * @param levelToSet
     */
    public void changeCardLevel(int cardIndex,int levelToSet) {
            this.listOfCards.get(cardIndex).setLevel(levelToSet);
        }

    /**this method changes the card color
     *
     * @param cardIndex index of the card we want to make the operation on
     * @param colorToSet
     */
    public void changeCardColor(int cardIndex,String colorToSet) {
            this.listOfCards.get(cardIndex).setColor(colorToSet);
        }

    /** this method changes the values of a requisite for starting the production
     *
     * @param cardIndex index of the card we want to make the operation on
     * @param requirementIndex
     * @param quantity
     * @param resource
     */
        public void changeProdRequirementOfCard(int cardIndex,int requirementIndex,int quantity, String resource){
            this.listOfCards.get(cardIndex).getAmountOfForProdRequirements().set(requirementIndex,quantity);
            this.listOfCards.get(cardIndex).getTypeOfResourceForProdRequirements().set(requirementIndex,resource);
        }

    /**this method adds a requisite for starting the production
     *
     * @param cardIndex index of the card we want to make the operation on
     * @param quantity
     * @param resource
     */
        public void addProdRequirementOfCard(int cardIndex,int quantity, String resource ) {
            this.listOfCards.get(cardIndex).getAmountOfForProdRequirements().add(quantity);
            this.listOfCards.get(cardIndex).getTypeOfResourceForProdRequirements().add(resource);
        }

    /**this method removes a requisite for starting the production
     *
     * @param cardIndex index of the card we want to make the operation on
     * @param requirementIndex
     */
    public void removeProdRequirementFromCard(int cardIndex,int requirementIndex){
            this.listOfCards.get(cardIndex).getAmountOfForProdRequirements().remove(requirementIndex);
            this.listOfCards.get(cardIndex).getTypeOfResourceForProdRequirements().remove(requirementIndex);
        }

    /** this method changes the values of a production result
     *
     * @param cardIndex index of the card we want to make the operation on
     * @param resultIndex
     * @param quantity
     * @param resource
     */
        public void changeProdResultOfCard(int cardIndex,int resultIndex,int quantity, String resource){
            this.listOfCards.get(cardIndex).getAmountOfForProdResults().set(resultIndex,quantity);
            this.listOfCards.get(cardIndex).getTypeOfResourceForProdResults().set(resultIndex,resource);
        }

        /**
         * this method adds a production result
         */
        public void addProdResultOfCard(int cardIndex,int quantity, String resource ) {
            this.listOfCards.get(cardIndex).getAmountOfForProdResults().add(quantity);
            this.listOfCards.get(cardIndex).getTypeOfResourceForProdResults().add(resource);
        }

    /**this method removes a production result
     *
     * @param cardIndex index of the card we want to make the operation on
     * @param requirementIndex
     */
    public void removeProdResultFromCard(int cardIndex,int requirementIndex){
            this.listOfCards.get(cardIndex).getAmountOfForProdResults().remove(requirementIndex);
            this.listOfCards.get(cardIndex).getTypeOfResourceForProdResults().remove(requirementIndex);
        }

    /**this method changes the card victory points
     *
     * @param cardIndex index of the card we want to make the operation on
     * @param victoryPointsToSet
     */
    public void changeCardVictoryPoints(int cardIndex,int victoryPointsToSet) {
            this.listOfCards.get(cardIndex).setVictoryPoints(victoryPointsToSet);
        }

        public void addNewCard(List<Integer> amountOfForPrice, List<String> typeOfResourceForPrice, Integer level, String color, List<Integer> amountOfForProdRequirements, List<String> typeOfResourceForProdRequirements, List<Integer> amountOfForProdResults, List<String> typeOfResourceForProdResults, Integer victoryPoints){
            DevelopmentCardForFA cardToAdd = new DevelopmentCardForFA(amountOfForPrice, typeOfResourceForPrice,level,color, amountOfForProdRequirements, typeOfResourceForProdRequirements, amountOfForProdResults, typeOfResourceForProdResults,victoryPoints,true);
            this.listOfCards.add(cardToAdd);
        }

    /**
     * this method just prints the temporary cards to screen
     */
    public void printCards(){
            System.out.println("list of cards:");
            Gson listOfCardsGson = new GsonBuilder().setPrettyPrinting().create();
            String listJson = listOfCardsGson.toJson(this.listOfCards);
            System.out.println(listJson);
        }
    /**this method is to write the modified arraylist of cards into the json file
     *
     */
    public void writeCardsInJson(){
        Gson listOfCardsGson = new GsonBuilder().setPrettyPrinting().create();
        String listJson = listOfCardsGson.toJson(this.listOfCards);
        try (FileWriter file = new FileWriter("src/main/resources/DevCardInstancingFA.json")) {
            file.write(listJson);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * this method updates all the cards values in the GUI table views
     */
    public void updateProperties() {
        //index of the card
        int i =0;
        for(DevelopmentCardForFA card:this.listOfCards){
            card.initializePropertiesForTableView(i);
            i++;
        }
    }

    public void clearPrice(Integer cardIndex) {
        this.listOfCards.get(cardIndex).getAmountOfForPrice().clear();
        this.listOfCards.get(cardIndex).getTypeOfResourceForPrice().clear();
    }

    public void clearProdRequirements(Integer cardIndex) {
        this.listOfCards.get(cardIndex).getAmountOfForProdRequirements().clear();
        this.listOfCards.get(cardIndex).getTypeOfResourceForProdRequirements().clear();
    }

    public void clearProdResults(Integer cardIndex){
        this.listOfCards.get(cardIndex).getAmountOfForProdResults().clear();
        this.listOfCards.get(cardIndex).getTypeOfResourceForProdResults().clear();
    }
}
