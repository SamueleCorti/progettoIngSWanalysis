package it.polimi.ingsw.leadercard;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.developmentcard.DevelopmentCardForJson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class LeaderCardModifier {

    private ArrayList<LeaderCardForJson> listOfCards;

    public ArrayList<LeaderCardForJson> getListOfCards() {
        return listOfCards;
    }
    public LeaderCardModifier() {
        this.listOfCards = new ArrayList <LeaderCardForJson>();
    }

    /**this method imports all the leader cards from json into the arraylist of this class
     *
     * @throws FileNotFoundException
     */
    public void importCards() throws FileNotFoundException {
        //part where we import all the cards from json
        JsonReader reader = new JsonReader(new FileReader("C:\\Users\\Sam\\Desktop\\Progetto ingegneria del software\\LeaderCardsInstancing.json"));
        JsonParser parser = new JsonParser();
        JsonArray cardsArray = parser.parse(reader).getAsJsonArray();
        for(JsonElement jsonElement : cardsArray) {
            Gson gson = new Gson();
            LeaderCardForJson cardRecreated = gson.fromJson(jsonElement.getAsJsonObject(), LeaderCardForJson.class);
            this.listOfCards.add(cardRecreated);
        }
    }

    /** this method changes the values of a requisite for activating the card
     *
     * @param cardIndex
     * @param requirementIndex
     * @param quantity
     * @param resource
     */
   /* public void changeResourcesRequirementOfCard(int cardIndex,int requirementIndex,int quantity, String resource){
        this.listOfCards.get(cardIndex).getAmountOfForResourcesRequirement().set(requirementIndex,quantity);
        this.listOfCards.get(cardIndex).getType().set(requirementIndex,resource);
    }*/

    /**this method adds a requisite for activating the card
     *
     * @param cardIndex
     * @param quantity
     * @param resource
     */
   /* public void addProdRequirementOfCard(int cardIndex,int quantity, String resource ) {
        this.listOfCards.get(cardIndex).getAmountOfForProdRequirements().add(quantity);
        this.listOfCards.get(cardIndex).getTypeOfResourceForProdRequirements().add(resource);
    }*/

    /**this method removes a requisite for activating the card
     *
     * @param cardIndex
     * @param requirementIndex
     */
  /*  public void removeProdRequirementFromCard(int cardIndex,int requirementIndex){
        this.listOfCards.get(cardIndex).getAmountOfForProdRequirements().remove(requirementIndex);
        this.listOfCards.get(cardIndex).getTypeOfResourceForProdRequirements().remove(requirementIndex);
    }

*/

    /**this method is to write the modified arraylist of cards into the json file
     *
     */
    public void writeCardsInJson(){
        //FOR NOW IT JUST PRINTS THE CARD TO SCREEN
        System.out.println("list of cards:");
        Gson listOfCardsGson = new GsonBuilder().setPrettyPrinting().create();
        String listJson = listOfCardsGson.toJson(this.listOfCards);
        System.out.println(listJson);
    }

}
