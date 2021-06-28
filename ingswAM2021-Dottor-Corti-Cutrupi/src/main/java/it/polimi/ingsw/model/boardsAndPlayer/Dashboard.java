package it.polimi.ingsw.model.boardsAndPlayer;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.exception.warehouseErrors.WarehouseDepotsRegularityError;
import it.polimi.ingsw.model.developmentcard.DevelopmentCard;
import it.polimi.ingsw.model.developmentcard.DevelopmentCardZone;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.leadercard.LeaderCardZone;
import it.polimi.ingsw.model.leadercard.leaderpowers.PowerType;
import it.polimi.ingsw.model.papalpath.PapalPath;
import it.polimi.ingsw.model.resource.*;
import it.polimi.ingsw.model.storing.ExtraDepot;
import it.polimi.ingsw.model.storing.Strongbox;
import it.polimi.ingsw.model.storing.Warehouse;
import it.polimi.ingsw.server.messages.jsonMessages.SerializationConverter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player's dashboard. Contains his resources, leader cards, development cards' zones, and papal path
 */
public class Dashboard {
    /** needs a method to handle the development card zones
     *
     */
    private final Warehouse warehouse;
    private final Strongbox strongbox;
    private final LeaderCardZone leaderCardZone;
    private final ArrayList <DevelopmentCardZone> developmentCardZones;
    private final transient PapalPath papalPath;
    String json1="src/main/resources/standardprodParameters.json";

    //resources produced in this turn, at the end of the turn they will be moved in the strongbox
    private final ArrayList <Resource> resourcesProduced;
    //number of resources consumed by the standard prod
    private int numOfStandardProdRequirements;
    //number of resources produced by the standard prod
    private int numOfStandardProdResults;


    private final ArrayList<Resource> discountedResources;

    //resources that represent the extra productions brought by the Leader Power
    private final ArrayList <ArrayList<Resource>> resourcesForExtraProd;

    private final ArrayList<ExtraDepot> extraDepots;

    private final ArrayList<ArrayList<Resource>> whiteToColorResources;


    /**
     * @return an array list containing all resources produced this turn
     */
    public ArrayList<Resource> getResourcesProduced() {
        return resourcesProduced;
    }

    /**
     * Adds the resources in the card activated to the ones already discounted
     * @param discountedResources: what is going to be discounted
     */
    public void activateDiscountCard(ArrayList<Resource> discountedResources){
        for(Resource resource: discountedResources){
            this.discountedResources.add(resource);
        }
    }

    /**
     * Adds a new extra depot in the dashboard
     */
    public void addExtraDepot(ExtraDepot extraDepot){
        extraDepots.add(extraDepot);
    }

    /**
     * Adds another choice of resources to substitute a blank marble with, when acquiring from market
     * @param list
     */
    public void addNewWhiteToColorEffect(ArrayList<Resource> list){
        whiteToColorResources.add(list);
    }

    /**
     * Used when a blank resource gets taken while having some white to color leader card active
     * @param index: what card is being used to substitute the blank
     */
    public void activateWhiteToColorCard(int index) throws PapalCardActivatedException {
        for (Resource resource:whiteToColorResources.get(index)) {
            resource.effectFromMarket(this);
        }
    }

    /**
     * @return the various choices of resources a player can substitute a blank marble with
     */
    public ArrayList<ArrayList<Resource>> getWhiteToColorResources(){
        return new ArrayList<>(this.whiteToColorResources);
    }

