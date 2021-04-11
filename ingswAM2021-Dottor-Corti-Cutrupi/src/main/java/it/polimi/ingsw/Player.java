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
        for(DevelopmentCardZone developmentCardZone : dashboard.getDevelopmentCardZones()){
            for (DevelopmentCard developmentCard:developmentCardZone.getCards()) {
                victoryPoints+=developmentCard.getVictoryPoints();
            }
        }
        victoryPoints+=dashboard.getPapalPath().getVictoryPoints();
        for (LeaderCard leaderCard : dashboard.getLeaderCardZone().getLeaderCards()) {
            if(leaderCard.getCondition().equals(CardCondition.Active)){
                victoryPoints+=leaderCard.getVictoryPoints();
            }
        }
        victoryPoints += ((dashboard.availableResourcesForProduction(new CoinResource())+dashboard.availableResourcesForProduction(new ServantResource())+
                dashboard.availableResourcesForProduction(new StoneResource())+dashboard.availableResourcesForProduction(new ShieldResource()))/5);
        return victoryPoints;
    }

    public void checkTable(GameBoard gameBoard){
        //this should be implemented in the GUI I think, here is a rough first try
        gameBoard.getMarket().printMarket();
    }

    //the player chooses what line to get from the market
    public void getResourceFromMarket(GameBoard gameBoard) throws OutOfBoundException, RegularityError {
        Scanner in = new Scanner(System.in);
        System.out.println("Write r to get a row, c for a column");
        String rowColumn = in.next();
        boolean isRow;
        if (rowColumn=="r") isRow=true;
        else isRow=false;
        System.out.println("Write the index of the row/column you wish to get");
        int index= in.nextInt();
        gameBoard.getMarket().getResourcesFromMarket(isRow,index,dashboard);
    }

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

    public void activateStandardProduction(List<Resource> resourcesUsed, Resource resourceToProduce){
        try {
            dashboard.activateStandardProd(resourcesUsed,resourceToProduce);
        } catch (NotEnoughResourcesToActivateProductionException regularityError) {
            regularityError.printStackTrace();
        }
    }

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
        //must call warehouse.swapResources
        //should add the productions to the strongbox
        //gameHandler should give the next player the turn
        //if the player is against Lorenzo, check if a color of dev cards is completely empty
        try {
            dashboard.getWarehouse().swapResources();
        } catch (RegularityError regularityError) {
            regularityError.printStackTrace();
        }
        dashboard.moveResourcesProducedToStrongbox();
        //notifies the gameHandler to change the player

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

    //I don't know what we wish to return tbh, so I return the 2 leader cards the player has
    public LeaderCardZone getLeaderCards(){
        return dashboard.getLeaderCardZone();
    }

    public Dashboard getDashboard() {
        return dashboard;
    }
}
