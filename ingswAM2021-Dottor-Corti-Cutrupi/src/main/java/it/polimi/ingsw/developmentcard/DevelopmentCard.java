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
    private List <Requirements> cardPrice;
    private Pair <Integer, Color> cardStats;
    private List <ResourcesRequirements> prodRequirements;
    private List <Resource> prodResults;
    private int victoryPoints;
     /*
    we need a way to instantiate the whole deck
     */

    public DevelopmentCard(List<Requirements> cardPrice, Pair<Integer, Color> cardStats, List<ResourcesRequirements> prodRequirements, List<Resource> prodResults, int victoryPoints) {
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
        for(Requirements requirements: prodRequirements){
            if(!requirements.checkRequirement(dashboard)==true)      return false;

        }
        return true;
    }

     public void produce(Dashboard dashboard) throws RegularityError {

        int quantity;
        Resource resource;
        //part where we remove the resources, starting from the warehouse, then in the extra depot and in the end in the strongbox
        for(ResourcesRequirements requirement:this.prodRequirements) {
            quantity = requirement.getResourcesRequired().getValue0();
            resource = requirement.getResourcesRequired().getValue1();
            quantity = quantity - dashboard.getWarehouse().removeResource(resource, quantity);
            if (quantity != 0) {
                for (ExtraDepot extraDepot : dashboard.getExtraDepots()) {
                    if (extraDepot.getExtraDepotType() == resource) {
                        for (int i = extraDepot.getExtraDepotSize(); i > 0; i--) {
                            if(quantity!=0) {
                                quantity = quantity - 1;
                                extraDepot.removeResource();
                            }
                        }
                    }
                }
            }
            if(quantity != 0){
                dashboard.getStrongbox().removeResourceWithAmount(resource,quantity);
            }
        }
        //part where we add the created resources in the strongbox or we move forward the papal path if the resource to add is faith
         for(Resource resourceProduced: this.prodResults) {
             if(resourceProduced.getResourceType()=="faith"){
                 dashboard.getPapalPath().moveForward();
             }else {
                 dashboard.getStrongbox().addResource(resourceProduced);
             }
         }
    }
}
