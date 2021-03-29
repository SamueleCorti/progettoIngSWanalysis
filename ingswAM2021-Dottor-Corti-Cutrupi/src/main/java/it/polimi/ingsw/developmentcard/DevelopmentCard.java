package it.polimi.ingsw.developmentcard;

import it.polimi.ingsw.Dashboard;
import it.polimi.ingsw.requirements.Requirements;
import it.polimi.ingsw.resource.Resource;
import org.javatuples.Pair;

import java.util.List;


public class DevelopmentCard {
    private List <Requirements> cardPrice;
    private Pair <Integer, Color> cardStats;
    private List <Requirements> prodRequirements;
    private List <Resource> prodResults;
    private int victoryPoints;
     /*
    we need a way to instantiate the whole deck
     */

    public DevelopmentCard(List<Requirements> cardPrice, Pair<Integer, Color> cardStats, List<Requirements> prodRequirements, List<Resource> prodResults, int victoryPoints) {
        this.cardPrice = cardPrice;
        this.cardStats = cardStats;
        this.prodRequirements = prodRequirements;
        this.prodResults = prodResults;
        this.victoryPoints = victoryPoints;
    }

    public List<Requirements> getCardPrice() {
        return cardPrice;
    }

    public Pair<Integer, Color> getCardStats() {
        return cardStats;
    }

    public List<Requirements> getProdRequirements() {
        return prodRequirements;
    }

    public List<Resource> getProdResults() {
        return prodResults;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public boolean checkPrice(Dashboard dashboard) {
        for(Requirements requirements: cardPrice){
            if(!requirements.checkRequirement(dashboard)==true){
                return false;
            }
        }
    return true;
    }

    public boolean checkRequirements(Dashboard dashboard) {
        for(Requirements requirements: prodRequirements){
            if(requirements.checkRequirement(dashboard)==true){
            }
            else{
                return false;
            }
        }
        return true;
    }

     public void produce(Dashboard dashboard){
        
    /*still to implement*/

    }
}
