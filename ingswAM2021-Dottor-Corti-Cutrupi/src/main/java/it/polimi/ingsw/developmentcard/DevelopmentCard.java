package it.polimi.ingsw.developmentcard;

import it.polimi.ingsw.Warehouse;
import it.polimi.ingsw.resource.Resource;
import org.javatuples.Pair;

import java.util.List;


public class DevelopmentCard {
    //we created the card requirements, price, stats and results as a Pair of Lists, in this way containing all the information necessary
    private Pair <List <Integer>,List <Resource>> price;
    private Pair <List <Integer>,List <Color>> cardStats;
    private Pair <List <Integer>,List <Resource>> prodRequirements;
    private Pair <List <Integer>,List <Resource>> prodResults;
    private int victoryPoints;

    public DevelopmentCard(Pair<List<Integer>, List<Resource>> price, Pair<List<Integer>,List <Color>> cardStats, Pair<List<Integer>, List<Resource>> prodRequirements, Pair<List<Integer>, List<Resource>> prodResults, int victoryPoints) {
        this.price = price;
        this.cardStats = cardStats;
        this.prodRequirements = prodRequirements;
        this.prodResults = prodResults;
        this.victoryPoints = victoryPoints;
    }

    public Pair<List<Integer>, List<Resource>> getPrice() {
        return price;
    }

    public Pair<List<Integer>,List <Color>> getCardStats() {
        return cardStats;
    }

    public Pair<List<Integer>, List<Resource>> getProdRequirements() {
        return prodRequirements;
    }

    public Pair<List<Integer>, List<Resource>> getProdResults() {
        return prodResults;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }
   /* public void produce(Warehouse warehouse,Strongbox strongbox){


    }*/
}
