package it.polimi.ingsw.model.boardsAndPlayer;

import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.model.developmentcard.Color;
import it.polimi.ingsw.model.developmentcard.DevelopmentCard;
import it.polimi.ingsw.model.developmentcard.DevelopmentCardZone;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.leadercard.LeaderCardZone;
import it.polimi.ingsw.model.market.OutOfBoundException;
import it.polimi.ingsw.model.papalpath.CardCondition;
import it.polimi.ingsw.model.resource.*;
import it.polimi.ingsw.exception.warehouseErrors.WarehouseDepotsRegularityError;

import java.util.ArrayList;
import java.util.Collections;

public class Player {
    private String nickname;
    private int order;  //from 0 to 3
    private int victoryPoints;
    private Dashboard dashboard;

    public Player(String nickname, int order) {
        this.nickname=nickname;
        this.order=order;
        this.dashboard= new Dashboard(order);
    }

    //case single-player game
    public Player(String nickname) {
        this.nickname = nickname;
        this.order=1;
        this.dashboard= new Dashboard(order);
    }

    public void setOrder(int order) {
        this.order = order;
            this.dashboard= new Dashboard(order);
    }


    public String getNickname() {
        return nickname;
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

    public void checkTable(GameBoard gameBoard){
        //this should be implemented in the GUI I think, here is a rough first try
        gameBoard.getMarket().printMarket();
    }

    /**
     *Method used to get the resources from the selected row/column of the market and put them in the warehouse
     */
    public void acquireResourcesFromMarket(GameBoard gameBoard, boolean isRow, int index) throws OutOfBoundException, WarehouseDepotsRegularityError, PapalCardActivatedException {
        /*if(dashboard.getWhiteToColorResources()!=null && dashboard.getWhiteToColorResources().size()==1){
            int numOfBlanks= gameBoard.getMarket().checkNumOfBlank(isRow,index);
            gameBoard.getMarket().getResourcesFromMarket(isRow,index,dashboard);
            for(int i=0; i<numOfBlanks;i++){
                dashboard.getWhiteToColorResources().get(0).effectFromMarket(dashboard);
            }
        }
        else    gameBoard.getMarket().getResourcesFromMarket(isRow,index,dashboard);*/

        gameBoard.acquireResourcesFromMarket(isRow,index,dashboard);
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

    public void buyDevelopmentCardFake(Color color, int level,int index,GameBoard gameBoard) throws NotCoherentLevelException, NotEnoughResourcesException{
        DevelopmentCard developmentCard;
        if((level>1 && dashboard.getDevelopmentCardZones().get(index).copyLastCard()==null)
                ||(dashboard.getDevelopmentCardZones().get(index).copyLastCard()!=null && dashboard.getDevelopmentCardZones().get(index).copyLastCard  ().getCardStats().getValue0()==1 && level==3)||
                (dashboard.getDevelopmentCardZones().get(index).copyLastCard()!=null && dashboard.getDevelopmentCardZones().get(index).copyLastCard().getCardStats().getValue0()==level)||
                (level==1 && dashboard.getDevelopmentCardZones().get(index).copyLastCard()!=null)){
            throw new NotCoherentLevelException();
        }
        developmentCard = gameBoard.getDeckOfChoice(color,level).drawCard();
        developmentCard.buyCard(dashboard);
        dashboard.buyCard(index,developmentCard);
    }

    /**
     * Method used when a player has achieved one of the goals to end the game. It notifies the controller and all
     * the other player next to this player will make their last turn.
     * STILL HAS TO BE MADE
     */
    public void endGame(){
        //call GameHandler endGame()
        System.out.println("End game");
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
            return 0;
        } catch (NotEnoughResourcesToActivateProductionException e) {
            return 1;
        }catch (WrongAmountOfResourcesException e) {
            return 2;
        } catch (PapalCardActivatedException e) {
            e.printStackTrace();
        }
        return -1;
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
        else{
            System.out.println("Error not handled in player.checkActivateLeaderCard");;
        }
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

    public Dashboard getDashboard() {
        return dashboard;
    }

    public LeaderCard getLeaderCard(int index){
        if(getLeaderCardZone().getLeaderCards().get(index)!=null)        return getLeaderCardZone().getLeaderCards().get(index);
        else return null;
    }

    public void activateLeaderCard(int index) throws NotInactiveException, RequirementsUnfulfilledException {
        if (dashboard.leaderCardRequirementsFulfilled(index) && dashboard.isLeaderActive(index))        dashboard.activateLeaderCard(index);
        else if(!dashboard.isLeaderInactive(index))   throw new NotInactiveException();
        else if(!dashboard.getLeaderCardZone().getLeaderCards().get(index).checkRequirements(dashboard)) throw new RequirementsUnfulfilledException();
    }

    public void moveFowardFaith() throws PapalCardActivatedException {
        dashboard.moveForward();
    }
}
