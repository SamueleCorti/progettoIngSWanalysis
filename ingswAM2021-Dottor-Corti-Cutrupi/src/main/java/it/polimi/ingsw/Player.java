package it.polimi.ingsw;

import it.polimi.ingsw.Exceptions.NotEnoughResourcesException;
import it.polimi.ingsw.developmentcard.Color;
import it.polimi.ingsw.developmentcard.DevelopmentCard;
import it.polimi.ingsw.developmentcard.DevelopmentCardZone;
import it.polimi.ingsw.leadercard.LeaderCard;
import it.polimi.ingsw.leadercard.LeaderCardZone;
import it.polimi.ingsw.market.OutOfBoundException;
import it.polimi.ingsw.papalpath.CardCondition;
import it.polimi.ingsw.resource.*;
import it.polimi.ingsw.storing.RegularityError;

import java.util.Scanner;

public class Player {

    //I added dashboard and gameBoard, both absent in the UML, because they're needed to enter the market and all the components the dashboard has
    private String nickname;
    private int order;
    private int victoryPoints;
    private Dashboard dashboard;
    private GameBoard gameBoard;

    public Player(String nickname, int order, GameBoard gameBoard){
        this.nickname=nickname;
        this.order=order;
        this.dashboard= new Dashboard(order);
        this.gameBoard=gameBoard;
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

    public void checkTable(){
        //this should be implemented in the GUI I think, here is a rough first try
        gameBoard.getMarket().printMarket();
    }

    //the player chooses what line to get from the market
    public void getResourceFromMarket() throws OutOfBoundException, RegularityError {
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

    public void buyDevelopmentCard(Color color, int level,DevelopmentCardZone developmentCardZone) throws NotEnoughResourcesException {
        DevelopmentCard developmentCard;
        if ((gameBoard.getDeckOfChoice(color,level).getFirstCard().checkPrice(dashboard)) && ((level==1 && developmentCardZone.getLastCard()==null) ||
                (developmentCardZone.getLastCard().getCardStats().getValue0()==level-1)))       {
            developmentCard = gameBoard.getDeckOfChoice(color,level).drawCard();
            developmentCardZone.addNewCard(developmentCard);
        }
            try {
                if(!gameBoard.getDeckOfChoice(color,level).getFirstCard().checkPrice(dashboard) || (level>1 && developmentCardZone.getLastCard()==null) ||
                        (developmentCardZone.getLastCard().getCardStats().getValue0()!=level-1)){
                    throw new NotEnoughResourcesException();
                }
            }catch (NotEnoughResourcesException e1){
                System.out.println(e1.toString());
            }
        if (dashboard.numberOfDevCards()>=7) endGame();
    }

    public void endGame(){
        //call GameHandler endGame()
    }

    public void activateProduction(){
        //TODO
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
        //gamehandler should give the nex player the turn
        //if the player is against Lorenzo, check if a color of dev cards is completely empty
    }

    //used when the player draws 4 leader cards, but can only keep 2 of them
    public void discard2LeaderCards(){
        System.out.println("What is the first card you'd like to discard? The first in number 0, the second 1, and so on ");
        Scanner in = new Scanner(System.in);
        int index1= in.nextInt();
        if(index1>3 || index1<0) System.out.println("You must chhose a different number, insert again ");
        System.out.println("What is the second card you'd like to discard? The first in number 0, the second 1, and so on ");
        int index2= in.nextInt();
        if(index1==index2 || index2>3 || index2<0) System.out.println("You must chhose a different number, insert again ");
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

    public GameBoard getGameBoard() {
        return gameBoard;
    }
}
