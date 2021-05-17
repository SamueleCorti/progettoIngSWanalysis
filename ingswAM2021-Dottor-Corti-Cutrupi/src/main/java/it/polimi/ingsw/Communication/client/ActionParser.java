package it.polimi.ingsw.Communication.client;

import it.polimi.ingsw.Communication.client.actions.*;
import it.polimi.ingsw.Communication.client.actions.mainActions.DevelopmentAction;
import it.polimi.ingsw.Communication.client.actions.mainActions.EndTurn;
import it.polimi.ingsw.Communication.client.actions.mainActions.MarketAction;
import it.polimi.ingsw.Communication.client.actions.mainActions.productionActions.BaseProductionAction;
import it.polimi.ingsw.Communication.client.actions.mainActions.productionActions.DevelopmentProductionAction;
import it.polimi.ingsw.Communication.client.actions.mainActions.productionActions.LeaderProductionAction;
import it.polimi.ingsw.Communication.client.actions.secondaryActions.ActivateLeaderCardAction;
import it.polimi.ingsw.Communication.client.actions.secondaryActions.DiscardLeaderCard;
import it.polimi.ingsw.Communication.client.actions.secondaryActions.ViewDashboardAction;
import it.polimi.ingsw.Communication.client.actions.secondaryActions.ViewGameboardAction;
import it.polimi.ingsw.Model.developmentcard.Color;
import it.polimi.ingsw.Model.resource.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Class whose purpose is to create {@link Action} messages from stdIn.
 */
public class ActionParser {

