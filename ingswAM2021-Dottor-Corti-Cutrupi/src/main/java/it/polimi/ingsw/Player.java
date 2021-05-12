package it.polimi.ingsw;

import it.polimi.ingsw.Communication.client.messages.actions.mainActions.productionActions.LeaderProductionAction;
import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.developmentcard.Color;
import it.polimi.ingsw.developmentcard.DevelopmentCard;
import it.polimi.ingsw.developmentcard.DevelopmentCardZone;
import it.polimi.ingsw.leadercard.LeaderCard;
import it.polimi.ingsw.leadercard.LeaderCardZone;
import it.polimi.ingsw.leadercard.leaderpowers.PowerType;
import it.polimi.ingsw.market.OutOfBoundException;
import it.polimi.ingsw.papalpath.CardCondition;
import it.polimi.ingsw.resource.*;
import it.polimi.ingsw.storing.RegularityError;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private String nickname;
    private int order;
    private int victoryPoints;
    private Dashboard dashboard;

    public Player(String nickname, int order) throws FileNotFoundException {
        this.nickname=nickname;
        this.order=order;
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
     * used when there are 2 whiteToColor leader cards active. If this gets called but the condition isn't true, this method proceeds to call the other
     * getResourceFromMarket
     */
    public void getResourcesFromMarket(GameBoard gameBoard, boolean isRow, int index, ArrayList<Resource> array) throws OutOfBoundException, RegularityError, NotCoherentResourceInArrayWhiteToColorException {
        if(dashboard.getWhiteToColorResources()==null || dashboard.getWhiteToColorResources().size()<2){
            getResourcesFromMarket(gameBoard,isRow,index);
        }
        else{
            int numOfBlanks= gameBoard.getMarket().checkNumOfBlank(isRow,index);
            if(numOfBlanks!=array.size())   throw new NotCoherentResourceInArrayWhiteToColorException();
            else {
                gameBoard.getMarket().getResourcesFromMarket(isRow,index,dashboard);
                for(int i=0; i<numOfBlanks;i++)
                    array.get(i).effectFromMarket(dashboard);
            }
        }
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
            try {
                throw new NotCoherentLevelException();
            } catch (NotCoherentLevelException e) {
                System.out.println(e.toString());
            }
        }
        else if ((gameBoard.getDeckOfChoice(color,level).getFirstCard().checkPrice(dashboard)) && ((level==1 && dashboard.getDevelopmentCardZones().get(index).getLastCard()==null) ||
                (dashboard.getDevelopmentCardZones().get(index).getLastCard().getCardStats().getValue0()==level-1)))       {
            developmentCard = gameBoard.getDeckOfChoice(color,level).drawCard();
            developmentCard.buyCard(dashboard);
            dashboard.getDevelopmentCardZones().get(index).addNewCard(developmentCard);
        }
        else{
            try {
                throw new NotEnoughResourcesException();
            } catch (NotEnoughResourcesException e) {
                System.out.println(e.toString());
            }
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
    public void activateDevelopmentProduction(int developmentCardZone) throws RegularityError, NotEnoughResourcesToActivateProductionException {
        if(dashboard.checkProductionPossible(dashboard.getDevelopmentCardZones().get(developmentCardZone))){
            dashboard.activateProd(dashboard.getDevelopmentCardZones().get(developmentCardZone));
        }
        else{
            try {
                throw new NotEnoughResourcesToActivateProductionException();
            } catch (NotEnoughResourcesToActivateProductionException e) {
                System.out.println(e.toString());
            }
        }
    }

    /**
     * Method to activate the basic production (in the normal version, without any changes in the json file, it
     * requires 2 resources chosen by the player to produce another one, still chosen by the player)
     */
    public boolean activateStandardProduction(List<Resource> resourcesUsed,List<Resource> resourcesToProduce){
        try {
            dashboard.activateStandardProd(resourcesUsed,resourcesToProduce);
            return true;
        } catch (NotEnoughResourcesToActivateProductionException regularityError) {
            regularityError.printStackTrace();
        }
        return false;
    }

    /**
     * used when the player activates the production from a leader card. If it isn't possible, the problem is
     * communicated through various specific exceptions
     */
    public boolean activateLeaderProduction(int leaderCardIndex, LeaderProductionAction action) throws ActivatingLeaderCardsUsingWrongIndexException, WrongTypeOfLeaderPowerException, NotEnoughResourcesToActivateProductionException, LeaderCardNotActiveException {
        if((dashboard.getLeaderCardZone().getLeaderCards().get(leaderCardIndex).getLeaderPower().equals(PowerType.ExtraProd)) &&
                dashboard.checkLeaderProdPossible(dashboard.getLeaderCardZone().getLeaderCards().get(leaderCardIndex).getLeaderPower().returnRelatedResource()) &&
                dashboard.getLeaderCardZone().getLeaderCards().get(leaderCardIndex).getCondition().equals(CardCondition.Active)){
            dashboard.getLeaderCardZone().getLeaderCards().get(leaderCardIndex).activateCardPower(dashboard);
            return true;
        }
        else if (!dashboard.getLeaderCardZone().getLeaderCards().get(leaderCardIndex).getLeaderPower().equals(PowerType.ExtraProd)){
                throw new WrongTypeOfLeaderPowerException();
        }
        else if(!dashboard.checkLeaderProdPossible(dashboard.getLeaderCardZone().getLeaderCards().get(leaderCardIndex).getLeaderPower().returnRelatedResource())){
                throw new NotEnoughResourcesToActivateProductionException();
        }
        else{
                throw new LeaderCardNotActiveException();
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

    public LeaderCardZone getLeaderCards(){
        return dashboard.getLeaderCardZone();
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    public void activateLeaderCard(int index) throws NotInactiveException, RequirementsUnfulfilledException {
        if (dashboard.getLeaderCardZone().getLeaderCards().get(index).checkRequirements(dashboard) && dashboard.getLeaderCardZone().getLeaderCards().get(index).getCondition()==CardCondition.Inactive) {
            dashboard.getLeaderCardZone().getLeaderCards().get(index).setCondition(CardCondition.Active,dashboard);
        }
        else if(dashboard.getLeaderCardZone().getLeaderCards().get(index).getCondition()!=CardCondition.Inactive)   throw new NotInactiveException();
        else if(!dashboard.getLeaderCardZone().getLeaderCards().get(index).checkRequirements(dashboard)) throw new RequirementsUnfulfilledException();
    }
}
