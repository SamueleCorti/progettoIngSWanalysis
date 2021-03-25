package it.polimi.ingsw.developmentcard;

import it.polimi.ingsw.Dashboard;
import it.polimi.ingsw.resource.Resource;
import org.javatuples.Pair;

import java.util.List;


public class DevelopmentCard {
    //we created the card requirements, price, stats and results as a Pair of Lists, in this way containing all the information necessary
    private List <Requirements> cardPrice;
    private Pair <Integer, Color> cardStats;
    private List <Requirements> prodRequirements;
    private List <Resource> prodResults;
    private int victoryPoints;

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
    public boolean checkRequirements(Dashboard dashboard) {
        for(Requirements requirements: cardPrice){
            if(requirements.checkRequirement(dashboard)==true){
            }
            else{
                return false;
            }
        }
    return true;
    }
    /* public void produce(Warehouse warehouse,Strongbox strongbox){


    }*/
}