    public Warehouse getWarehouse() {
        return new Warehouse(warehouse);
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

    /**
     * @return the list of extra depots active
     */
    public ArrayList<ExtraDepot> getExtraDepots() {
        return extraDepots;
    }

    /**
     * @return the list of resources a development card leader is discounter of when buying it
     */
    public ArrayList<Resource> getDiscountedResources() {
        return discountedResources;
    }

    /**
     * @return the resources a player posses, except those produced this round
     */
    public ArrayList<Resource> resourcesUsableForProd(){
        ArrayList<Resource> list = new ArrayList<>();
        list.addAll(warehouse.getAllResources());
        list.addAll(strongbox.getAllResources());
        for (ExtraDepot extraDepot:extraDepots) {
            list.addAll(extraDepot.getAllResources());
        }
        ArrayList<Resource> copyArray=new ArrayList<>();
        for(Resource resource:list) copyArray.add(copyResource(resource.getResourceType()));
        return copyArray;
    }

    /**
     * @return a new resource of the same type given
     */
    public Resource copyResource(ResourceType resourceType){
        switch (resourceType){
            case Coin:
                return new CoinResource();
            case Servant:
                return new ServantResource();
            case Shield:
                return new ShieldResource();
            case Stone:
                return new StoneResource();
            default:
                return new BlankResource();
        }
    }

    /**
     * @return the number of resources needed to perform the standard production
     */
    public int getNumOfStandardProdRequirements() {
        return numOfStandardProdRequirements;
    }

    /**
     * @return the number of resources created while performing the standard production
     */
    public int getNumOfStandardProdResults() {
        return numOfStandardProdResults;
    }


    /**
     * Creates a new dashboard
     * @param playerOrder: used to set the starting faith position
     */
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

    /**
     * Creates a copy of the dashboard given
     */
    public Dashboard(Dashboard copy) {
        this.warehouse = copy.warehouse;
        this.strongbox = copy.strongbox;
        this.leaderCardZone = copy.leaderCardZone;
        this.developmentCardZones = copy.developmentCardZones;
        this.papalPath = copy.papalPath;
        this.extraDepots= copy.extraDepots;
        this.whiteToColorResources = copy.whiteToColorResources;
        this.discountedResources = copy.discountedResources;
        this.resourcesProduced= copy.resourcesProduced;
        this.resourcesForExtraProd = copy.resourcesForExtraProd;
    }

    /**
     * Called to check if a player has fulfilled a condition to end the game
     * @return true if he has, false otherwise
     */
    public boolean checkGameIsEnded(){
        int numOfDevelopmentCards = 0;
        for(DevelopmentCardZone cardZone: developmentCardZones){
            numOfDevelopmentCards += cardZone.getSize();
        }
        return numOfDevelopmentCards >= 7 || papalPath.getFaithPosition() >= 24;
    }

    /**
     *returns the amount of resourceToLookFor when it is needed to buy a development card
     */
    public int availableResourcesForDevelopment(Resource resourceToLookFor){
        int quantityInDepots=0;
        int discounted=0;
        for(int i=0; i<extraDepots.size();i++){
            if(extraDepots.get(i).getExtraDepotType().equals(resourceToLookFor.getResourceType()))    quantityInDepots+=extraDepots.get(i).getAmountOfContainedResources();
        }
        for(Resource resourceDiscounted: discountedResources){
            if(resourceToLookFor.getResourceType()==resourceDiscounted.getResourceType()){
                for(LeaderCard leaderCard: leaderCardZone.getLeaderCards()){
                    if (leaderCard.getLeaderPower().returnRelatedResourcesCopy().get(0).getResourceType()==resourceToLookFor.getResourceType())
                        discounted=leaderCard.getLeaderPower().returnRelatedResourcesCopy().size();
                }
                return warehouse.amountOfResource(resourceToLookFor) + strongbox.amountOfResource(resourceToLookFor) + quantityInDepots +  discounted;
            }
        }
        return warehouse.amountOfResource(resourceToLookFor) + strongbox.amountOfResource(resourceToLookFor) + quantityInDepots;
    }

    /**
     *returns the amount of resourceToLookFor when it is needed to activate a production
     */
    public int availableResourcesForProduction(Resource resourceToLookFor){
        int quantityInDepots=0;
        for(int i=0; i<extraDepots.size();i++){
            if(extraDepots.get(i).getExtraDepotType().equals(resourceToLookFor.getResourceType()))    quantityInDepots+=extraDepots.get(i).getAmountOfContainedResources();
        }
        return warehouse.amountOfResource(resourceToLookFor)+strongbox.amountOfResource(resourceToLookFor)+quantityInDepots;
    }

    /**
     * Returns player's number of resource of a certain type, considering also those he has just produced
     * @param resourceToLookFor: resource he wants the quantity of
     * @return its quantity
     */
    public int allAvailableResources(Resource resourceToLookFor){
        int quantityInDepots=0;
        for(int i=0; i<extraDepots.size();i++){
            if(extraDepots.get(i).getExtraDepotType().equals(resourceToLookFor.getResourceType()))    quantityInDepots+=extraDepots.get(i).getAmountOfContainedResources();
        }
        int quantityProduced=0;
        for(Resource resource:resourcesProduced)    if(resource.getResourceType()==resourceToLookFor.getResourceType()) quantityProduced++;
        return warehouse.amountOfResource(resourceToLookFor)+strongbox.amountOfResource(resourceToLookFor)+quantityInDepots+quantityProduced;
    }
    /**
     *
     * Returns player's quantity produced of a certain resource
     * @param resourceToLookFor: resource he wants the quantity of
     * @return its quantity
     */
    public int producedThisTurn(ResourceType resourceToLookFor){
        int quantityProduced=0;
        for(Resource resource:resourcesProduced)    if(resource.getResourceType()==resourceToLookFor) quantityProduced++;
        return quantityProduced;
    }

    /**
     * @param resourcesToCheck resources selected as price to activate the base production
     * @return true if he has those resources, false otherwise
     */
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
        return typeOfResource[3] <= 0 || typeOfResource[3] <= availableResourcesForProduction(new ServantResource());
    }

