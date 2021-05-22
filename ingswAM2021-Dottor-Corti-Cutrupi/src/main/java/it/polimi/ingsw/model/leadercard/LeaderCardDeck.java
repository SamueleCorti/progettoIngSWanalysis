package it.polimi.ingsw.model.leadercard;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.model.developmentcard.Color;
import it.polimi.ingsw.model.leadercard.leaderpowers.*;
import it.polimi.ingsw.model.requirements.DevelopmentRequirements;
import it.polimi.ingsw.model.requirements.Requirements;
import it.polimi.ingsw.model.requirements.ResourcesRequirementsForLeaderCards;
import it.polimi.ingsw.model.resource.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class LeaderCardDeck {

    private List<LeaderCard> deck;

    public LeaderCardDeck(){
        this.deck = new ArrayList<LeaderCard>();
    }

    public void addNewCard(LeaderCard cardToAdd){
        this.deck.add(cardToAdd);
    }

    /**this method shuffles the deck
     *
     */
    public void shuffle(){
        Collections.shuffle(this.deck);
    }

    public List<LeaderCard> getDeck() {
        return deck;
    }

    /**this method removes the first card of the deck and returns it
     *
     * @return
     */
    public LeaderCard drawCard(){
        LeaderCard temp=this.deck.get(0);
        this.deck.remove(0);
        return temp;
    }

    /** this method is used to instantiate the whole deck, getting the list of the card from a json file
    * the cards imported from json are given in the LeaderCardForJson class, and they are here
    * converted as LeaderCards.
     */
    public void deckInitializer() {
        int i;
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader("src/main/resources/LeaderCardsInstancing.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        JsonParser parser = new JsonParser();
        JsonArray cardsArray = parser.parse(reader).getAsJsonArray();

        for(JsonElement jsonElement : cardsArray) {
            Gson gson = new Gson();
            LeaderCardForJson cardRecreated = gson.fromJson(jsonElement.getAsJsonObject(), LeaderCardForJson.class);

            /**here we convert the card requirements
             *
             */
            i=0;
            ArrayList <Requirements> requirements = new ArrayList<Requirements>();
            /**case were teh card has resources requirements
             *
             */
            if(cardRecreated.getTypeOfRequirement().equals("resources")){
                for(Integer quantity: cardRecreated.getAmountOfForResourcesRequirement()){
                    Resource resourceForPrice;
                    if (cardRecreated.getResourcesRequired().get(i).equals("coin")) {
                        resourceForPrice = new CoinResource();
                    }
                    else if (cardRecreated.getResourcesRequired().get(i).equals("stone")) {
                        resourceForPrice = new StoneResource();
                    }
                    else if (cardRecreated.getResourcesRequired().get(i).equals("shield")) {
                        resourceForPrice = new ShieldResource();
                    } else {
                        resourceForPrice = new ServantResource();
                    }
                    ResourcesRequirementsForLeaderCards requirement = new ResourcesRequirementsForLeaderCards (quantity,resourceForPrice);
                    requirements.add(requirement);
                    i++;
                }
            }
            /**case where the card has development requirements
             *
             */
            else {
                i=0;
                for(Integer quantity: cardRecreated.getAmountOfForDevelopmentRequirement()){
                    DevelopmentRequirements requirement;
                    if (cardRecreated.getColorsRequired().get(i).equals("blue")){
                        requirement = new DevelopmentRequirements(quantity,cardRecreated.getLevelsRequired().get(i),Color.Blue);
                    } else
                    if (cardRecreated.getColorsRequired().get(i).equals("yellow")){
                        requirement = new DevelopmentRequirements(quantity,cardRecreated.getLevelsRequired().get(i),Color.Yellow);
                    } else
                    if (cardRecreated.getColorsRequired().get(i).equals("green")){
                        requirement = new DevelopmentRequirements(quantity,cardRecreated.getLevelsRequired().get(i),Color.Green);
                    }
                    else{
                        requirement = new DevelopmentRequirements(quantity,cardRecreated.getLevelsRequired().get(i),Color.Purple);
                    }
                    requirements.add(requirement);
                    i++;
                }
            }

            /**here we convert the Leader Power
             *
             */
            LeaderPower leaderPower;
            /**first we get the correct resource of the power
             *
             */
             ArrayList <Resource> resourcesForLeaderPower= new ArrayList <Resource>();

             for(String string: cardRecreated.getSpecialPowerResources()) {
                 if (string.toLowerCase(Locale.ROOT).equals("coin")) {
                     resourcesForLeaderPower.add(new CoinResource());
                 } else if (string.toLowerCase(Locale.ROOT).equals("stone")) {
                     resourcesForLeaderPower.add(new StoneResource());
                 } else if (string.toLowerCase(Locale.ROOT).equals("shield")) {
                     resourcesForLeaderPower.add(new ShieldResource());
                 } else {
                     resourcesForLeaderPower.add(new ServantResource());
                 }
             }


            /**and then the leader power type
             *
             */
            if(cardRecreated.getSpecialPower().equals("discount")){
                leaderPower = new Discount(resourcesForLeaderPower);
            }else
            if(cardRecreated.getSpecialPower().equals("extradeposit")){
                leaderPower = new ExtraDeposit(resourcesForLeaderPower);
            }else
            if(cardRecreated.getSpecialPower().equals("extraprod")){
                leaderPower = new ExtraProd(resourcesForLeaderPower);
            }
            else{
                leaderPower = new WhiteToColor(resourcesForLeaderPower);
            }
            LeaderCard cardToAdd = new LeaderCard(requirements,cardRecreated.getVictoryPoints(),leaderPower);
            this.deck.add(cardToAdd);
        }
    }

    public int deckSize(){
        return this.deck.size();
    }
}
