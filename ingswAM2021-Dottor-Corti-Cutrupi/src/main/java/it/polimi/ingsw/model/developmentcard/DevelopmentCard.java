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
    private List <ResourcesRequirementsForAcquisition> cardPrice;
    private Pair <Integer, Color> cardStats;
    private List <ResourcesRequirements> prodRequirements;
    private List <Resource> prodResults;
    private int victoryPoints;

    public DevelopmentCard(List<ResourcesRequirementsForAcquisition> cardPrice, Pair<Integer, Color> cardStats, List<ResourcesRequirements> prodRequirements, List<Resource> prodResults, int victoryPoints) {
        this.cardPrice = cardPrice;
        this.cardStats = cardStats;
        this.prodRequirements = prodRequirements;
        this.prodResults = prodResults;
        this.victoryPoints = victoryPoints;
    }

    @Override
    public String   toString() {
        String prodResultsString = new String("[");
        for (Resource res:prodResults) {
            prodResultsString = prodResultsString + res.getResourceType() + " ";
        }
        prodResultsString = prodResultsString + "]";
        return "DevelopmentCard{" +
                "cardPrice=" + cardPrice +
                ", cardStats=" + cardStats +
                ", prodRequirements=" + prodRequirements +
                ", prodResults=" + prodResultsString +
                ", victoryPoints=" + victoryPoints +
                '}';
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

     public int produce(Dashboard dashboard) throws PapalCardActivatedException {
        int quantity, faithNumber=0;
        Resource resource;
         /**part where we remove the resources
          *
          */
        for(ResourcesRequirements requirement:this.prodRequirements) {
            quantity = requirement.getResourcesRequired().getValue0();
            resource = requirement.getResourcesRequired().getValue1();
            dashboard.removeResourcesFromDashboard(quantity,resource);
        }
         /**part where we add the created resources in the strongbox or we move forward the papal path if the resource to add is faith
          *
          */
         for(Resource resourceToProduce: this.prodResults) {
             if(resourceToProduce.getResourceType()== ResourceType.Faith)   faithNumber++;
             else resourceToProduce.effectFromProduction(dashboard);
         }
         return faithNumber;
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

}