    /**
     *Method removes the amount of resource to remove taking, in order ,from warehouse, extradepots and strongbox
     */
    public void removeResourcesFromDashboard(int quantity,Resource resourceToRemove) {
        quantity -= warehouse.removeResource(resourceToRemove,quantity);
        if (quantity != 0) {
            for (ExtraDepot extraDepot : this.extraDepots) {
                if (extraDepot.getExtraDepotType().equals(resourceToRemove.getResourceType())) {
                    for (int i = extraDepot.getAmountOfContainedResources(); i > 0; i--) {
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
     *Calls {@link it.polimi.ingsw.model.developmentcard.DevelopmentCardZone#activateProd(Dashboard this)}
     */
    public void activateDevProd(int index) throws PapalCardActivatedException {
        developmentCardZones.get(index).activateProd(this);
    }

    /**
     *this method checks if there's an available Leader prod of the type of resource brought
     */
    public boolean checkLeaderProdPossible(int index){
        ArrayList <Resource> resourcesLeaderProdToCheck = leaderCardZone.getLeaderCards().get(index).getLeaderPower().returnRelatedResourcesCopy();
        for(ArrayList<Resource> resourcesToCheck: this.resourcesForExtraProd){
            if (compareArrayOfResources(resourcesToCheck,resourcesLeaderProdToCheck)){
                //we use the check base production possible method because it dose what we need even if it was created for something else
                if(checkBaseProductionPossible(resourcesToCheck)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Method that compares two arrays of resources, returns true if they are the same
     * @param array1
     * @param array2
     * @return
     */
    public boolean compareArrayOfResources(ArrayList<Resource> array1,ArrayList<Resource> array2){
        int i=0;
        for(Resource resource1: array1){
            if(!resource1.getResourceType().equals(array2.get(i).getResourceType())){
                return false;
            }
            i++;
        }
        return true;
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

    /**
     * Removes the resource needed to perform the production, then produce what the player chose
     * @param index: index of the leader card to use
     * @param resourcesWanted: resources the player has selected as productions
     */
    public void leaderProd(int index,ArrayList <Resource> resourcesWanted ){
        if(leaderCardZone.isLeaderActive(index) && leaderPowerTypeProd(index)) {
            for(Resource resourceToRemove: leaderCardZone.getLeaderCards().get(index).getLeaderPower().returnRelatedResourcesCopy()){
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
     * Used to add the required resources of each leader production to the dashboard
     * @param resourcesRequired: price for the production
     */
    public void addToExtraProd(ArrayList <Resource> resourcesRequired){
        this.resourcesForExtraProd.add(resourcesRequired);
    }

    /**
     * Moves the player of one tile in the papal path
     * @throws PapalCardActivatedException: warns the controller to check the position of all players in the papal path
     */
    public void moveForward() throws PapalCardActivatedException {
        papalPath.moveForward();
    }

    /**
     * Calls {@link it.polimi.ingsw.model.developmentcard.DevelopmentCardZone#addNewCard(DevelopmentCard)}
     */
    public void buyCard(int index, DevelopmentCard card){
        developmentCardZones.get(index).addNewCard(card);
    }

    /**
     * @param index leader card selected
     * @return  true if the selected leader card grants a production as its effect, false otherwise
     */
    public boolean leaderPowerTypeProd(int index){
        return leaderCardZone.getLeaderCards().get(index).getLeaderPower().returnPowerType().equals(PowerType.ExtraProd);
    }

    public boolean isLeaderActive(int index){
        return leaderCardZone.isLeaderActive(index);
    }

    public boolean isLeaderInactive(int index){
        return leaderCardZone.isLeaderInactive(index);
    }

    /**
     * Calls {@link it.polimi.ingsw.model.leadercard.LeaderCardZone#removeCard(int index)}
     * @param index: index of the card to remove
     */
    public void discardCard(int index){
        leaderCardZone.removeCard(index);
    }

    /**
     * Calls {@link it.polimi.ingsw.model.leadercard.LeaderCardZone#activateCard(int index, Dashboard this)}
     * @param index: index of the card to activate
     */
    public void activateLeaderCard(int index){
        leaderCardZone.activateCard(index,this);
        try {
            warehouse.swapResources();
        } catch (WarehouseDepotsRegularityError ignored) {
        }
    }

    /**
     * @param index card to check
     * @return true if the conditions are fulfilled, false otherwise
     */
    public boolean leaderCardRequirementsFulfilled(int index){
        return leaderCardZone.checkRequirements(index, this);
    }

    /**
     * Used at the start of the game, gives the player a leader card
     */
    public void drawCard(LeaderCard card) {
        leaderCardZone.addNewCard(card);
    }

    /**
     * Remouve a single resource from the warehouse
     * @param index: each number represents a resource
     */
    public void removeResourceFromWarehouse(int index) throws WarehouseDepotsRegularityError {
        warehouse.removeResource(index);
    }


    /**
     * @return  {@link it.polimi.ingsw.model.storing.Warehouse#returnLengthOfDepot(int index)}
     */
    public int lengthOfDepot(int index) {
        return warehouse.returnLengthOfDepot(index);
    }

    /**
     * Calls {@link Warehouse#swapResources()}
     */
    public void swapResources() throws WarehouseDepotsRegularityError {
        warehouse.swapResources();
    }

    /**
     * Calls {@link Warehouse#removeExceedingDepot(int indexOfDepotToRemove)}
     */
    public int removeExceedingDepot(int index) throws WarehouseDepotsRegularityError {
        return warehouse.removeExceedingDepot(index);
    }

    /**
     * @return {@link PapalPath#getFaithPosition()}
     */
    public int getFaith() {
        return papalPath.getFaithPosition();
    }

    /**
     * @return {@link PapalPath#cardsActivated()}
     */
    public int numberOfActivatedPapalCards(){
        return papalPath.cardsActivated();
    }

    /**
     * @return {@link PapalPath#cardsActivated()}==0
     */
    public boolean isAtLeastAPapalCardActivated(){
        return papalPath.cardsActivated()==0;
    }

    /**
     * Calls {@link PapalPath#moveForwardLorenzo()}
     * @throws LorenzoWonTheMatch if lorenzo reaches position 24
     * @throws LorenzoActivatesPapalCardException if Lorenzo activated a card but the player doesn't
     * @throws BothPlayerAndLorenzoActivatePapalCardException self explanatory
     */
    public void moveForwardLorenzo() throws LorenzoWonTheMatch, LorenzoActivatesPapalCardException, BothPlayerAndLorenzoActivatePapalCardException {
        papalPath.moveForwardLorenzo();
    }

    /**
     * Calls {@link PapalPath#moveForwardLorenzo(int num)}
     * @throws LorenzoWonTheMatch if lorenzo reaches position 24
     * @throws LorenzoActivatesPapalCardException if Lorenzo activated a card but the player doesn't
     * @throws BothPlayerAndLorenzoActivatePapalCardException self explanatory
     */
    public void moveForwardLorenzo(int amount) throws LorenzoWonTheMatch, LorenzoActivatesPapalCardException, BothPlayerAndLorenzoActivatePapalCardException {
        papalPath.moveForwardLorenzo(amount);
    }

    /**
     * @return {@link PapalPath#getNextCardToActivatePosition()}
     */
    public int nextPapalCardToActivateInfo(){
        return papalPath.getNextCardToActivatePosition();
    }

    /**
     * Calls {@link Warehouse#addResource(Resource resource)}
     */
    public void addResourceToWarehouse(Resource resource){
        warehouse.addResource(resource);
    }

    /**
     * Calls {@link Strongbox#addResource(Resource resource)}
     */
    public void addResourceToStrongbox(Resource resource){
        strongbox.addResource(resource);
    }

    /**
     * @param index index of the development card zone the card selected is contained in
     * @return {@link DevelopmentCardZone#getCards()}
     */
    public List <DevelopmentCard> getDevelopmentCardsInAdevCardZone(int index) {
        return developmentCardZones.get(index).getCards();
    }
}
