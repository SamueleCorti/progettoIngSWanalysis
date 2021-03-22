package it.polimi.ingsw.developmentcard;

import it.polimi.ingsw.resource.Resource;
import org.javatuples.Pair;


public class DevelopmentCard {

    private Pair <Integer, Resource> price;
    private Pair <Integer, Color> cardStats;
    private Pair <Integer, Resource> prodRequirements;
    private Pair <Integer,Resource> prodResults;
    private int victoryPoints;


    public DevelopmentCard(Pair<Integer, Resource> price, Pair<Integer, Color> cardStats, Pair<Integer, Resource> prodRequirements, Pair<Integer, Resource> prodResults, int victoryPoints) {
        this.price = price;
        this.cardStats = cardStats;
        this.prodRequirements = prodRequirements;
        this.prodResults = prodResults;
        this.victoryPoints = victoryPoints;
    }

    public Pair<Integer, Resource> getPrice() {
        return price;
    }

    public Pair<Integer, Color> getCardStats() {
        return cardStats;
    }

    public Pair<Integer, Resource> getProdRequirements() {
        return prodRequirements;
    }

    public Pair<Integer, Resource> getProdResults() {
        return prodResults;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }
    
}
