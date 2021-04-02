package it.polimi.ingsw.developmentcard;

import it.polimi.ingsw.Dashboard;
import it.polimi.ingsw.requirements.Requirements;
import it.polimi.ingsw.requirements.ResourcesRequirements;
import it.polimi.ingsw.resource.Resource;
import it.polimi.ingsw.storing.ExtraDepot;
import it.polimi.ingsw.storing.RegularityError;
import org.javatuples.Pair;

import java.util.List;


public class DevelopmentCard {
    private List <ResourcesRequirements> cardPrice;
    private Pair <Integer, Color> cardStats;
    private List <ResourcesRequirements> prodRequirements;
    private List <Resource> prodResults;
    private int victoryPoints;
     /*
    we need a way to instantiate the whole deck
     */

    public DevelopmentCard(List<ResourcesRequirements> cardPrice, Pair<Integer, Color> cardStats, List<ResourcesRequirements> prodRequirements, List<Resource> prodResults, int victoryPoints) {
        this.cardPrice = cardPrice;
        this.cardStats = cardStats;
        this.prodRequirements = prodRequirements;
        this.prodResults = prodResults;
        this.victoryPoints = victoryPoints;
    }

    public List<ResourcesRequirements> getCardPrice() {
        return cardPrice;
    }

    public Pair<Integer, Color> getCardStats() {
        return cardStats;
    }

    public List<ResourcesRequirements> getProdRequirements() {
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
        for(ResourcesRequirements requirements: prodRequirements){
            if(!requirements.checkRequirement(dashboard)==true)      return false;
        }
        return true;
    }

     public void produce(Dashboard dashboard) throws RegularityError {

        int quantity;
        Resource resource;
        //part where we remove the resources
        for(ResourcesRequirements requirement:this.prodRequirements) {
            quantity = requirement.getResourcesRequired().getValue0();
            resource = requirement.getResourcesRequired().getValue1();
            dashboard.removeResourcesFromDashboard(quantity,resource);
        }
        //part where we add the created resources in the strongbox or we move forward the papal path if the resource to add is faith
         for(Resource resourceProduced: this.prodResults) {
             if(resourceProduced.getResourceType()=="faith"){
                 dashboard.getPapalPath().moveForward();
             }else {
                 dashboard.produceResource(resourceProduced);
             }
         }
    }

    /*this method is for buying the card. It just removes the resources from the dashboard;
    someone else will move the card itself from the deck to the developmentCardZone */
    public void buyCard(Dashboard dashboard) throws RegularityError {
        int quantity;
        Resource resource;

        for(ResourcesRequirements requirement:this.cardPrice) {

            quantity = requirement.getResourcesRequired().getValue0();
            resource = requirement.getResourcesRequired().getValue1();
            for(Resource resourceDiscounted: dashboard.getDiscountedResources()){
                if(resource.getResourceType()==resourceDiscounted.getResourceType()){
                    quantity=quantity-1;
                }
            }
            dashboard.removeResourcesFromDashboard(quantity,resource);
        }
    }

}
