package it.polimi.ingsw.Model.boardsAndPlayer;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.Exceptions.NotEnoughResourcesToActivateProductionException;
import it.polimi.ingsw.Exceptions.WrongAmountOfResourcesException;
import it.polimi.ingsw.Model.developmentcard.DevelopmentCardZone;
import it.polimi.ingsw.Model.leadercard.LeaderCardZone;
import it.polimi.ingsw.Model.leadercard.leaderpowers.ExtraProd;
import it.polimi.ingsw.Model.papalpath.CardCondition;
import it.polimi.ingsw.Model.papalpath.PapalPath;
import it.polimi.ingsw.Model.resource.*;
import it.polimi.ingsw.Model.storing.ExtraDepot;
import it.polimi.ingsw.Model.storing.Strongbox;
import it.polimi.ingsw.Model.storing.Warehouse;

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

    public ArrayList<Resource> getResourcesUsableForProd(){
        ArrayList<Resource> list = new ArrayList<>();
        list.addAll(warehouse.getAllResources());
        list.addAll(strongbox.getAllResources());
        for (ExtraDepot extraDepot:extraDepots) {
            list.addAll(extraDepot.getAllResources());
        }
        return list;
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
        for(int i=0; i<3; i++)        this.developmentCardZones.add(new DevelopmentCardZone());
        this.papalPath = new PapalPath(playerOrder);
        this.extraDepots= new ArrayList<ExtraDepot>();
        this.whiteToColorResources = new ArrayList<Resource>();
        this.discountedResources = new ArrayList<Resource>();
        this.resourcesProduced= new ArrayList<Resource>();
        this.resourcesForExtraProd = new ArrayList<Resource>();
        //here we import the standard prod settings from json
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader("ingswAM2021-Dottor-Corti-Cutrupi/standardprodParameters.json"));
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

    public boolean checkGameIsEnded(){
        int numOfDevelopmentCards = 0;
        for(DevelopmentCardZone cardZone: developmentCardZones){
            numOfDevelopmentCards += cardZone.getCards().size();
        }
        if (numOfDevelopmentCards>=7||papalPath.getFaithPosition()>=24){
            return true;
        }else{
            return false;
        }
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

    public boolean checkBaseProductionPossible(ArrayList<Resource> resourcesToCheck){
        //0=coin; 1=stone; 2=shield; 3=servant
        int[] typeOfResource = new int[4];
        for (Resource resource:resourcesToCheck) {
            if(resource.getResourceType()==ResourceType.Coin) typeOfResource[0]++;
            if(resource.getResourceType()==ResourceType.Stone) typeOfResource[1]++;
            if(resource.getResourceType()==ResourceType.Shield) typeOfResource[2]++;
            if(resource.getResourceType()==ResourceType.Servant) typeOfResource[3]++;
        }

        if(typeOfResource[0]>0 && typeOfResource[0]>availableResourcesForProduction(new CoinResource())){
            return false;
        }
        if(typeOfResource[1]>0 && typeOfResource[1]>availableResourcesForProduction(new StoneResource())){
            return false;
        }
        if(typeOfResource[2]>0 && typeOfResource[2]>availableResourcesForProduction(new ShieldResource())){
            return false;
        }
        if(typeOfResource[3]>0 && typeOfResource[3]>availableResourcesForProduction(new ServantResource())){
            return false;
        }
        return true;
    }

    /**
     *Method removes the amount of resource to remove taking, in order ,from warehouse, extradepots and strongbox
     */
    public void removeResourcesFromDashboard(int quantity,Resource resourceToRemove) {
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
    public void activateProd(DevelopmentCardZone zoneToActivate)  {
        zoneToActivate.getLastCard().produce(this);
    }

    /**
     *this method checks if there's an available Leader prod of the type of resource brought
     */
    public boolean checkLeaderProdPossible(int index){
        Resource resourceLeaderProdToCheck = leaderCardZone.getLeaderCards().get(index).getLeaderPower().returnRelatedResource();
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

    public void activateBaseProd(ArrayList <Resource> resourcesToRemove, List<Resource> resourcesToProduce) throws WrongAmountOfResourcesException, NotEnoughResourcesToActivateProductionException {
        //CORRECT PATH: USER HAS INSERT THE CORRECT AMOUNT TO REMOVE (SAME AS THE REQUIRED TO MAKE BASE PRODUCTION)
        if (resourcesToRemove.size() == (this.numOfStandardProdRequirements) && resourcesToProduce.size() == this.numOfStandardProdResults) {
            //CORRECT PATH: USER HAS ENOUGH RESOURCES TO ACTIVATE BASE PROD
            if(checkBaseProductionPossible(resourcesToRemove)){
                for (Resource resourceToRemove : resourcesToRemove) {
                    this.removeResourcesFromDashboard(1, resourceToRemove);
                }
                for (Resource resourceToProduce : resourcesToProduce) {
                    resourceToProduce.effectFromProduction(this);
                }
            }

            //WRONG PATH: USER HASN'T GOT ENOUGH RESOURCES TO ACTIVATE BASE PROD
            else throw new NotEnoughResourcesToActivateProductionException();
        }

        //WRONG PATH: USER HAS INSERT AN INCORRECT AMOUNT OF RESOURCES
        else throw new WrongAmountOfResourcesException();
    }

    /**
     * method checks if the requirements of the card are fulfilled
     */
    public boolean checkProductionPossible(DevelopmentCardZone zoneToActivate) {
       return zoneToActivate.getLastCard().checkRequirements(this);
    }

    public void leaderProd(int index, Resource resourceWanted ){
        if(leaderCardZone.getLeaderCards().get(index).getCondition()==CardCondition.Active &&(
            leaderCardZone.getLeaderCards().get(index).getLeaderPower() instanceof ExtraProd)) {
            removeResourcesFromDashboard(1, leaderCardZone.getLeaderCards().get(index).getLeaderPower().returnRelatedResource());
            produceResource(resourceWanted);
        }
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
