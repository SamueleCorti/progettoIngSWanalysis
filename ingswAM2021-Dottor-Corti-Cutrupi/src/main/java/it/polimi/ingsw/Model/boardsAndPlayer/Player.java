package it.polimi.ingsw.Model.boardsAndPlayer;

import it.polimi.ingsw.Communication.client.actions.TestAction;
import it.polimi.ingsw.Communication.client.actions.mainActions.productionActions.LeaderProductionAction;
import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Model.developmentcard.Color;
import it.polimi.ingsw.Model.developmentcard.DevelopmentCard;
import it.polimi.ingsw.Model.developmentcard.DevelopmentCardZone;
import it.polimi.ingsw.Model.leadercard.LeaderCard;
import it.polimi.ingsw.Model.leadercard.LeaderCardZone;
import it.polimi.ingsw.Model.leadercard.leaderpowers.PowerType;
import it.polimi.ingsw.Model.market.OutOfBoundException;
import it.polimi.ingsw.Model.papalpath.CardCondition;
import it.polimi.ingsw.Model.resource.*;
import it.polimi.ingsw.Model.storing.RegularityError;

import java.util.ArrayList;

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
    public void getResourcesFromMarket(GameBoard gameBoard, boolean isRow, int index) throws OutOfBoundException, RegularityError {
        if(dashboard.getWhiteToColorResources()!=null && dashboard.getWhiteToColorResources().size()==1){
            int numOfBlanks= gameBoard.getMarket().checkNumOfBlank(isRow,index);
            gameBoard.getMarket().getResourcesFromMarket(isRow,index,dashboard);
            for(int i=0; i<numOfBlanks;i++){
                dashboard.getWhiteToColorResources().get(0).effectFromMarket(dashboard);
            }
        }
        else    gameBoard.getMarket().getResourcesFromMarket(isRow,index,dashboard);
    }



    /**
     * Method to buy a card from the gameBoard. The parameters color and level indicate the card to buy and
     * developmentCardZone indicates the zone to put the card in
     */
    public void buyDevelopmentCard(Color color, int level,int index,GameBoard gameBoard) throws NotCoherentLevelException, NotEnoughResourcesException, RegularityError, NotEnoughResourcesToActivateProductionException {
        DevelopmentCard developmentCard;
        if((level>1 && dashboard.getDevelopmentCardZones().get(index).getLastCard()==null)
                ||(dashboard.getDevelopmentCardZones().get(index).getLastCard()!=null && dashboard.getDevelopmentCardZones().get(index).getLastCard().getCardStats().getValue0()==1 && level==3)||
                (dashboard.getDevelopmentCardZones().get(index).getLastCard()!=null && dashboard.getDevelopmentCardZones().get(index).getLastCard().getCardStats().getValue0()==level)){
                throw new NotCoherentLevelException();
        }
        else if ((gameBoard.getDeckOfChoice(color,level).getFirstCard().checkPrice(dashboard)) && ((level==1 && dashboard.getDevelopmentCardZones().get(index).getLastCard()==null) ||
                (dashboard.getDevelopmentCardZones().get(index).getLastCard().getCardStats().getValue0()==level-1)))       {
        developmentCard = gameBoard.getDeckOfChoice(color,level).drawCard();
        developmentCard.buyCard(dashboard);
        dashboard.getDevelopmentCardZones().get(index).addNewCard(developmentCard);
        }
        else{
                throw new NotEnoughResourcesException();
        }
        if (dashboard.numberOfDevCards()>=7) endGame();
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
    public void activateDevelopmentProduction(int developmentCardZone) throws NotEnoughResourcesToActivateProductionException {
        if(dashboard.checkProductionPossible(dashboard.getDevelopmentCardZones().get(developmentCardZone))){
            dashboard.activateProd(dashboard.getDevelopmentCardZones().get(developmentCardZone));
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
        }
    }

    /**
     * used when the player activates the production from a leader card. If it isn't possible, the problem is
     * communicated through various specific exceptions
     */
    public void checkActivateLeaderProduction(int leaderCardIndex) throws WrongTypeOfLeaderPowerException, NotEnoughResourcesToActivateProductionException, LeaderCardNotActiveException {
        if((dashboard.getLeaderCardZone().getLeaderCards().get(leaderCardIndex).getLeaderPower().returnPowerType().equals(PowerType.ExtraProd)) &&
                dashboard.checkLeaderProdPossible(leaderCardIndex) &&
                dashboard.getLeaderCardZone().getLeaderCards().get(leaderCardIndex).getCondition().equals(CardCondition.Active)){

            dashboard.getLeaderCardZone().getLeaderCards().get(leaderCardIndex).activateCardPower(dashboard);
        }

        else if(!dashboard.getLeaderCardZone().getLeaderCards().get(leaderCardIndex).getCondition().equals(CardCondition.Active)){
            throw new LeaderCardNotActiveException();
        }


        else if (!dashboard.getLeaderCardZone().getLeaderCards().get(leaderCardIndex).getLeaderPower().returnPowerType().equals(PowerType.ExtraProd)){
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
     * Method used when a player ends his turn: moves the resources from the temporary list of produced resources
     * to the strongbox and gameHandler gives the turn to the next player.
     * STILL HAS TO BE MADE
     */
    public void endTurn(){
        //gameHandler should give the next player the turn
        dashboard.moveResourcesProducedToStrongbox();
        //gameHandler should give the next player the active role
    }

    /**
     * Method called in the turn 0: player selects 2 cards to discard from the 4 given at the start of the game
     */
    public void discard2LeaderCards(int discard1, int discard2){
        //doing this makes the method remove the higher index first, so we don't risk the shift in index tha would happen if we were to remove the smaller
        //one first
        if (discard1>discard2)  {
            dashboard.getLeaderCardZone().getLeaderCards().remove(discard1);
            dashboard.getLeaderCardZone().getLeaderCards().remove(discard2);
        }
        else {
            dashboard.getLeaderCardZone().getLeaderCards().remove(discard2);
            dashboard.getLeaderCardZone().getLeaderCards().remove(discard1);
        }
    }

    /**
     * The player chooses to discard one of his leader cards to move one forward on the faith track
     * @param index: represents the card to discard
     */
    public void discardLeaderCardToGainFaith(int index){
        dashboard.getLeaderCardZone().getLeaderCards().get(index).setCondition(CardCondition.Discarded,dashboard);
        dashboard.getPapalPath().moveForward();
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
        if (dashboard.getLeaderCardZone().getLeaderCards().get(index).checkRequirements(dashboard) && dashboard.getLeaderCardZone().getLeaderCards().get(index).getCondition()==CardCondition.Inactive) {
            dashboard.getLeaderCardZone().getLeaderCards().get(index).setCondition(CardCondition.Active,dashboard);
        }
        else if(dashboard.getLeaderCardZone().getLeaderCards().get(index).getCondition()!=CardCondition.Inactive)   throw new NotInactiveException();
        else if(!dashboard.getLeaderCardZone().getLeaderCards().get(index).checkRequirements(dashboard)) throw new RequirementsUnfulfilledException();
    }

    public void testingMethod(){
        getLeaderCard(0).setCondition(CardCondition.Active, dashboard);
        getLeaderCard(1).setCondition(CardCondition.Active, dashboard);
    }
}