    /**
     * Constructor, called everytime the player writes something in stdIn.
     * @param input: string created by the stdIn that is going to get parsed
     * @return: the correctly parsed {@link Action}, created from the string
     */
    public Action parseInput(@NotNull String input){
        List<String> in = new ArrayList<>(Arrays.asList(input.split(" ")));
        String command = in.get(0);
        Action actionToSend;
        switch(command.toLowerCase()){

            case"endturn": {actionToSend = new EndTurn(); break;}

            case "setupdiscard": {
                try {
                    int index1 = Integer.parseInt(in.get(1));
                    int index2 = Integer.parseInt(in.get(2));
                    if (index1 >= 0 && index1 < 4 && index2 >= 0 && index2 < 4 && index1!=index2) {
                        actionToSend = new DiscardTwoLeaderCardsAction(index1, index2);
                    }
                    else if(index1==index2){
                        actionToSend=null;
                        System.out.println("You can't insert the same index for both the cards");
                    }
                    else {
                        actionToSend = null;
                        System.out.println("Wrong indexes in setupdiscard action");
                    }
                }catch (IndexOutOfBoundsException ignored){
                    actionToSend = null;
                    System.out.println("You must insert 2 indexes after setupdiscard action call");
                }
                break;
            }

            case "infiniteresources":{
                actionToSend = new InfiniteResourcesAction();
                break;
            }

            case "viewgameboard": {
                actionToSend = new ViewGameboardAction();
                break;
            }

            case "activateleadercard":{
                actionToSend = new ActivateLeaderCardAction(Integer.parseInt(in.get(1)));
                break;
            }

            case "viewdashboard":{
                actionToSend = new ViewDashboardAction(Integer.parseInt(in.get(1)));
                break;
            }

            case "buydevelopmentcard":{
                try {
                    Color color = colorParser(in.get(1));
                    int level = Integer.parseInt(in.get(2));
                    int indexOfDevZone = Integer.parseInt(in.get(3));
                    if(color!=null && level>0 && level<4 && indexOfDevZone>0 && indexOfDevZone<4) {
                        actionToSend = new DevelopmentAction(color, level, indexOfDevZone);
                    }
                    else if(color==null){
                        actionToSend=null;
                        System.out.println("You must insert a valid color [blue, yellow, green, purple]");
                    }
                    else if(level<1 || level>3){
                        actionToSend=null;
                        System.out.println("Level must be between 1 and 3");
                    }
                    else {
                        actionToSend=null;
                        System.out.println("Index of dev zone must be between 1 and 3");
                    }
                }catch (IndexOutOfBoundsException e){
                    actionToSend=null;
                    System.out.println("You must select a color, an index for the card level and an index for the devZone you want " +
                            "to insert the card");
                }catch (NumberFormatException e){
                    actionToSend=null;
                    System.out.println("You must insert a number as 2nd and 3rd parameter of this action");
                }
                break;
            }

            //the user inserts: marketaction, number of the row/column, and if is a row or a column
            case"market": {
                boolean isRow=false;
                try {
                    int index = Integer.parseInt(in.get(1));
                    String rowOrColumn = in.get(2);
                    if (rowOrColumn.equals("row")) {
                        isRow = true;
                    } else if (rowOrColumn.equals("column")) isRow = false;
                    if (!rowOrColumn.equals("row") && !rowOrColumn.equals("column")) {
                        actionToSend = null;
                        System.out.println("You must insert row or column!");
                    } else if (rowOrColumn.equals("column") && (index < 0 || index > 3)) {
                        actionToSend = null;
                        System.out.println("You must insert an index between 0 and 3 if you choose column");
                    } else if (rowOrColumn.equals("row") && (index < 0 || index > 2)) {
                        actionToSend = null;
                        System.out.println("You must insert an index between 0 and 2 if you choose row");
                    } else actionToSend = new MarketAction(index, isRow);
                }catch (IndexOutOfBoundsException e){
                    actionToSend=null;
                    System.out.println("You must insert row or column and then an index");
                }
                break;
            }

            case "developmentproduction":{
                try {
                    int index = Integer.parseInt(in.get(1));
                    if(index<0 || index>2){
                        actionToSend=null;
                        System.out.println("The index must be between 0 and 2");
                    }
                    else actionToSend = new DevelopmentProductionAction(index);
                }catch (IndexOutOfBoundsException e){
                    actionToSend=null;
                    System.out.println("You must insert the index of the DevZone of the card you want to activate the " +
                            "production of");
                }
                break;
            }

            case "leaderproduction":{
                try {
                    int index = Integer.parseInt(in.get(1));
                    ResourceType resourceType = parseResource(in.get(2));
                    if(index<0 || index>1){
                        actionToSend = null;
                        System.out.println("You must insert an index, only 0 or 1 are valid");
                    }
                    else if(resourceType==null){
                        actionToSend = null;
                        System.out.println("You must insert a valid resource type [coin, stone, servant, shield]");
                    }
                    else actionToSend = new LeaderProductionAction(index, resourceType);
                }catch (IndexOutOfBoundsException e){
                    actionToSend = null;
                    System.out.println("You must insert the index of the leader card you want to activate and then " +
                            "the resource type you want to produce");
                }catch (NumberFormatException e){
                    actionToSend=null;
                    System.out.println("You must insert a number as parameter of this action");
                }
                break;
            }

            case "printmarket":
                actionToSend= new PrintMarketAction();
                break;

            case "test":
                actionToSend= new TestAction("white");
                break;

            case "baseproductionaction":{
                ArrayList <ResourceType> resourcesParsed1= new ArrayList<ResourceType>();
                ArrayList <ResourceType> resourcesParsed2= new ArrayList<ResourceType>();
                int i;
                if (in.get(1).equals("used:")){
                    for(i=2;!in.get(i).equals("wanted:");i++){
                        resourcesParsed1.add(parseResource(in.get(i)));
                    }
                    i++;
                    while(i<in.size()){
                        resourcesParsed2.add(parseResource(in.get(i)));
                        i++;
                    }
                }
                actionToSend = new BaseProductionAction(resourcesParsed1,resourcesParsed2);
                break;
            }

            case"help": {
                System.out.println("here is the list of commands you might insert:");
                System.out.println("'activateleadercard': activate a leader card; you have to insert the index of the card you want to activate.\n" +
                        "'viewdashboard': view the dashboard of a different player; you have to insert the index of the player whose dashboard you want to receive.\n" +
                        "'buydevelopmentcard': buy a development card from a deck on the gameboard; you have to insert the color and the level of the card you are buying, and the index of the development card zone where you want to put it (between 1 and 3)\n" +
                        "'market': make the action to receive resources from market; you have to insert the index of the row/column you want to take the resources from, and if its a row or a column.\n" +
                        "'developmentproduction': activate the production of a development card you own; you have to insert the the index of the development card zone that you want to activate.\n" +
                        "'leaderproduction': activate the production of a development card you own; you have to insert the the index of the leader card that you want to activate, and the resource that you want to produce. (e.g leaderproduction 1 coin) \n" +
                        "'baseproductionaction': activate your base production; you have to insert the list of resources that you want to consume after writing 'used:', and the list of resources that you want to produce after 'wanted:' (e.g. baseproductionaction used: coin stone wanted: servant)\n");
                actionToSend = null;
                break;
            }

            case "discardleader":
                actionToSend = new DiscardLeaderCard(Integer.parseInt(in.get(1)));
                break;

            default:{
                System.out.println("uncorrect action type inserted,try again");
                actionToSend = null;
                break;}
        }
        System.out.println("the action inserted is "+ actionToSend);
        return actionToSend;
    }

    /**
     * Parser a string to a {@link Color}
     * @param colorToParse: string representing a color
     * @return {@link Color}
     */

    public Color colorParser(String colorToParse){
        switch(colorToParse.toLowerCase()){
            case "blue": return Color.Blue;
            case "yellow": return Color.Yellow;
            case "green": return Color.Green;
            case "purple": return Color.Purple;
        }
        return null;
    }


    /**
     * Parser a string to a {@link ResourceType}
     * @param string: string representing a resource type
     * @return {@link ResourceType}
     */

    public ResourceType parseResource(String string){
        switch (string.toLowerCase(Locale.ROOT)){
            case "coin": return ResourceType.Coin;
            case "stone": return ResourceType.Stone;
            case "servant": return ResourceType.Servant;
            case "shield": return ResourceType.Shield;
        }
        return null;
    }
}
