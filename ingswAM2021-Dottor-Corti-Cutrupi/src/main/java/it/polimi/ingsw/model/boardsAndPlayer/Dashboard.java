package it.polimi.ingsw.model.boardsAndPlayer;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.exception.NotEnoughResourcesToActivateProductionException;
import it.polimi.ingsw.exception.PapalCardActivatedException;
import it.polimi.ingsw.exception.WrongAmountOfResourcesException;
import it.polimi.ingsw.model.developmentcard.DevelopmentCard;
import it.polimi.ingsw.model.developmentcard.DevelopmentCardZone;
import it.polimi.ingsw.model.leadercard.LeaderCardZone;
import it.polimi.ingsw.model.leadercard.leaderpowers.ExtraProd;
import it.polimi.ingsw.model.leadercard.leaderpowers.PowerType;
import it.polimi.ingsw.model.papalpath.CardCondition;
import it.polimi.ingsw.model.papalpath.PapalPath;
import it.polimi.ingsw.model.resource.*;
import it.polimi.ingsw.model.storing.ExtraDepot;
import it.polimi.ingsw.model.storing.Strongbox;
import it.polimi.ingsw.model.storing.Warehouse;

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
    private transient PapalPath papalPath;
    String json1="ingswAM2021-Dottor-Corti-Cutrupi/src/main/resources/standardprodParameters.json";

    //resources produced in this turn, at the end of the turn they will be moved in the strongbox
    private ArrayList <Resource> resourcesProduced;
    //number of resources consumed by the standard prod
    private int numOfStandardProdRequirements;
    //number of resources produced by the standard prod
    private int numOfStandardProdResults;


    private ArrayList<Resource> discountedResources;

    //resources that represent the extra productions brought by the Leader Power
    private ArrayList <ArrayList<Resource>> resourcesForExtraProd;

    private ArrayList<ExtraDepot> extraDepots;

    private ArrayList<ArrayList<Resource>> whiteToColorResources;



    public ArrayList<Resource> getResourcesProduced() {
        return resourcesProduced;
    }

    public void activateDiscountCard(ArrayList<Resource> discountedResources){
        for(Resource resource: discountedResources){
            this.discountedResources.add(resource);
        }
    }
    public void addExtraDepot(ExtraDepot extraDepot){
        extraDepots.add(extraDepot);
    }

    public void addNewWhiteToColorEffect(ArrayList<Resource> list){
        whiteToColorResources.add(list);
    }

    public void activateWhiteToColorCard(int index) throws PapalCardActivatedException {
        for (Resource resource:whiteToColorResources.get(index)) {
            resource.effectFromMarket(this);
        }
    }

    public ArrayList<ArrayList<Resource>> getWhiteToColorResources(){
        return new ArrayList<ArrayList<Resource>>(this.whiteToColorResources);
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

    public ArrayList <ArrayList<Resource>> getResourcesForExtraProd() {
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
        this.whiteToColorResources = new ArrayList<ArrayList<Resource>>();
        this.discountedResources = new ArrayList<Resource>();
        this.resourcesProduced= new ArrayList<Resource>();
        this.resourcesForExtraProd = new ArrayList <ArrayList<Resource>>();
        //here we import the standard prod settings from json
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader(json1));
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
            if(extraDepots.get(i).getExtraDepotType().equals(resourceToLookFor.getResourceType()))    quantityInDepots+=extraDepots.get(i).getExtraDepotSize();
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
            if(extraDepots.get(i).getExtraDepotType().equals(resourceToLookFor.getResourceType()))    quantityInDepots+=extraDepots.get(i).getExtraDepotSize();
        }
        return warehouse.amountOfResource(resourceToLookFor)+strongbox.amountOfResource(resourceToLookFor)+quantityInDepots;
    }


    public int allAvailableResources(Resource resourceToLookFor){
        int quantityInDepots=0;
        for(int i=0; i<extraDepots.size();i++){
            if(extraDepots.get(i).getExtraDepotType().equals(resourceToLookFor.getResourceType()))    quantityInDepots+=extraDepots.get(i).getExtraDepotSize();
        }
        int quantityProduced=0;
        for(Resource resource:resourcesProduced)    if(resource.getResourceType()==resourceToLookFor.getResourceType()) quantityProduced++;
        return warehouse.amountOfResource(resourceToLookFor)+strongbox.amountOfResource(resourceToLookFor)+quantityInDepots+quantityProduced;
    }

    public int producedThisTurn(ResourceType resourceToLookFor){
        int quantityProduced=0;
        for(Resource resource:resourcesProduced)    if(resource.getResourceType()==resourceToLookFor) quantityProduced++;
        return quantityProduced;
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
                if (extraDepot.getExtraDepotType().equals(resourceToRemove.getResourceType())) {
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
    public void activateDevProd(int index) throws PapalCardActivatedException {
        developmentCardZones.get(index).activateProd(this);
    }

    /**
     *this method checks if there's an available Leader prod of the type of resource brought
     */
    public boolean checkLeaderProdPossible(int index){
        ArrayList <Resource> resourcesLeaderProdToCheck = leaderCardZone.getLeaderCards().get(index).getLeaderPower().returnRelatedResources();

        for(ArrayList<Resource> resourcesToCheck: this.resourcesForExtraProd){
            if (resourcesToCheck.equals(resourcesLeaderProdToCheck)){
                //we use the check base production possible method because it dose what we need even if it was created for something else
                if(checkBaseProductionPossible(resourcesToCheck)){
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

    public void activateBaseProd(ArrayList <Resource> resourcesToRemove, List<Resource> resourcesToProduce) throws WrongAmountOfResourcesException, NotEnoughResourcesToActivateProductionException, PapalCardActivatedException {
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
    public boolean checkProductionPossible(int index) {
        return developmentCardZones.get(index).checkProdPossible(this);
    }

    public void leaderProd(int index,ArrayList <Resource> resourcesWanted ){
        if(leaderCardZone.getLeaderCards().get(index).getCondition()==CardCondition.Active &&(
            leaderCardZone.getLeaderCards().get(index).getLeaderPower() instanceof ExtraProd)) {
            for(Resource resourceToRemove: leaderCardZone.getLeaderCards().get(index).getLeaderPower().returnRelatedResources()){
                removeResourcesFromDashboard(1,resourceToRemove);
            }
            for(Resource resourceWanted: resourcesWanted) {
                produceResource(resourceWanted);
            }
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

    public void addToExtraDepot(Resource resource){
        
    }

    public void addToExtraProd(ArrayList <Resource> resourcesRequired){
        this.resourcesForExtraProd.add(resourcesRequired);
    }


    public void moveForward() throws PapalCardActivatedException {
        papalPath.moveForward();
    }

    public void buyCard(int index, DevelopmentCard card){
        developmentCardZones.get(index).addNewCard(card);
    }

    public boolean leaderPowerTypeProd(int index){
        if(leaderCardZone.getLeaderCards().get(index).getLeaderPower().returnPowerType().equals(PowerType.ExtraProd)) return true;
        return false;
    }

    public boolean isLeaderActive(int index){
        return leaderCardZone.isLeaderActive(index);
    }

    public boolean isLeaderInactive(int index){
        return leaderCardZone.isLeaderInactive(index);
    }

    public void discardCard(int index){
        leaderCardZone.removeCard(index);
    }

    public void activateLeaderCard(int index){
        leaderCardZone.activateCard(index,this);
    }

    public boolean leaderCardRequirementsFulfilled(int index){
        return leaderCardZone.checkRequirements(index, this);
    }
}
