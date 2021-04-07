package it.polimi.ingsw;

import it.polimi.ingsw.developmentcard.DevelopmentCard;
import it.polimi.ingsw.developmentcard.DevelopmentCardZone;
import it.polimi.ingsw.leadercard.LeaderCardZone;
import it.polimi.ingsw.papalpath.PapalPath;
import it.polimi.ingsw.resource.Resource;
import it.polimi.ingsw.resource.ResourceType;
import it.polimi.ingsw.storing.ExtraDepot;
import it.polimi.ingsw.storing.RegularityError;
import it.polimi.ingsw.storing.Strongbox;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Dashboard {
    // needs a method to handle the development card zones
    private Warehouse warehouse;
    private Strongbox strongbox;
    private LeaderCardZone leaderCardZone;
    private ArrayList <DevelopmentCardZone> developmentCardZones;
    private PapalPath papalPath;
    private ArrayList<ExtraDepot> extraDepots;
    private ArrayList<Resource> whiteToColorResources;
    private ArrayList<Resource> discountedResources;
    //resources that represent the extra productions brought by the Leader Power
    private ArrayList<Resource> resourcesForExtraProd;
    //resources produced in this turn, at the end of the turn they will be moved in the strongbox
    private ArrayList <Resource> resourcesProduced;

    public void activatedDiscountCard(Resource discountedResource){
        discountedResources.add(discountedResource);
    }

    public ArrayList<Resource> getWhiteToColorResources() {
        return whiteToColorResources;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public Strongbox getStrongbox() {
        return strongbox;
    }

    public LeaderCardZone getLeaderCardZone() {
        return leaderCardZone;
    }

    public PapalPath getPapalPath() {
        return papalPath;
    }

    public ArrayList<DevelopmentCardZone> getDevelopmentCardZones() {
        return developmentCardZones;
    }

    public ArrayList<ExtraDepot> getExtraDepots() {
        if(extraDepots!=null)   return extraDepots;
        else return null;
    }

    public ArrayList<Resource> getDiscountedResources() {
        return discountedResources;
    }

    public ArrayList<Resource> getResourcesForExtraProd() {
        return resourcesForExtraProd;
    }

    public Dashboard(int playerOrder) {
        this.warehouse = new Warehouse();
        this.strongbox = new Strongbox();
        this.leaderCardZone = new LeaderCardZone();
        this.developmentCardZones = new ArrayList<DevelopmentCardZone>();
        this.developmentCardZones.add(new DevelopmentCardZone());
        this.developmentCardZones.add(new DevelopmentCardZone());
        this.developmentCardZones.add(new DevelopmentCardZone());
        for(int i=0; i<3; i++)        this.developmentCardZones.add(new DevelopmentCardZone());
        this.papalPath = new PapalPath(playerOrder);
        this.extraDepots= new ArrayList<ExtraDepot>();
        this.whiteToColorResources = new ArrayList<Resource>();
        this.discountedResources = new ArrayList<Resource>();
        this.resourcesProduced= new ArrayList<Resource>();
    }

    //returns the amount of resourceToLookFor when it is needed to buy a development card
    public int availableResourcesForDevelopment(Resource resourceToLookFor){
        int quantityInDepots=0;
        for(int i=0; i<extraDepots.size();i++){
            if(extraDepots.get(i).getExtraDepotType().equals(resourceToLookFor))    quantityInDepots+=extraDepots.get(i).getExtraDepotSize();
        }
            if((discountedResources!=null && discountedResources.size()>0)&&(resourceToLookFor.equals(discountedResources.get(0)) || resourceToLookFor.equals(discountedResources.get(1)))){
                return warehouse.amountOfResource(resourceToLookFor) + strongbox.amountOfResource(resourceToLookFor) + quantityInDepots + 1;
            }
        return warehouse.amountOfResource(resourceToLookFor) + strongbox.amountOfResource(resourceToLookFor) + quantityInDepots;
    }

    //returns the amount of resourceToLookFor when it is needed to activate a production
    public int availableResourcesForProduction(Resource resourceToLookFor){
        int quantityInDepots=0;
        for(int i=0; i<extraDepots.size();i++){
            if(extraDepots.get(i).getExtraDepotType().equals(resourceToLookFor))    quantityInDepots+=extraDepots.get(i).getExtraDepotSize();
        }
        return warehouse.amountOfResource(resourceToLookFor)+strongbox.amountOfResource(resourceToLookFor)+quantityInDepots;
    }

    /*this method removes an amount of resources from the dashboard: it first takes them from the warehouse,
    then from the extra deposits and then from the strongbox */
    public void removeResourcesFromDashboard(int quantity,Resource resourceToRemove) throws RegularityError {
        quantity = quantity - this.warehouse.removeResource(resourceToRemove,quantity);
        if (quantity != 0) {
            for (ExtraDepot extraDepot : this.extraDepots) {
                if (extraDepot.getExtraDepotType() == resourceToRemove) {
                    for (int i = extraDepot.getExtraDepotSize(); i > 0; i--) {
                        if(quantity!=0) {
                            quantity = quantity - 1;
                            extraDepot.removeResource();
                        }
                    }
                }
            }
            if(quantity != 0){
                strongbox.removeResourceWithAmount(resourceToRemove,quantity);
            }
        }
    }

    public void activateProd(DevelopmentCardZone zoneToActivate) throws RegularityError {
        zoneToActivate.getOnTopCard().produce(this);

    }

    // NON SO SE SIA MEGLIO PASSARE UNA RISORSA O UN INDICE, E COSA USARE PER CHECKARE
    //this method checks if there's an available Leader prod of the type of resource brought
    public boolean checkLeaderProdPossible(Resource resourceLeaderProdToCheck){
        for(Resource resourceOfLeaderProduct: resourcesForExtraProd){
            if (resourceOfLeaderProduct.getResourceType()==resourceLeaderProdToCheck.getResourceType()){
                if(availableResourcesForProduction(resourceLeaderProdToCheck)>=1){
                    return true;
                }
            }
        }
        return false;
    }

    //STESSO DUBBIO DEL METODO PRIMA
    //this method activates the leader production of the resource provided, and creates an amount of the resource chosen
    public void activateLeaderProd(Resource resourceLeaderProdToActivate, Resource resourceToObtain){

        this.papalPath.moveForward();
        if(resourceToObtain.getResourceType()== ResourceType.Faith){
            this.papalPath.moveForward();
        }else {
            this.produceResource(resourceToObtain);
        }
    }

    /*method that activates the always available standard production;
    it removes the first two resources given to add a resource of the second given type*/
    public void activateStandardProd(List <Resource> resourcesToGive,Resource resourceToObtain) throws RegularityError {
        for(Resource resourceToRemove: resourcesToGive){
            this.removeResourcesFromDashboard(1,resourceToRemove);
        }
        if(resourceToObtain.getResourceType()== ResourceType.Faith){
            this.getPapalPath().moveForward();
        }else {
            this.produceResource(resourceToObtain);
        }
    }

    public boolean checkProductionPossible(DevelopmentCardZone zoneToActivate) {
       return zoneToActivate.getOnTopCard().checkRequirements(this);
    }

    /* this method adds the resource produced in a temporary list
    that will be put in the strongbox at the end of the turn. */
    public void produceResource(Resource resourceProduced){
            this.resourcesProduced.add(resourceProduced);
    }

    /* this method is meant to be used at the end of the turn, to put all the resources created
    in the strongbox; it then clears the list to be used the next turn */
    public void endTurnMoveProduction(){
        for(Resource resourceToMove: this.resourcesProduced){
            this.strongbox.addResource(resourceToMove);
        }
        resourcesProduced.clear();
    }
}
