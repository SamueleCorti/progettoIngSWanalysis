package it.polimi.ingsw.model.boardsAndPlayer;

import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.adapters.ResourceDuplicator;
import it.polimi.ingsw.model.developmentcard.Color;
import it.polimi.ingsw.model.developmentcard.DevelopmentCard;
import it.polimi.ingsw.model.developmentcard.DevelopmentCardZone;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.leadercard.LeaderCardZone;
import it.polimi.ingsw.model.leadercard.leaderpowers.PowerType;
import it.polimi.ingsw.exception.OutOfBoundException;
import it.polimi.ingsw.model.papalpath.CardCondition;
import it.polimi.ingsw.model.papalpath.PapalPath;
import it.polimi.ingsw.model.resource.*;
import it.polimi.ingsw.exception.warehouseErrors.WarehouseDepotsRegularityError;
import it.polimi.ingsw.model.storing.Strongbox;
import it.polimi.ingsw.model.storing.Warehouse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {
    private String nickname;
    private int order;  //from 0 to 3
    private int victoryPoints;
    private Dashboard dashboard;
    private GameBoard gameBoard;

    public Player(String nickname, int order,GameBoard gameBoard) {
        this.nickname=nickname;
        this.order=order;
        this.dashboard= new Dashboard(order);
        this.gameBoard = gameBoard;
    }

    public Player(String nickname, int order,GameBoard gameBoard, String favorCardsFA,String papalPathTilesFA,String standardProdParametersFA) {
        this.nickname=nickname;
        this.order=order;
        this.dashboard= new Dashboard(order,favorCardsFA,papalPathTilesFA,standardProdParametersFA);
        this.gameBoard = gameBoard;
    }

    //case single-player game
    public Player(String nickname,GameBoard gameBoard) {
        this.nickname = nickname;
        this.order=1;
        this.dashboard= new Dashboard(order);
        this.gameBoard = gameBoard;
    }

    public Player(String nickname,GameBoard gameBoard,String favorCardsFA,String papalPathTilesFA,String standardProdParametersFA) {
        this.nickname = nickname;
        this.order=1;
        this.dashboard= new Dashboard(order,favorCardsFA,papalPathTilesFA,standardProdParametersFA);
        this.gameBoard = gameBoard;
    }


    public String getNickname() {
        String string = nickname;
        return string;
    }

    public int getOrder() {
        return order;
    }


    /**
     *Method that returns the amount of victoryPoints of a player in a certain moment of the game
     */
    public int getVictoryPoints() {
        int victoryPoints=0;
        //sums the victory points for each development card in the player's dashBboard
        for(DevelopmentCardZone developmentCardZone : dashboard.getDevelopmentCardZones()){
            for (DevelopmentCard developmentCard:developmentCardZone.getCards()) {
                victoryPoints+=developmentCard.getVictoryPoints();
            }
        }
        //sums the victory points related to the player's position in the papal path
        victoryPoints+=dashboard.getPapalPath().getVictoryPoints();
        //sums the victory points related to the pope meeting cards
        for (LeaderCard leaderCard : dashboard.getLeaderCardZone().getLeaderCards()) {
            if(leaderCard.getCondition().equals(CardCondition.Active)){
                victoryPoints+=leaderCard.getVictoryPoints();
            }
        }
        //sums the victory points related to the remaining resources
        victoryPoints += ((dashboard.availableResourcesForProduction(new CoinResource())+dashboard.availableResourcesForProduction(new ServantResource())+
                dashboard.availableResourcesForProduction(new StoneResource())+dashboard.availableResourcesForProduction(new ShieldResource()))/5);
        return victoryPoints;
    }

    /**
     *Method used to get the resources from the selected row/column of the market and put them in the warehouse
     */
    public void acquireResourcesFromMarket(GameBoard gameBoard, boolean isRow, int index) throws OutOfBoundException, WarehouseDepotsRegularityError, PapalCardActivatedException {
        gameBoard.acquireResourcesFromMarket(isRow,index,dashboard);
    }

    public int baseProductionRequirements(){
        return dashboard.getNumOfStandardProdRequirements();
    }

    public int baseProductionResults(){
        return dashboard.getNumOfStandardProdResults();
    }

    public ArrayList<Resource> resourcesUsableForProd(){
        return dashboard.resourcesUsableForProd();
    }

    public ArrayList<Resource> resourcesProduced(){
        return dashboard.getResourcesProduced();
    }

    public int numOfLeaderCards(){
        return getLeaderCardZone().getLeaderCards().size();
    }

    /**
     * Method to buy a card from the gameBoard. The parameters color and level indicate the card to buy and
     * developmentCardZone indicates the zone to put the card in
     */
    public void buyDevelopmentCard(Color color, int level,int index,GameBoard gameBoard) throws NotCoherentLevelException, NotEnoughResourcesException{
        DevelopmentCard developmentCard;
        if((level>1 && dashboard.getDevelopmentCardZones().get(index).copyLastCard()==null)
                ||(dashboard.getDevelopmentCardZones().get(index).copyLastCard()!=null && dashboard.getDevelopmentCardZones().get(index).copyLastCard().getCardStats().getValue0()==1 && level==3)||
                (dashboard.getDevelopmentCardZones().get(index).copyLastCard()!=null && dashboard.getDevelopmentCardZones().get(index).copyLastCard().getCardStats().getValue0()==level)||
                (level==1 && dashboard.getDevelopmentCardZones().get(index).copyLastCard()!=null)){
                throw new NotCoherentLevelException();
        }
        else if ((gameBoard.getDeckOfChoice(color,level).getFirstCard().checkPrice(dashboard)) && ((level==1 && dashboard.getDevelopmentCardZones().get(index).copyLastCard()==null) ||
                (dashboard.getDevelopmentCardZones().get(index).copyLastCard().getCardStats().getValue0()==level-1)))       {
        developmentCard = gameBoard.getDeckOfChoice(color,level).drawCard();
        developmentCard.buyCard(dashboard);
        dashboard.buyCard(index,developmentCard);
        }
        else{
                throw new NotEnoughResourcesException();
        }
    }

    /**
     * Method to make the development card production, the developmentCardZone indicates the zone where to take the
     * card on the last level and activate its production
     */
    public void activateDevelopmentProduction(int developmentCardZone) throws NotEnoughResourcesToActivateProductionException, PapalCardActivatedException {
        if(dashboard.checkProductionPossible(developmentCardZone)){
             dashboard.activateDevProd(developmentCardZone);
        }
        else throw new NotEnoughResourcesToActivateProductionException();
    }

    /**
     * Method to activate the basic production (in the normal version, without any changes in the json file, it
     * requires 2 resources chosen by the player to produce another one, still chosen by the player)
     */
    public int activateBaseProduction(ArrayList<Resource> resourcesUsed, ArrayList<Resource> resourcesToProduce){
        try {
            dashboard.activateBaseProd(resourcesUsed,resourcesToProduce);
        } catch (NotEnoughResourcesToActivateProductionException e) {
            return 1;
        }catch (WrongAmountOfResourcesException e) {
            return 2;
        } catch (PapalCardActivatedException e) {
            return 3;
        }
        return 0;
    }

    /**
     * used when the player activates the production from a leader card. If it isn't possible, the problem is
     * communicated through various specific exceptions
     */
    public void checkLeaderProduction(int leaderCardIndex) throws WrongTypeOfLeaderPowerException, NotEnoughResourcesToActivateProductionException, LeaderCardNotActiveException {
         if(!dashboard.isLeaderActive(leaderCardIndex) ){
            throw new LeaderCardNotActiveException();
        }

        else if (!dashboard.leaderPowerTypeProd(leaderCardIndex)){
                throw new WrongTypeOfLeaderPowerException();
        }

        else if(!dashboard.checkLeaderProdPossible(leaderCardIndex)){
                throw new NotEnoughResourcesToActivateProductionException();
        }
    }

    public void buyDevelopmentCardFake(Color color, int level,int index) throws NotCoherentLevelException, NotEnoughResourcesException{
        DevelopmentCard developmentCard;
        if((level>1 && dashboard.getDevelopmentCardZones().get(index).copyLastCard()==null)
                ||(dashboard.getDevelopmentCardZones().get(index).copyLastCard()!=null && dashboard.getDevelopmentCardZones().get(index).copyLastCard  ().getCardStats().getValue0()==1 && level==3)||
                (dashboard.getDevelopmentCardZones().get(index).copyLastCard()!=null && dashboard.getDevelopmentCardZones().get(index).copyLastCard().getCardStats().getValue0()==level)||
                (level==1 && dashboard.getDevelopmentCardZones().get(index).copyLastCard()!=null)){
            throw new NotCoherentLevelException();
        }
        developmentCard = gameBoard.getDeckOfChoice(color,level).drawCard();
        dashboard.buyCard(index,developmentCard);
    }

    /**
     * Method used when a player ends his turn: moves the resources from the temporary list of produced resources to the strongbox
     */
    public void endTurn(){
        dashboard.moveResourcesProducedToStrongbox();
    }

    /**
     * Method called in the turn 0: player selects 2 cards to discard from the 4 given at the start of the game
     */
    public void discardLeaderCards(ArrayList<Integer> listOfIndexes){
        Collections.sort(listOfIndexes);
        Collections.reverse(listOfIndexes);
        for(Integer index: listOfIndexes) {
            dashboard.discardCard(index);
        }
    }

    public LeaderCardZone getLeaderCardZone(){
        return dashboard.getLeaderCardZone();
    }

    public Dashboard getDashboardCopy() {
        return dashboard;
    }

    public int nextPapalCardToActivateInfo(){
        return dashboard.nextPapalCardToActivateInfo();
    }

    public boolean noPapalCardActivated(){
        return dashboard.isAtLeastAPapalCardActivated();
    }

    public int numberOfActivatedPapalCards(){
        return dashboard.numberOfActivatedPapalCards();
    }

    public ArrayList<ArrayList<Resource>> activatedWhiteToColor(){
        return dashboard.getWhiteToColorResources();
    }

    public void addResourceInWarehouse(Resource resourceToAdd){
        dashboard.addResourceToWarehouse(resourceToAdd);
    }

    public void addResourceInStrongbox(Resource resourceToAdd){
        dashboard.addResourceToStrongbox(resourceToAdd);
    }

    public void leaderCardProduction(int index, ArrayList<Resource> resourcesWanted){
        dashboard.leaderProd(index, resourcesWanted);
    }

    public ResourceType typeOfExtraDepotGivenItsIndex(int index){
        if(dashboard.getExtraDepots()==null || dashboard.getExtraDepots().size()==0 || dashboard.getExtraDepots().get(index)==null) return null;
        return dashboard.getExtraDepots().get(index).getExtraDepotType();
    }

    public int resourcesContainedInAnExtraDepotGivenItsIndex(int index){
        return dashboard.getExtraDepots().get(index).getAllResources().size();
    }

    public int checkPositionOfGivenPapalCard(int cardActivated){
        return dashboard.getPapalPath().checkPosition(cardActivated);
    }

    public void moveForwardLorenzo() throws LorenzoWonTheMatch, LorenzoActivatesPapalCardException, BothPlayerAndLorenzoActivatePapalCardException {
        dashboard.moveForwardLorenzo();
    }

    public void moveForwardLorenzo(int amount) throws LorenzoWonTheMatch, LorenzoActivatesPapalCardException, BothPlayerAndLorenzoActivatePapalCardException {
        dashboard.moveForwardLorenzo(amount);
    }

    public int sizeOfWarehouse(){
        return dashboard.getWarehouse().sizeOfWarehouse();
    }

    public int numberOfExtraDepots(){
        return dashboard.getExtraDepots().size();
    }

    public int lengthOfDepotGivenItsIndex(int index){
        return dashboard.lengthOfDepot(index);
    }

    public ResourceType typeOfDepotGivenItsIndex(int index){
        return dashboard.getWarehouse().returnTypeofDepot(index);
    }


    public LeaderCard getLeaderCard(int index){
        if(getLeaderCardZone().getLeaderCards().size()>index && getLeaderCardZone().getLeaderCards().get(index)!=null) {
            return getLeaderCardZone().getLeaderCards().get(index);
        }
        else return null;
    }

    public void activateLeaderCard(int index) throws NotInactiveException, RequirementsUnfulfilledException {
        if (dashboard.leaderCardRequirementsFulfilled(index) && !dashboard.isLeaderActive(index)){
            dashboard.activateLeaderCard(index);
        } else if(!dashboard.isLeaderInactive(index)) {
            throw new NotInactiveException();
        }
        else if(!dashboard.getLeaderCardZone().getLeaderCards().get(index).checkRequirements(dashboard)) {
            throw new RequirementsUnfulfilledException();
        }
    }

    public void moveForwardFaith() throws PapalCardActivatedException {
        dashboard.moveForward();
    }

    public int resourcesToProduceInTheSpecifiedLeaderCard(int i){
        int quantity = dashboard.getLeaderCardZone().getLeaderCards().get(i).getLeaderPower().returnRelatedResourcesCopy().size();
        return quantity;
    }

    public boolean isLastCardOfTheSelectedDevZoneNull(int index){
        return dashboard.getDevelopmentCardZones().get(index-1).getLastCard()==null;
    }

    public void giveCard(LeaderCard card){
        dashboard.drawCard(card);
    }

    public void activateWhiteToColorCardWithSelectedIndex(int index) throws PapalCardActivatedException {
        dashboard.activateWhiteToColorCard(index);
    }

    public PowerType returnPowerTypeOfTheSelectedCard(int index){
        return getLeaderCardZone().getLeaderCards().get(index).getLeaderPower().returnPowerType();
    }

    public boolean checkGameIsEnded(){
       return dashboard.checkGameIsEnded();
    }

    public ArrayList <LeaderCard> getLeaderCardsCopy(){
        return this.getLeaderCardZone().getLeaderCardsCopy();
    }

    public void discardLeaderCard(int index){
        getLeaderCardZone().removeCard(index);
    }

    public void removeResource(int index) throws WarehouseDepotsRegularityError {
        dashboard.removeResourceFromWarehouse(index);
    }

    public void swapResources() throws WarehouseDepotsRegularityError {
        dashboard.swapResources();
    }

    public int removeExceedingDepot(int index) throws WarehouseDepotsRegularityError {
        return dashboard.removeExceedingDepot(index);
    }

    public int availableResourceOfType(ResourceType resourceType){
        ResourceDuplicator duplicator= new ResourceDuplicator();
        return dashboard.allAvailableResources(duplicator.copyResource(resourceType));
    }

    public int producedThisTurn(ResourceType resourceType){
        return dashboard.producedThisTurn(resourceType);
    }

    public int getFaithPosition() {
        return dashboard.getFaith();
    }

    public boolean isALeaderProdCard(int index){
        if(getLeaderCard(index).isAnExtraProd()) {
            return true;
        }else return false;

    }

    public Strongbox getStrongbox(){
        return dashboard.getStrongbox();
    }

    public ArrayList <Resource>  getProducedResources(){
        return dashboard.getResourcesProduced();
    }

    public PapalPath getPapalPath() {
        return dashboard.getPapalPath();
    }

    public Warehouse getWarehouse() {
        return dashboard.getWarehouse();
    }

    public List <DevelopmentCard> getDevelopmentCardsInADevCardZone(int index){
        return dashboard.getDevelopmentCardsInAdevCardZone(index);
    }

    public int indexOfALeaderCard(LeaderCard leaderCard){
        for(int i=0;i<getLeaderCardsCopy().size();i++){
            if(leaderCard.equals(getLeaderCardsCopy().get(i))){
                return i;
            }
        }
        return -1;
    }

    public int[] availableResourcesForDevelopment(){
        int[] resources= new int[4];
        resources[0]= dashboard.availableResourcesForDevelopment(new CoinResource());
        resources[1]= dashboard.availableResourcesForDevelopment(new StoneResource());
        resources[2]= dashboard.availableResourcesForDevelopment(new ServantResource());
        resources[3]= dashboard.availableResourcesForDevelopment(new ShieldResource());
        return resources;
    }
}