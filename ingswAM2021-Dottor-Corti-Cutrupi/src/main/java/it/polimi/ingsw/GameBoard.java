package it.polimi.ingsw;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.developmentcard.Color;
import it.polimi.ingsw.developmentcard.DevelopmentCard;
import it.polimi.ingsw.developmentcard.DevelopmentCardDeck;
import it.polimi.ingsw.developmentcard.DevelopmentCardForJson;
import it.polimi.ingsw.market.Market;
import it.polimi.ingsw.requirements.Requirements;
import it.polimi.ingsw.requirements.ResourcesRequirements;
import it.polimi.ingsw.requirements.ResourcesRequirementsForAcquisition;
import it.polimi.ingsw.resource.*;
import org.javatuples.Pair;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class GameBoard {

    private Market market;
    private DevelopmentCardDeck[][] developmentCardDecks;

    public GameBoard(){
        market= new Market();
        developmentCardDecks = new DevelopmentCardDeck[3][4];
    }

    public void decksInitializer() throws FileNotFoundException {
        int i=0;
        JsonReader reader = new JsonReader(new FileReader("C:\\Users\\Sam\\Desktop\\provajson.json"));
        JsonParser parser = new JsonParser();
        JsonArray cardsArray = parser.parse(reader).getAsJsonArray();
        for(JsonElement jsonElement : cardsArray){
            Gson gson = new Gson();
            DevelopmentCardForJson cardRecreated = gson.fromJson(jsonElement.getAsJsonObject(), DevelopmentCardForJson.class);

            //here we convert the card price
            List<ResourcesRequirementsForAcquisition> cardPrice = new ArrayList<ResourcesRequirementsForAcquisition>();
            for(int quantity: cardRecreated.getAmountOfForPrice()){
                Resource resourceForPrice;
                if(cardRecreated.getTypeOfResourceForPrice().get(i)=="coin"){
                    resourceForPrice = new CoinResource();
                }
                if(cardRecreated.getTypeOfResourceForPrice().get(i)=="stone"){
                    resourceForPrice = new StoneResource();
                }
                if(cardRecreated.getTypeOfResourceForPrice().get(i)=="shield"){
                    resourceForPrice = new ShieldResource();
                }
                else{
                    resourceForPrice = new ServantResource();
                }
                ResourcesRequirementsForAcquisition requirement = new ResourcesRequirementsForAcquisition (quantity,resourceForPrice);
                cardPrice.add(requirement);
                i++;
            }

            // here we convert the card stats
            Color cardColor;
            if(cardRecreated.getColor()=="blue"){
                cardColor= Color.Blue;
            }
            if(cardRecreated.getColor()=="purple"){
                cardColor= Color.Purple;
            }
            if(cardRecreated.getColor()=="green"){
                cardColor= Color.Green;
            }
            else{
                cardColor= Color.Yellow;
            }
            Pair <Integer,Color> cardStats = new Pair <Integer,Color>(cardRecreated.getLevel(),cardColor);

            //here we convert the prod Requirements
            List<ResourcesRequirements> prodRequirements = new ArrayList<ResourcesRequirements>();
            i=0;
            for(int quantity: cardRecreated.getAmountOfForProdRequirements()){
                Resource resourceForRequirements;
                if(cardRecreated.getTypeOfResourceForPrice().get(i)=="coin"){
                    resourceForRequirements = new CoinResource();
                }
                if(cardRecreated.getTypeOfResourceForPrice().get(i)=="stone"){
                    resourceForRequirements = new StoneResource();
                }
                if(cardRecreated.getTypeOfResourceForPrice().get(i)=="shield"){
                    resourceForRequirements = new ShieldResource();
                }
                else{
                    resourceForRequirements = new ServantResource();
                }
                ResourcesRequirements requirement = new ResourcesRequirements (quantity,resourceForRequirements);
                prodRequirements.add(requirement);
                i++;
            }


            //here we convert the prod results
            i=0;
            List<Resource> prodResults = new ArrayList<Resource>();
            for(int quantity: cardRecreated.getAmountOfForProdResults()){
                Resource resourceForResults;
                if(cardRecreated.getTypeOfResourceForPrice().get(i)=="coin"){
                    resourceForResults = new CoinResource();
                }
                if(cardRecreated.getTypeOfResourceForPrice().get(i)=="stone"){
                    resourceForResults = new StoneResource();
                }
                if(cardRecreated.getTypeOfResourceForPrice().get(i)=="shield"){
                    resourceForResults = new ShieldResource();
                }
                else{
                    resourceForResults = new ServantResource();
                }
                prodResults.add(resourceForResults);
                i++;
            }

            DevelopmentCard cardToAdd = new DevelopmentCard(cardPrice,cardStats,prodRequirements,prodResults,cardRecreated.getVictoryPoints());

            if(cardColor==Color.Green){
                developmentCardDecks[0][cardRecreated.getLevel()].addNewCard(cardToAdd);
            }
            if(cardColor==Color.Blue){
                developmentCardDecks[1][cardRecreated.getLevel()].addNewCard(cardToAdd);
            }
            if(cardColor==Color.Yellow){
                developmentCardDecks[2][cardRecreated.getLevel()].addNewCard(cardToAdd);
            }
            if(cardColor==Color.Purple){
                developmentCardDecks[3][cardRecreated.getLevel()].addNewCard(cardToAdd);
            }
        }
    }

    public void discardTokenEffect(Color developmentCardColor){
        //also ends the game if Lorenzo is playing and a certain color of development card is no longer present
    }

    public Market getMarket(){
        return market;
    }

}
