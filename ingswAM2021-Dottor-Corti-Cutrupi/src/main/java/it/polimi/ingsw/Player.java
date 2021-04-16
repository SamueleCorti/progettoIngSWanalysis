package it.polimi.ingsw;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player {

    //I added dashboard and gameBoard, both absent in the UML, because they're needed to enter the market and all the components the dashboard has
    private String nickname;
    private int order;
    private int victoryPoints;
    private Dashboard dashboard;

    public Player(String nickname, int order){
        this.nickname=nickname;
        this.order=order;
        this.dashboard= new Dashboard(order);
    }

    public int getOrder() {
        return order;
    }


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

    //the player chooses what line to get from the market
    public void getResourceFromMarket(GameBoard gameBoard, boolean isRow, int index) throws OutOfBoundException, RegularityError {
        boolean errorFound = false;
        try{
            if(dashboard.getWhiteToColorResources().size()==2 && gameBoard.getMarket().checkNumOfBlank(isRow,index,dashboard)>0) {
                errorFound = true;
                throw new TwoWhiteToColorException();
            }
        }catch (TwoWhiteToColorException e1) {
            System.out.println(e1.toString());
        }
        if(errorFound==false) gameBoard.getMarket().getResourcesFromMarket(isRow,index,dashboard);
    }

    public void getResourcesFromMarketWhen2WhiteToColor(GameBoard gameBoard, boolean isRow, int index,ArrayList<Resource> array) throws RegularityError, OutOfBoundException {
        boolean errorFound = false;
        for (Resource resource:array) {
            try{
                if(dashboard.getWhiteToColorResources().size()==2 && gameBoard.getMarket().checkNumOfBlank(isRow,index,dashboard)>0) {
                    errorFound = true;
                    throw new NotCoherentResourceInArrayWhiteToColorException();
                }
            }catch (NotCoherentResourceInArrayWhiteToColorException e1) {
                System.out.println(e1.toString());
            }
        }
        if(errorFound == true) return;
        gameBoard.getMarket().getResourcesFromMarket(isRow,index,dashboard);
        for (Resource resource:array) {
            resource.effectFromMarket(dashboard);
        }
    }

    //the parameters indicate what card to buy and where to place it: we give the card's color and level to locate it in the gameboard (whom we pass as a parameter
    //it self), and the developmentCardZone where the players wish to place the card
    public void buyDevelopmentCard(Color color, int level,DevelopmentCardZone developmentCardZone,GameBoard gameBoard) throws NotCoherentLevelException, NotEnoughResourcesException, RegularityError, NotEnoughResourcesToActivateProductionException {
        DevelopmentCard developmentCard;
        if((level>1 && developmentCardZone.getLastCard()==null)
                ||(developmentCardZone.getLastCard()!=null && developmentCardZone.getLastCard().getCardStats().getValue0()==1 && level==3)||
                (developmentCardZone.getLastCard()!=null && developmentCardZone.getLastCard().getCardStats().getValue0()==level)){
            try {
                throw new NotCoherentLevelException();
            } catch (NotCoherentLevelException e) {
                System.out.println(e.toString());
            }
        }
        else if ((gameBoard.getDeckOfChoice(color,level).getFirstCard().checkPrice(dashboard)) && ((level==1 && developmentCardZone.getLastCard()==null) ||
                (developmentCardZone.getLastCard().getCardStats().getValue0()==level-1)))       {
            developmentCard = gameBoard.getDeckOfChoice(color,level).drawCard();
            developmentCard.buyCard(dashboard);
            developmentCardZone.addNewCard(developmentCard);
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

    public void endGame(){
        //call GameHandler endGame()
        System.out.println("End game");
    }

    //used when the player activates the production from a development card. If it isn't possible, an exception regarding the absence of resources is thrown
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

    //basic production, 2 resources for one
    public void activateStandardProduction(List<Resource> resourcesUsed, Resource resourceToProduce){
        try {
            dashboard.activateStandardProd(resourcesUsed,resourceToProduce);
        } catch (NotEnoughResourcesToActivateProductionException regularityError) {
            regularityError.printStackTrace();
        }
    }

    //used when the player activates the production from a leader card. If it isn't possible, the problem is communicated through various specific exceptions
    public void activateLeaderProduction(int leaderCardIndex) throws ActivatingLeaderCardsUsingWrongIndexException {
        if((dashboard.getLeaderCardZone().getLeaderCards().get(leaderCardIndex).getLeaderPower().equals(PowerType.ExtraProd)) &&
                dashboard.checkLeaderProdPossible(dashboard.getLeaderCardZone().getLeaderCards().get(leaderCardIndex).getLeaderPower().returnRelatedResource()) &&
                dashboard.getLeaderCardZone().getLeaderCards().get(leaderCardIndex).getCondition().equals(CardCondition.Active)){
            dashboard.getLeaderCardZone().getLeaderCards().get(leaderCardIndex).activateCardPower(dashboard);
        }
        else if (!dashboard.getLeaderCardZone().getLeaderCards().get(leaderCardIndex).getLeaderPower().equals(PowerType.ExtraProd)){
            try {
                throw new WrongTypeOfLeaderPowerException();
            } catch (WrongTypeOfLeaderPowerException e) {
                System.out.println(e.toString());
            }
        }
        else if(!dashboard.checkLeaderProdPossible(dashboard.getLeaderCardZone().getLeaderCards().get(leaderCardIndex).getLeaderPower().returnRelatedResource())){
            try {
                throw new NotEnoughResourcesToActivateProductionException();
            } catch (NotEnoughResourcesToActivateProductionException e) {
                System.out.println(e.toString());
            }
        }
        else{
            try {
                throw new LeaderCardNotActiveException();
            } catch (LeaderCardNotActiveException e) {
                System.out.println(e.toString());
            }
        }
    }

    //if the player has 2 active whiteToColor leader cards, every time he gets a blank resource from the market this method gets called
    public int chooseWhiteToColor(){
        System.out.println("Which white to color leader effect would you like to activate for the next blank resource? ");
        Scanner in = new Scanner(System.in);
        int index= in.nextInt();
        return index;
    }

    public void endTurn(){
        //gameHandler should give the next player the turn
        //if the player is against Lorenzo, check if a color of dev cards is completely empty
        dashboard.moveResourcesProducedToStrongbox();
        //gameHandler should give the next player the active role
    }

    //used when the player draws 4 leader cards, but can only keep 2 of them
    public void discard2LeaderCards(){
        int index1;
        do{
            System.out.println("What is the first card you'd like to discard? The first in number 0, the second 1, and so on ");
            Scanner in = new Scanner(System.in);
            index1= in.nextInt();
            if(index1>3 || index1<0){
                try {
                    throw new GenericWrongIndexException();
                } catch (GenericWrongIndexException e) {
                    System.out.println(e.toString());
                }
            }
        }while (index1>3 || index1<0);
        int index2;
        do{
            System.out.println("What is the second card you'd like to discard? The first in number 0, the second 1, and so on ");
            Scanner in = new Scanner(System.in);
            index2= in.nextInt();
            if(index2>3 || index2<0) {
                try {
                    throw new GenericWrongIndexException();
                } catch (GenericWrongIndexException e) {
                    System.out.println(e.toString());
                }
            }
        }while (index2>3 || index2<0);
        int firstTodiscard;
        //doing this makes the method remove the higher index first, so we don't risk the shift in index tha would happen if we were to remove the smaller
        //one first
        if (index1>index2)  {
            firstTodiscard=index1;
            index1=index2;
        }
        else                firstTodiscard=index2;
        dashboard.getLeaderCardZone().getLeaderCards().remove(firstTodiscard);
        dashboard.getLeaderCardZone().getLeaderCards().remove(index1);
    }

    //this method returns the 2 leader cards the player has
    public LeaderCardZone getLeaderCards(){
        return dashboard.getLeaderCardZone();
    }

    public Dashboard getDashboard() {
        return dashboard;
    }
}
