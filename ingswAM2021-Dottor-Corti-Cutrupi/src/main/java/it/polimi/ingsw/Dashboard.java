package it.polimi.ingsw;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.Communication.client.actions.mainActions.productionActions.LeaderProductionAction;
import it.polimi.ingsw.Exceptions.NotEnoughResourcesToActivateProductionException;
import it.polimi.ingsw.developmentcard.DevelopmentCardZone;
import it.polimi.ingsw.leadercard.LeaderCardZone;
import it.polimi.ingsw.papalpath.PapalPath;
import it.polimi.ingsw.resource.*;
import it.polimi.ingsw.storing.ExtraDepot;
import it.polimi.ingsw.storing.RegularityError;
import it.polimi.ingsw.storing.Strongbox;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Dashboard {
    /** needs a method to handle the development card zones
     *
     */
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
    //number of resources consumed by the standard prod
    private int numOfStandardProdRequirements;
    //number of resources produced by the standard prod
    private int numOfStandardProdResults;


    public ArrayList<Resource> getResourcesProduced() {
        return resourcesProduced;
    }

    public void activateDiscountCard(Resource discountedResource){
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



    public int getNumOfStandardProdRequirements() {
        return numOfStandardProdRequirements;
    }

    public int getNumOfStandardProdResults() {
        return numOfStandardProdResults;
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
        //here we import the standard prod settings from json
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader("standardprodParameters.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        JsonParser parser = new JsonParser();
        JsonArray cardsArray = parser.parse(reader).getAsJsonArray();
        Gson gson = new Gson();
        int[] arr = gson.fromJson(cardsArray, int[].class);
        this.numOfStandardProdRequirements=arr[0];
        this.numOfStandardProdResults=arr[1];
    }


    /**
     *returns the amount of resourceToLookFor when it is needed to buy a development card
     */
    public int availableResourcesForDevelopment(Resource resourceToLookFor){
        int quantityInDepots=0;
        for(int i=0; i<extraDepots.size();i++){
            if(extraDepots.get(i).getExtraDepotType().getResourceType().equals(resourceToLookFor.getResourceType()))    quantityInDepots+=extraDepots.get(i).getExtraDepotSize();
        }
            if((discountedResources!=null && discountedResources.size()>0)&&( resourceToLookFor.getResourceType().equals(discountedResources.get(0).getResourceType()) ||(discountedResources.size()>1 && resourceToLookFor.getResourceType().equals(discountedResources.get(1).getResourceType())))){
                return warehouse.amountOfResource(resourceToLookFor) + strongbox.amountOfResource(resourceToLookFor) + quantityInDepots + 1;
            }
        return warehouse.amountOfResource(resourceToLookFor) + strongbox.amountOfResource(resourceToLookFor) + quantityInDepots;
    }

    /**
     *returns the amount of resourceToLookFor when it is needed to activate a production
     */
    public int availableResourcesForProduction(Resource resourceToLookFor){
        int quantityInDepots=0;
        for(int i=0; i<extraDepots.size();i++){
            if(extraDepots.get(i).getExtraDepotType().getResourceType().equals(resourceToLookFor.getResourceType()))    quantityInDepots+=extraDepots.get(i).getExtraDepotSize();
        }
        return warehouse.amountOfResource(resourceToLookFor)+strongbox.amountOfResource(resourceToLookFor)+quantityInDepots;
    }

    /**
     *Method removes the amount of resource to remove taking, in order ,from warehouse, extradepots and strongbox
     */
    public void removeResourcesFromDashboard(int quantity,Resource resourceToRemove) throws NotEnoughResourcesToActivateProductionException {
        quantity -= this.warehouse.removeResource(resourceToRemove,quantity);
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

    /**
     *Calls the method of the card that produces
     */
    public void activateProd(DevelopmentCardZone zoneToActivate) throws RegularityError, NotEnoughResourcesToActivateProductionException {
        zoneToActivate.getLastCard().produce(this);
    }

    /**
     *this method checks if there's an available Leader prod of the type of resource brought
     */
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

    /**
     * method that activates the always available standard production;
     * it checks if the number of resources provided for the production is correct
     */

    public void activateStandardProd(List <Resource> resourcesToRemove,List<Resource> resourcesToProduce) throws NotEnoughResourcesToActivateProductionException {
        if (resourcesToRemove.size() == (this.numOfStandardProdRequirements) && resourcesToProduce.size() == this.numOfStandardProdResults) {
            for (Resource resourceToRemove : resourcesToRemove) {
                this.removeResourcesFromDashboard(1, resourceToRemove);
            }
            for (Resource resourceToProduce : resourcesToProduce) {
                resourceToProduce.effectFromProduction(this);
            }
        }
    }

    /**
     * method checks if the requirements of the card are fulfilled
     */
    public boolean checkProductionPossible(DevelopmentCardZone zoneToActivate) {
       return zoneToActivate.getLastCard().checkRequirements(this);
    }

    public boolean leaderProd(int index, Resource resourceWanted ){
        try {
            removeResourcesFromDashboard(1,leaderCardZone.getLeaderCards().get(index).getLeaderPower().returnRelatedResource());
            produceResource(resourceWanted);
            return true;
        } catch (NotEnoughResourcesToActivateProductionException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * this method adds the resource produced in a temporary list
     *     that will be put in the strongbox at the end of the turn.
     */
    public void produceResource(Resource resourceProduced){
            this.resourcesProduced.add(resourceProduced);
    }

    /** this method is meant to be used at the end of the turn, to put all the resources created
     *  in the strongbox; it then clears the list to be used the next turn
     */
    public void moveResourcesProducedToStrongbox(){
        for(Resource resourceToMove: this.resourcesProduced){
            this.strongbox.addResource(resourceToMove);
        }
        resourcesProduced.clear();
    }

    /**
     * used to check if the player has 7 development cards and the game should end
     */
    public int numberOfDevCards(){
        int num=0;
        for (DevelopmentCardZone developmentCardZone : this.getDevelopmentCardZones()  ) {
            num+=developmentCardZone.getSize();
        }
        return num;
    }


    public void addStartingResource(ResourceType resourceType){
        Resource resource;
        switch (resourceType){
            case Coin:
                resource= new CoinResource();
                break;
            case Servant:
                resource= new ServantResource();
                break;
            case Shield:
                resource= new ShieldResource();
                break;
            case Stone:
                resource= new StoneResource();
                break;
            default:
                resource= new BlankResource();
        }
        warehouse.addResource(resource);
    }
}
