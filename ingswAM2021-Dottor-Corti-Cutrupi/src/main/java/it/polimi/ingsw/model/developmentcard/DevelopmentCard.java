package it.polimi.ingsw.model.developmentcard;

import it.polimi.ingsw.exception.PapalCardActivatedException;
import it.polimi.ingsw.model.boardsAndPlayer.Dashboard;
import it.polimi.ingsw.model.requirements.Requirements;
import it.polimi.ingsw.model.requirements.ResourcesRequirementsForAcquisition;
import it.polimi.ingsw.model.requirements.ResourcesRequirements;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceType;
import org.javatuples.Pair;

import java.util.List;


public class DevelopmentCard {
    private final List <ResourcesRequirementsForAcquisition> cardPrice;
    private final Pair <Integer, Color> cardStats;
    private final List <ResourcesRequirements> prodRequirements;
    private final List <Resource> prodResults;
    private final int victoryPoints;

    public DevelopmentCard(List<ResourcesRequirementsForAcquisition> cardPrice, Pair<Integer, Color> cardStats, List<ResourcesRequirements> prodRequirements, List<Resource> prodResults, int victoryPoints) {
        this.cardPrice = cardPrice;
        this.cardStats = cardStats;
        this.prodRequirements = prodRequirements;
        this.prodResults = prodResults;
        this.victoryPoints = victoryPoints;
    }

    public List<ResourcesRequirementsForAcquisition> getCardPrice() {
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

    /** method that checks if the card can be bought
     *
     * @param dashboard
     * @return
     */
    public boolean checkPrice(Dashboard dashboard) {
        for(Requirements requirements: cardPrice){
            if(!requirements.checkRequirement(dashboard)==true){
                return false;
            }
        }
    return true;
    }

    /**method that checks if the card's production can be activated
     *
     * @param dashboard
     * @return
     */
    public boolean checkRequirements(Dashboard dashboard) {
        for(ResourcesRequirements requirements: prodRequirements){
            if(!requirements.checkRequirement(dashboard)==true)      return false;
        }
        return true;
    }

     public void produce(Dashboard dashboard) throws PapalCardActivatedException {
        int quantity;
        Resource resource;
        for(ResourcesRequirements requirement:this.prodRequirements) {
            quantity = requirement.getResourcesRequired().getValue0();
            resource = requirement.getResourcesRequired().getValue1();
            dashboard.removeResourcesFromDashboard(quantity,resource);
        }
         for(Resource resourceToProduce: this.prodResults) {
             resourceToProduce.effectFromProduction(dashboard);
         }
    }

    /**
     * this method is for buying the card. It just removes the resources from the dashboard;
     * someone else will move the card itself from the deck to the developmentCardZone
     */
    public void buyCard(Dashboard dashboard)  {
        int quantity;
        Resource resource;

        for(ResourcesRequirementsForAcquisition requirement:this.cardPrice) {

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

    @Override
    public String toString() {
        String string=new String();
        string+="Color: "+ this.getCardStats().getValue1()+"\tlevel: "+this.getCardStats().getValue0()+" \tvictory points: "+this.getVictoryPoints();
        string+="\nCard cost: \t";
        for(ResourcesRequirementsForAcquisition requirements: this.getCardPrice())
            string+=requirements.getResourcesRequired().getValue0() +" "+ requirements.getResourcesRequired().getValue1().getResourceType()+"s\t";
        string+="\nProduction cost: \n";
        for(ResourcesRequirements resourcesRequirements: this.getProdRequirements()){
            string+= resourcesRequirements.getResourcesRequired().getValue0()+" "+ resourcesRequirements.getResourcesRequired().getValue1().getResourceType()+"s\t";
        }
        string+="\n";
        string+="Resources produced: \n";
        for(Resource resource: this.getProdResults())
            string+= resource.getResourceType()+"\t";
        string+="\n";
        return string;
    }
}
